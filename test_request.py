import requests
import json

data = {
    "customerNumber": "CUST001",
    "squareMetres": 25.5,
    "fair": "Book Fair 2024",
    "location": "Hall A, Section 3"
}

# Try shop-service first
print("Testing shop-service:")
try:
    response = requests.post("http://localhost:8000/api/stands", json=data, headers={"Content-Type": "application/json"})
    print(f"Shop Service Status Code: {response.status_code}")
    print(f"Shop Service Response Headers: {response.headers}")
    print(f"Shop Service Response Body: {response.text}")
except Exception as e:
    print(f"Error with shop-service: {e}")

print("\nTesting stand-service directly:")
try:
    response = requests.post("http://localhost:8080/stands", json=data, headers={"Content-Type": "application/json"})
    print(f"Stand Service Status Code: {response.status_code}")
    print(f"Stand Service Response Headers: {response.headers}")
    print(f"Stand Service Response Body: {response.text}")
except Exception as e:
    print(f"Error with stand-service: {e}") 