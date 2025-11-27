import os

from dotenv import load_dotenv
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from pydantic import BaseModel, EmailStr
from pymongo import MongoClient
from passlib.context import CryptContext
from datetime import datetime, timezone

# ---------- Password hashing utils ----------

# Use PBKDF2-SHA256 instead of bcrypt to avoid backend issues
pwd_context = CryptContext(
    schemes=["pbkdf2_sha256"],
    deprecated="auto",
)

def hash_password(password: str) -> str:
    return pwd_context.hash(password)


def verify_password(plain_password: str, hashed_password: str) -> bool:
    return pwd_context.verify(plain_password, hashed_password)

# ---------- Config ----------

load_dotenv()

MONGO_URI = os.getenv("MONGODB_URI")
DB_NAME = os.getenv("MONGODB_DB_NAME")

client = MongoClient(MONGO_URI)
db = client[DB_NAME]
users_col = db["users"]

app = FastAPI(
    title="SporeX Backend",
    version="0.1.0",
)

# CORS – Android isn’t a browser, but this also helps if you later add a web UI
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],        # later you can lock this down
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ---------- Pydantic models ----------

class RegisterBody(BaseModel):
    email: EmailStr
    password: str
    username: str | None = None
    name: str | None = None


class LoginBody(BaseModel):
    email: EmailStr
    password: str


class BasicResponse(BaseModel):
    success: bool
    message: str


# ---------- Routes ----------

@app.get("/api/health", response_model=BasicResponse)
async def health_check():
    """
    Simple health check for your backend.
    """
    return {"success": True, "message": "Backend running"}


@app.post("/api/register")
async def register(body: RegisterBody):
    """
    Register a new user.

    - Checks for existing email
    - Hashes password
    - Inserts document that matches MongoDB validator
    """

    # 1) Check if a user with this email already exists
    if users_col.find_one({"email": body.email}):
        return JSONResponse(
            status_code=409,
            content={"success": False, "message": "User already exists"},
        )

    # 2) Decide username (either from body or from email prefix)
    username = body.username or body.email.split("@")[0]

    # 3) Hash the password
    password_hash = hash_password(body.password)

    # 4) Build document that satisfies the collection validator
    user_doc = {
        "email": body.email,
        "username": username,
        "password_hash": password_hash,
        "role": "member",                 # default role
        "status": "active",               # default status
        "created_at": datetime.now(timezone.utc),
    }

    # Optional extra field, not part of the validator but allowed
    if body.name:
        user_doc["name"] = body.name

    # 5) Insert into Mongo
    users_col.insert_one(user_doc)

    # 6) Response shape friendly to Android
    return {"success": True, "message": "User registered"}


@app.post("/api/login")
async def login(body: LoginBody):
    user = users_col.find_one({"email": body.email})

    if not user:
        return JSONResponse(
            status_code=401,
            content={"success": False, "message": "Invalid credentials"},
        )

    if not verify_password(body.password, user["password_hash"]):
        return JSONResponse(
            status_code=401,
            content={"success": False, "message": "Invalid credentials"},
        )

    return {"success": True, "message": "Login OK"}
