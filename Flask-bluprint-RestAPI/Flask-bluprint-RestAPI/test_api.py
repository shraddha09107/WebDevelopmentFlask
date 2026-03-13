import requests
import json

BASE_URL = "http://localhost:5000"

# Test 1: Register user
print("1. Registering user...")
register_data = {
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
}
response = requests.post(f"{BASE_URL}/auth/register", json=register_data)
print(f"Status: {response.status_code}")
print(f"Response: {response.text}\n")

# Test 2: Login
print("2. Logging in...")
login_data = {"username": "testuser", "password": "password123"}
response = requests.post(f"{BASE_URL}/auth/login", json=login_data)
print(f"Status: {response.status_code}")
print(f"Response: {response.text}")

if response.status_code == 200:
    token_data = response.json()
    token = token_data.get("access_token")
    print(f"Got token: {token[:20]}...\n")

    # Test 3: Create post
    print("3. Creating post...")
    headers = {"Authorization": f"Bearer {token}"}
    post_data = {
        "title": "My First Blog Post",
        "content": "This is the detailed content of my blog post.",
    }
    response = requests.post(f"{BASE_URL}/posts", json=post_data, headers=headers)
    print(f"Status: {response.status_code}")
    print(f"Response: {response.text}")
