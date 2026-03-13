from datetime import timedelta
from flask import Flask
from flask_jwt_extended import JWTManager
from app.models.user import User
from app.database.db import db


def create_app():
    app = Flask(__name__)
    app.config["SECRET_KEY"] = "your_secret_key_here"
    app.config["JWT_SECRET_KEY"] = "your_jwt_secret_key_here"
    app.config["SQLALCHEMY_DATABASE_URI"] = (
        "postgresql://postgres:Nopassword%4003@localhost/test"
    )
    # app.config["ACCESS_TOKEN_EXPIRES"] = 60 * 60 * 24 * 30  # 30 days
    app.config["ACCESS_TOKEN_EXPIRES"] = timedelta(days=30)
    jwt = JWTManager(app)

    db.init_app(app)
    with app.app_context():
        db.create_all()

    from app.routes.auth import auth_bp
    from app.routes.post import post_bp

    app.register_blueprint(auth_bp, url_prefix="/auth")
    app.register_blueprint(post_bp, url_prefix="/posts")

    return app
