from flask import Flask, request, jsonify
import mysql.connector
from mysql.connector import Error
import bcrypt
import os
from flask_cors import CORS
# from dotenv import load_dotenv # Uncomment if you use .env file

# load_dotenv()

app = Flask(__name__)
CORS(app) # Enable CORS for all routes

# Database Configuration matches previous PHP db.php
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'bioelectrodeai'
}

def get_db_connection():
    try:
        conn = mysql.connector.connect(**DB_CONFIG)
        if conn.is_connected():
            return conn
    except Error as e:
        print(f"Error connecting to MySQL: {e}")
    return None

@app.route('/')
def home():
    return jsonify({"message": "BioElectrodeAI Backend is running!"})

# --- REGISTER ---
@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    fullname = data.get('fullname')
    email = data.get('email')
    role = data.get('role')
    password = data.get('password')
    confirm_password = data.get('confirm_password')

    if not all([fullname, email, role, password, confirm_password]):
        return jsonify({"status": "error", "message": "All fields are required"}), 400

    if password != confirm_password:
        return jsonify({"status": "error", "message": "Passwords do not match"}), 400

    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Database connection failed"}), 500

    cursor = conn.cursor(dictionary=True)
    
    try:
        # Check if email exists
        cursor.execute("SELECT id FROM users WHERE email = %s", (email,))
        if cursor.fetchone():
            return jsonify({"status": "error", "message": "Email already exists"}), 409

        # Hash password
        hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

        # Insert user
        query = "INSERT INTO users (name, email, role, password) VALUES (%s, %s, %s, %s)"
        cursor.execute(query, (fullname, email, role, hashed_password))
        conn.commit()

        return jsonify({"status": "success", "message": "User registered successfully"}), 201
    except Error as e:
        return jsonify({"status": "error", "message": str(e)}), 500
    finally:
        if conn.is_connected():
            cursor.close()
            conn.close()

# --- LOGIN ---
@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    email = data.get('email')
    password = data.get('password')

    if not email or not password:
        return jsonify({"status": "error", "message": "Email and Password are required"}), 400

    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Database connection failed"}), 500

    cursor = conn.cursor(dictionary=True)

    try:
        cursor.execute("SELECT id, name, role, password FROM users WHERE email = %s", (email,))
        user = cursor.fetchone()

        if not user:
             return jsonify({"status": "error", "message": "Invalid email or password"}), 401

        # Verify password (using bcrypt check)
        # Note: PHP password_hash uses bcrypt by default usually. 
        # If your PHP passwords were standardized, bcrypt.checkpw should work.
        # However, PHP's password_verify handles algos automatically. 
        # Here we assume standard bcrypt hash.
        
        # If existing hashes from PHP are incompatible, user might need to reset password or re-register.
        # But commonly PHP default is bcrypt compatible.
        
        # For new users registered via Python, valid.
        # We need to handle potential format differences if PHP used standard algos.
        # Assuming standard compatible hash for now.
        
        valid_password = False
        try:
             valid_password = bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8'))
        except ValueError:
            # Fallback if encoding issues or different hash format (e.g. raw md5 legacy - unlikely based on PHP code)
            pass

        if not valid_password:
             return jsonify({"status": "error", "message": "Invalid email or password"}), 401

        return jsonify({
            "status": "success",
            "message": "Login successful",
            "user": {
                "id": user['id'],
                "name": user['name'],
                "email": email,
                "role": user['role']
            }
        }), 200

    except Error as e:
        return jsonify({"status": "error", "message": str(e)}), 500
    finally:
        if conn.is_connected():
            cursor.close()
            conn.close()

# --- FORGOT PASSWORD (Simulated) ---
@app.route('/forgot_password', methods=['POST'])
def forgot_password():
    data = request.get_json()
    email = data.get('email')
    
    if not email:
        return jsonify({"status": "error", "message": "Email is required"}), 400
        
    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Database connection failed"}), 500

    cursor = conn.cursor(dictionary=True)
    try:
        cursor.execute("SELECT id FROM users WHERE email = %s", (email,))
        if not cursor.fetchone():
             # Security: usually don't reveal if email exists, but strict req says:
             return jsonify({"status": "error", "message": "Email not found"}), 404
        
        # In a real app, generate token and send email.
        # Here we verify the email exists and allow client to proceed to reset screen if architected that way
        # OR send a success message "Reset link sent".
        
        return jsonify({"status": "success", "message": "If this email exists, a reset link has been sent."}), 200
    except Error as e:
         return jsonify({"status": "error", "message": str(e)}), 500
    finally:
        if conn and conn.is_connected():
            cursor.close()
            conn.close()


# --- RESET PASSWORD ---
@app.route('/reset_password', methods=['POST'])
def reset_password():
    data = request.get_json()
    email = data.get('email')
    new_password = data.get('new_password')
    
    if not email or not new_password:
         return jsonify({"status": "error", "message": "Email and new password required"}), 400
         
    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Database connection failed"}), 500

    cursor = conn.cursor()
    try:
        # Check if email exists first
        cursor.execute("SELECT id FROM users WHERE email = %s", (email,))
        if not cursor.fetchone():
             return jsonify({"status": "error", "message": "Email not found"}), 404

        hashed_password = bcrypt.hashpw(new_password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
        
        cursor.execute("UPDATE users SET password = %s WHERE email = %s", (hashed_password, email))
        conn.commit()
        
        return jsonify({"status": "success", "message": "Password updated successfully"}), 200
    except Error as e:
        return jsonify({"status": "error", "message": str(e)}), 500
    finally:
        if conn and conn.is_connected():
            cursor.close()
            conn.close()

# --- VERIFY EMAIL ---
@app.route('/verify_email', methods=['POST'])
def verify_email():
    data = request.get_json()
    email = data.get('email')
    
    if not email:
        return jsonify({"status": "error", "message": "Email is required"}), 400
        
    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Database connection failed"}), 500
        
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT id FROM users WHERE email = %s", (email,))
        if cursor.fetchone():
            return jsonify({
                "status": "success", 
                "message": "Email found", 
                "exists": True
            }), 200
        else:
            return jsonify({
                "status": "success", 
                "message": "Email not found", 
                "exists": False
            }), 200 # App seems to handle "exists": false as valid response but logic path might error if not found. 
                    # Based on existing code, if found -> success. 
    except Error as e:
        return jsonify({"status": "error", "message": str(e)}), 500
    finally:
        if conn.is_connected():
            cursor.close()
            conn.close()

if __name__ == '__main__':
    # Run on all interfaces to be accessible from Emulator/Device
    app.run(host='0.0.0.0', port=5000, debug=True)
