# 🗨️ Simple Chat App

A full-stack chat application built with **Spring Boot** and **React**. It provides:
- RESTful APIs for user registration, login, and room management
- JWT-based authentication and authorization
- WebSocket-based real-time messaging
- PostgreSQL for persistent storage

## 🛠 Tech Stack

### Backend
- **Spring Boot**
- **Spring Security** (with JWT)
- **Spring WebSocket (STOMP)**
- **Spring Data JPA**
- **PostgreSQL**

### Frontend
- **React**
- **Axios** (for HTTP requests)
- **SockJS + StompJS** (for WebSocket communication)
- **React Router** (for routing)

---

## 📦 Features

- ✅ User registration and login (via REST API)
- ✅ JWT authentication and token-based security
- ✅ Room creation and member management (add/remove users to rooms)
- ✅ WebSocket messaging in real-time per room
- ✅ Message persistence (chat history saved in the database)

---

## ▶️ How to Run the Project

Follow the steps below to start the entire chat application locally.

### 1. Start Infrastructure (PostgreSQL, etc.)

```bash
cd backend-java/infra
docker-compose up -d
```

### 2.  Run the Backend
 Make sure your infrastructure (like PostgreSQL) is already running, then:

```bash
cd backend-java
gradle bootRun
```

### 2.  Run the Frontend

```bash
cd frontend
npm install
npm start
```
