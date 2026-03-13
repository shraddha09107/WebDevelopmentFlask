from flask import Blueprint, request
from flask_jwt_extended import jwt_required, get_jwt_identity
from app.models.post import Post
from app.models.user import User
from app.database.db import db

post_bp = Blueprint("post_bp", __name__)


# GET all posts
@post_bp.route("", methods=["GET"])
def get_all_posts():
    posts_response = []
    posts = Post.query.all()

    for post in posts:
        posts_response.append(
            {
                "id": post.id,
                "title": post.title,
                "content": post.content,
                "user_id": post.user_id,
                "created_at": post.created_at.isoformat(),
                "updated_at": post.updated_at.isoformat(),
            }
        )

    return {
        "posts": posts_response,
        "total": len(posts_response),
    }, 200
    
# GET a single post by ID
@post_bp.route("/<int:post_id>", methods=["GET"])
def get_post(post_id):
    post = Post.query.get(post_id)

    if not post:
        return {"message": "Post not found"}, 404

    return {
        "id": post.id,
        "title": post.title,
        "content": post.content,
        "user_id": post.user_id,
        "created_at": post.created_at.isoformat(),
        "updated_at": post.updated_at.isoformat(),
    }, 200


# CREATE a new post
@post_bp.route("", methods=["POST"])
@jwt_required()
def create_post():
    current_user_id = int(get_jwt_identity())
    data = request.get_json()

    if not data or not data.get("title") or not data.get("content"):
        return {"message": "Missing title or content"}, 400

    user = User.query.get(current_user_id)
    if not user:
        return {"message": "User not found"}, 404

    new_post = Post(
        title=data["title"],
        content=data["content"],
        user_id=current_user_id,
    )

    db.session.add(new_post)
    db.session.commit()

    return {
        "message": "Post created successfully",
        "id": new_post.id,
        "title": new_post.title,
        "content": new_post.content,
        "user_id": new_post.user_id,
        "created_at": new_post.created_at.isoformat(),
    }, 201


# UPDATE a post (owner only)
@post_bp.route("/<int:post_id>", methods=["PUT"])
@jwt_required()
def update_post(post_id):
    current_user_id = int(get_jwt_identity())
    post = Post.query.get(post_id)

    if not post:
        return {"message": "Post not found"}, 404

    if post.user_id != current_user_id:
        return {"message": "Unauthorized: You can only edit your own posts"}, 403

    data = request.get_json()

    if "title" in data:
        post.title = data["title"]
    if "content" in data:
        post.content = data["content"]

    db.session.commit()

    return {
        "message": "Post updated successfully",
        "id": post.id,
        "title": post.title,
        "content": post.content,
        "user_id": post.user_id,
        "created_at": post.created_at.isoformat(),
        "updated_at": post.updated_at.isoformat(),
    }, 200


# DELETE a post (owner only)
@post_bp.route("/<int:post_id>", methods=["DELETE"])
@jwt_required()
def delete_post(post_id):
    current_user_id = int(get_jwt_identity())
    post = Post.query.get(post_id)

    if not post:
        return {"message": "Post not found"}, 404

    if post.user_id != current_user_id:
        return {"message": "Unauthorized: You can only delete your own posts"}, 403

    db.session.delete(post)
    db.session.commit()

    return {"message": "Post deleted successfully"}, 200


# GET posts by user
@post_bp.route("/user/<int:user_id>", methods=["GET"])
def get_user_posts(user_id):
    user = User.query.get(user_id)

    if not user:
        return {"message": "User not found"}, 404

    page = request.args.get("page", 1, type=int)
    per_page = request.args.get("per_page", 10, type=int)

    posts = Post.query.filter_by(user_id=user_id).paginate(page=page, per_page=per_page)

    return {
        "user_id": user_id,
        "posts": [
            {
                "id": post.id,
                "title": post.title,
                "content": post.content,
                "created_at": post.created_at.isoformat(),
                "updated_at": post.updated_at.isoformat(),
            }
            for post in posts.items
        ],
        "total": posts.total,
        "pages": posts.pages,
        "current_page": page,
    }, 200
