# SporeX
SporeX is a mold detection and prevention system that combines image recognition with IoT sensor data.
## 👥 Team Members & Roles

| **Team Member** | **Roles / Responsibilities** |
|------------------|-------------------------------|
| **Meghan Keightley** | Creative Lead, UX/UI Designer & Frontend Operations |
| **Xu Teck Tan** | Lead Hardware & Security |
| **Wiktor Teter** | Team Lead, Lead Tester, Database Operations |
| **Eljesa Mesi** | Lead Scrum Master / Overall Support |
| **Diane Jugul Dalyop** | Backend Functionality & Operations, Team Researcher |

---

# 👷 SporeX — Developer Setup Guide

*Backend: FastAPI • Frontend: Android Studio • Database: MongoDB Atlas*
 
---

## 📦 1. Clone the Repository

```bash
git clone https://github.com/<your-org>/<your-repo>.git
cd <your-repo>
```

You should now see this folder structure:

```
SporeX/
│
├── app/                 ← ANDROID STUDIO PROJECT (main app module)
├── sporex-backend/      ← FASTAPI BACKEND (VS Code)
│
├── gradle/              ← Android build system
├── .gradle/
├── .idea/
├── .kotlin/
│
├── build.gradle.kts
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
├── README.md
└── .gitignore
```

---

## 🔧 2. Backend Setup (FastAPI + MongoDB Atlas)

We develop & run the backend inside **VS Code**.

---

### ✔️ 2.1 Open the Backend Folder in VS Code

```
File → Open Folder → sporex-backend
```

---

### ✔️ 2.2 Install Required VS Code Extension

#### 🟦 REST Client (Huachao Mao)

This extension allows us to test API endpoints using `.http` files.

Install via:

```
VS Code → Extensions → search "REST Client"
```

---

## 🐍 3. Create a Python Virtual Environment

Open a VS Code terminal inside `sporex-backend`:

```bash
python -m venv venv
```

Activate it:

### Windows

```bash
venv\Scripts\activate
```

### macOS/Linux

```bash
source venv/bin/activate
```

---

## 📥 4. Install Backend Dependencies

```bash
pip install -r requirements.txt
```

If Pydantic email validation is required:

```bash
pip install "pydantic[email]"
```

---

## 🔐 5. Create Your `.env` File

*(This file must **not** be committed to Git.)*

Inside **sporex-backend**, create:

```
.env
```

Add:

```
MONGODB_URI="MONGODB_URI="mongodb+srv://<USERNAME>:<PASSWORD>@<CLUSTER_NAME>.<CLUSTER_ID>.mongodb.net/<DATABASE_NAME>?retryWrites=true&w=majority&appName=<APP_NAME>"
MONGODB_DB_NAME="SporexDB"
```

Each team member must place **their own** Atlas URI here.

---

## 🌐 6. MongoDB Atlas Setup (Required for Backend Developers)

Every backend developer needs:

### ✔ A database user

Atlas → **Security → Database Access**

### ✔ IP whitelist entry

Atlas → **Network Access → Add IP**

Use:

```
0.0.0.0/0
```

*(Development only — will change later.)*

No need to manually add documents — FastAPI handles user creation and validation.

---

## ▶️ 7. Run the Backend

Inside `sporex-backend`:

```bash
uvicorn app:app --reload --port 8000
```

The backend now runs at:

```
http://localhost:8000
```

---

## 🧪 8. Test API Endpoints Using REST Client

Open **test.http** in VS Code.

Example:

```http
POST http://localhost:8000/api/login
Content-Type: application/json

{
  "email": "test@sporex.com",
  "password": "123456"
}
```

Click **Send Request** above the request.

Expected response:

```json
{
  "success": true,
  "message": "Login OK"
}
```

---

## 🤖 9. Android App Setup

Open the **root project** in Android Studio:

```
File → Open → <repo-root>
```

Android Studio automatically loads:

```
/sporex_app
```

---

### ✔ API Base URL for Emulator

The Android emulator **cannot** use `localhost`.
Use the special emulator IP:

```
10.0.2.2
```

Your Retrofit client is already set to:

```
http://10.0.2.2:8000/api/
```

---

### ✔ Cleartext Networking

*(Already configured in the project)*

* `network_security_config.xml`
* `AndroidManifest.xml` → `usesCleartextTraffic="true"`

This allows the Android app to call a local HTTP backend.

---

## 🟢 10. Run the App

1. Start the backend
2. Launch Android Emulator
3. Open the SporeX app
4. Press **Login**

If everything is configured correctly, you will receive a valid response.

End-to-end flow:

```
Android → FastAPI → MongoDB Atlas → FastAPI → Android
```
