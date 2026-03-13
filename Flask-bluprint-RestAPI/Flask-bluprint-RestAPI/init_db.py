from app.app import create_app

app = create_app()

with app.app_context():
    from app.database.db import db

    # Drop all tables and recreate them with the new schema
    print("Dropping and recreating all tables...")
    db.drop_all()
    db.create_all()
    print("✓ Tables recreated successfully!")
