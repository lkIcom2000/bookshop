# Library Service

This is a Python-based microservice that forwards requests to the stand-service and user-service. It serves as an API gateway for the online bookshop frontend.

## Features

- Forwards all stand-related endpoints from the stand-service
- Forwards all user-related endpoints from the user-service
- Automatic API documentation (Swagger UI)
- CORS support
- Environment-based configuration

## Prerequisites

- Python 3.8 or higher
- pip (Python package manager)

## Setup

1. Create a virtual environment:
   ```bash
   python -m venv venv
   ```

2. Activate the virtual environment:
   - Windows:
     ```bash
     .\venv\Scripts\activate
     ```
   - Unix/MacOS:
     ```bash
     source venv/bin/activate
     ```

3. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```

4. Create a `.env` file:
   ```bash
   cp .env.example .env
   ```
   Then edit the `.env` file with your actual service URLs.

## Running the Service

1. Start the service:
   ```bash
   python main.py
   ```
   The service will start on `http://localhost:8000`

2. Access the API documentation:
   - Swagger UI: `http://localhost:8000/docs`
   - ReDoc: `http://localhost:8000/redoc`

## API Endpoints

### Stand Service Endpoints
- GET `/api/stands` - Get all stands
- GET `/api/stands/{id}` - Get a specific stand
- POST `/api/stands` - Create a new stand
- PUT `/api/stands/{id}` - Update a stand
- DELETE `/api/stands/{id}` - Delete a stand

### User Service Endpoints
- GET `/api/users/{id}` - Get a specific user
- POST `/api/users` - Create a new user

## Environment Variables

- `STAND_SERVICE_URL` - URL of the stand service (default: http://localhost:8080)
- `USER_SERVICE_URL` - URL of the user service (default: http://localhost:8081) 