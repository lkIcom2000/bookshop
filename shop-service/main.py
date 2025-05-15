from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import httpx
from typing import List, Optional
from pydantic import BaseModel, Field

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
class StandCreate(BaseModel):
    customerNumber: str = Field(..., min_length=1, description="Customer number for the stand")
    squareMetres: float = Field(..., gt=0, description="Size of the stand in square metres")
    fair: str = Field(..., min_length=1, description="Name of the fair")
    location: str = Field(..., min_length=1, description="Location of the stand")

    class Config:
        json_schema_extra = {
            "example": {
                "customerNumber": "CUST001",
                "squareMetres": 25.5,
                "fair": "Book Fair 2024",
                "location": "Hall A, Section 3"
            }
        }

    @classmethod
    def validate_all_fields_present(cls, values):
        for field in cls.__fields__:
            if field not in values or values[field] is None:
                raise ValueError(f"Field '{field}' is required and cannot be null")
        return values

class StandResponse(BaseModel):
    id: int
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
@app.get("/api/stands", response_model=List[StandResponse])
async def get_all_stands():
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{settings.STAND_SERVICE_URL}/stands")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch stands")
        return response.json()

@app.get("/api/stands/{stand_id}", response_model=StandResponse)
async def get_stand_by_id(stand_id: int):
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{settings.STAND_SERVICE_URL}/stands/{stand_id}")
        if response.status_code == 404:
            raise HTTPException(status_code=404, detail="Stand not found")
        if response.status_code != 200:
            raise HTTPException(status_code=response.status_code, detail="Failed to fetch stand")
        return response.json()

@app.post("/api/stands", response_model=StandResponse)
async def create_stand(stand: StandCreate):
    try:
        # Validate all fields are present
        StandCreate.validate_all_fields_present(stand.dict())
        
        request_data = stand.dict()
        print(f"Creating stand with data (raw dict): {request_data}")
        print(f"Data types - customerNumber: {type(request_data['customerNumber'])}, "
              f"squareMetres: {type(request_data['squareMetres'])}, "
              f"fair: {type(request_data['fair'])}, "
              f"location: {type(request_data['location'])}")
        
        async with httpx.AsyncClient() as client:
            # Convert to JSON string and back to ensure proper serialization
            import json
            json_data = json.dumps(request_data)
            print(f"JSON payload being sent: {json_data}")
            
            response = await client.post(
                f"{settings.STAND_SERVICE_URL}/stands",
                json=request_data,
                headers={"Content-Type": "application/json"}
            )
            print(f"Response status: {response.status_code}")
            print(f"Response headers: {response.headers}")
            print(f"Response body: {response.text}")
            
            if response.status_code != 200:
                print(f"Error response from stand-service: {response.status_code} - {response.text}")
                raise HTTPException(status_code=response.status_code, detail="Failed to create stand")
            return response.json()
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))
    except Exception as e:
        print(f"Unexpected error: {str(e)}")
        raise HTTPException(status_code=500, detail="Internal server error")

@app.put("/api/stands/{stand_id}", response_model=StandResponse)
async def update_stand(stand_id: int, stand: StandResponse):
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