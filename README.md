# Verify Give Link - Donation Management System

A web application for managing donation requests and beneficiaries with admin approval functionality.

## Features

- Home page for beneficiaries to request help
- Donation page for donors to contribute
- Admin panel to approve/reject beneficiary requests
- Secure authentication for admin access
- MySQL database for data storage

## Prerequisites

- Python 3.7+
- MySQL Server
- pip (Python package installer)

## Installation

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd verify-give-link
```

### 2. Create a virtual environment (optional but recommended)

```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

### 3. Install dependencies

```bash
pip install -r requirements.txt
```

### 4. Set up the database

First, make sure your MySQL server is running. Then:

```bash
python init_db.py
```

This will create the `donation_system` database and required tables.

### 5. Configure database credentials

Edit the `app.py` file and update the MySQL configuration with your credentials:

```python
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'  # Change to your MySQL username
app.config['MYSQL_PASSWORD'] = 'password'  # Change to your MySQL password
```

### 6. Run the application

```bash
python app.py
```

The application will be available at `http://localhost:5000`

## Admin Access

- Default admin username: `admin`
- Default admin password: `admin123`
- Admin panel: `http://localhost:5000/admin/login`

## Project Structure

```
verify-give-link/
├── app.py                 # Main Flask application
├── init_db.py            # Database initialization script
├── requirements.txt      # Python dependencies
├── templates/            # HTML templates
│   ├── index.html        # Home page
│   ├── donate.html       # Donation page
│   ├── admin.html        # Admin dashboard
│   └── admin_login.html  # Admin login page
└── static/               # Static files (CSS, JS)
    ├── css/
    │   └── style.css
    └── js/
        └── script.js
```

## Database Schema

The application uses two main tables:

1. `beneficiaries` - Stores beneficiary requests
2. `admin_users` - Stores admin user credentials

## Customization

- Modify CSS in `static/css/style.css` to change the appearance
- Update templates in the `templates/` directory to modify page structure
- Add new features by extending the Flask routes in `app.py`

## Security Notes

- Change the default admin password after first login
- Update the Flask secret key in production
- Use environment variables for database credentials in production

## Troubleshooting

If you encounter database connection issues:
1. Verify MySQL server is running
2. Check database credentials in `app.py`
3. Ensure the database name `donation_system` exists