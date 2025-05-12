from pydantic_settings import BaseSettings
from typing import List

class Settings(BaseSettings):
    # API Configuration
    API_TITLE: str = "Library Service API"
    API_DESCRIPTION: str = "A microservice that forwards requests to stand-service and user-service"
    API_VERSION: str = "1.0.0"
    
    # Service URLs
    STAND_SERVICE_URL: str = "http://localhost:8080"
    USER_SERVICE_URL: str = "http://localhost:8081"
    
    # Server Configuration
    HOST: str = "0.0.0.0"
    PORT: int = 8000
    
    # CORS Configuration
    CORS_ORIGINS: List[str] = ["*"]  # In production, replace with specific origins
    CORS_ALLOW_CREDENTIALS: bool = True
    CORS_ALLOW_METHODS: List[str] = ["*"]
    CORS_ALLOW_HEADERS: List[str] = ["*"]
    
    class Config:
        env_file = ".env"
        case_sensitive = True

# Create settings instance
settings = Settings() 