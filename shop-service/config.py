from typing import List
import os
from dotenv import load_dotenv

# Load environment variables from .env file
load_dotenv()

class Settings:
    # API Configuration
    API_TITLE: str = "Library Service API"
    API_DESCRIPTION: str = "A microservice that forwards requests to stand-service and user-service"
    API_VERSION: str = "1.0.0"
    
    # Service URLs
    STAND_SERVICE_URL: str = os.getenv("STAND_SERVICE_URL", "http://localhost:8080")
    USER_SERVICE_URL: str = os.getenv("USER_SERVICE_URL", "http://localhost:8081")
    
    # Server Configuration
    HOST: str = os.getenv("HOST", "0.0.0.0")
    PORT: int = int(os.getenv("PORT", "8000"))
    
    # CORS Configuration
    CORS_ORIGINS: List[str] = ["*"]
    CORS_ALLOW_CREDENTIALS: bool = True
    CORS_ALLOW_METHODS: List[str] = ["*"]
    CORS_ALLOW_HEADERS: List[str] = ["*"]

# Create settings instance
settings = Settings() 