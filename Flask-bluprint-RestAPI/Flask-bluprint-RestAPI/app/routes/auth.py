from flask import Blueprint, request
from flask_jwt_extended import create_access_token, jwt_required, get_jwt_identity
from app.models.user import User
from app.database.db import db

auth_bp = Blueprint("auth_bp", __name__)


@auth_bp.route("/register", methods=["GET", "POST"])
def register():
    user = request.get_json()
    if user:
        new_user = User(username=user["username"], email=user["email"])
        new_user.set_password(user["password"])
        db.session.add(new_user)
        db.session.commit()
        return {"message": "User registered successfully"}, 200
    return {"message": "Invalid data"}, 400


@auth_bp.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    if not data or not data.get("username") or not data.get("password"):
        return {"message": "Missing username or password"}, 400

    user = User.query.filter_by(username=data["username"]).first()
    if user and user.check_password(data["password"]):
        access_token = create_access_token(identity=str(user.id))
        return {"access_token": access_token, "user_id": user.id}, 200
    return {"message": "Invalid credentials"}, 401


@auth_bp.route("/dashboard/<int:user_id>")
@jwt_required()
def dashboard(user_id):
    current_user_id = int(get_jwt_identity())
    if current_user_id != user_id:
        return {"message": "Unauthorized access"}, 403
    return {"message": f"Dashboard for user {user_id}"}, 200


@auth_bp.route("/logout", methods=["POST"])
@jwt_required()
def logout():
    return {"message": "Logged out successfully"}, 200
