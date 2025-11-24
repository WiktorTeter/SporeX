import os

from dotenv import load_dotenv
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from pydantic import BaseModel, EmailStr
from pymongo import MongoClient

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

    Keeps the same behaviour/JSON shape you had in Flask so
    Android code doesn't need to change.
    """
    # Check if user exists
    if users_col.find_one({"email": body.email}):
        return JSONResponse(
            status_code=409,
            content={"success": False, "message": "User already exists"},
        )

    # For MVP we still store plain password (you can hash later)
    users_col.insert_one(
        {
            "email": body.email,
            "password": body.password,
            "name": body.name,
        }
    )

    return {"success": True, "message": "User registered"}


@app.post("/api/login")
async def login(body: LoginBody):
    """
    Log in an existing user.
    """
    user = users_col.find_one(
        {"email": body.email, "password": body.password}
    )

    if not user:
        return JSONResponse(
            status_code=401,
            content={"success": False, "message": "Invalid email or password"},
        )

    # Later you can return a token or user info here.
    return {"success": True, "message": "Login OK"}
