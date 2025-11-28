import mysql.connector
from mysql.connector import Error

def create_database_and_tables():
    try:
        # Connect to MySQL server (without specifying database)
        connection = mysql.connector.connect(
            host='localhost',
            user='root',
            password='password'  # Change this to your MySQL password
        )
        
        if connection.is_connected():
            cursor = connection.cursor()
            
            # Create database
            cursor.execute("CREATE DATABASE IF NOT EXISTS donation_system")
            print("Database 'donation_system' created or already exists")
            
            # Use the database
            cursor.execute("USE donation_system")
            
            # Create beneficiaries table
            create_beneficiaries_table = """
            CREATE TABLE IF NOT EXISTS beneficiaries (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                phone VARCHAR(15),
                address TEXT,
                reason TEXT NOT NULL,
                amount_needed DECIMAL(10, 2),
                status ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """
            cursor.execute(create_beneficiaries_table)
            print("Table 'beneficiaries' created or already exists")
            
            # Create admin users table
            create_admin_table = """
            CREATE TABLE IF NOT EXISTS admin_users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password_hash VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """
            cursor.execute(create_admin_table)
            print("Table 'admin_users' created or already exists")
            
            # Insert default admin user (username: admin, password: admin123)
            # Password hash for 'admin123' using werkzeug.security
            from werkzeug.security import generate_password_hash
            default_password_hash = generate_password_hash('admin123')
            cursor.execute(
                "INSERT IGNORE INTO admin_users (username, password_hash) VALUES (%s, %s)",
                ('admin', default_password_hash)
            )
            print("Default admin user created or already exists")
            
            connection.commit()
            print("Database setup completed successfully!")
            
    except Error as e:
        print(f"Error while connecting to MySQL: {e}")
        
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()
            print("MySQL connection is closed")

if __name__ == "__main__":
    create_database_and_tables()