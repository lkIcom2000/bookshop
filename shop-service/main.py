from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import httpx
from typing import List, Optional
from pydantic import BaseModel

from config import settings

app = FastAPI(
    title=settings.API_TITLE,
    description=settings.API_DESCRIPTION,
    version=settings.API_VERSION
)

# Add CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=settings.CORS_ORIGINS,
    allow_credentials=settings.CORS_ALLOW_CREDENTIALS,
    allow_methods=settings.CORS_ALLOW_METHODS,
    allow_headers=settings.CORS_ALLOW_HEADERS,
)

# Models
class Stand(BaseModel):
    id: Optional[int] = None
    customerNumber: str
    squareMetres: float
    fair: str
    location: str

class UserCreateDTO(BaseModel):
    name: str
    birth: str
    role: str
    adress: str
    phoneNumber: int
    payment: str

class UserResponseDTO(BaseModel):
    id: int
    name: str
    birth: str
    role: str
    adress: str
    phoneNumber: int
    payment: str

# Stand Service Endpoints
@app.get("/api/stands", response_model=List[Stand])
async def get_all_stands():
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{settings.STAND_SERVICE_URL}/stands")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch stands")
        return response.json()

@app.get("/api/stands/{stand_id}", response_model=Stand)
async def get_stand_by_id(stand_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{settings.STAND_SERVICE_URL}/stands/{stand_id}")
        if response.status_code == 404:
            raise HTTPException(status_code=404, detail="Stand not found")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch stand")
        return response.json()

@app.post("/api/stands", response_model=Stand)
async def create_stand(stand: Stand):
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{settings.STAND_SERVICE_URL}/stands", json=stand.dict(exclude={'id'}))
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to create stand")
        return response.json()

@app.put("/api/stands/{stand_id}", response_model=Stand)
async def update_stand(stand_id: int, stand: Stand):
    async with httpx.AsyncClient() as client:
        response = await client.put(f"{settings.STAND_SERVICE_URL}/stands/{stand_id}", json=stand.dict(exclude={'id'}))
        if response.status_code == 404:
            raise HTTPException(status_code=404, detail="Stand not found")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to update stand")
        return response.json()

@app.delete("/api/stands/{stand_id}")
async def delete_stand(stand_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.delete(f"{settings.STAND_SERVICE_URL}/stands/{stand_id}")
        if response.status_code == 404:
            raise HTTPException(status_code=404, detail="Stand not found")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to delete stand")
        return {"message": "Stand deleted successfully"}

# User Service Endpoints
@app.get("/api/users/{user_id}", response_model=UserResponseDTO)
async def get_user_by_id(user_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{settings.USER_SERVICE_URL}/users/{user_id}")
        if response.status_code == 404:
            raise HTTPException(status_code=404, detail="User not found")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch user")
        return response.json()

@app.post("/api/users", response_model=UserResponseDTO)
async def create_user(user: UserCreateDTO):
    async with httpx.AsyncClient() as client:
        response = await client.post(f"{settings.USER_SERVICE_URL}/users", json=user.dict())
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to create user")
        return response.json()

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=settings.HOST, port=settings.PORT) 