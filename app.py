from flask import Flask, render_template, request, redirect, url_for, flash, session
from flask_mysqldb import MySQL
from werkzeug.security import generate_password_hash, check_password_hash
import os

app = Flask(__name__)
app.secret_key = 'your_secret_key_here'

# MySQL Configuration
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = 'password'  # Change this to your MySQL password
app.config['MYSQL_DB'] = 'donation_system'

mysql = MySQL(app)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/donate')
def donate():
    return render_template('donate.html')

def is_admin_logged_in():
    return 'admin_id' in session

@app.route('/admin')
def admin():
    if not is_admin_logged_in():
        return redirect(url_for('admin_login'))
    
    cur = mysql.connection.cursor()
    cur.execute("SELECT id, name, email, phone, address, reason, amount_needed, status FROM beneficiaries ORDER BY created_at DESC")
    beneficiaries = cur.fetchall()
    cur.close()
    return render_template('admin.html', beneficiaries=beneficiaries)

@app.route('/admin/login', methods=['GET', 'POST'])
def admin_login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        
        cur = mysql.connection.cursor()
        cur.execute("SELECT id, password_hash FROM admin_users WHERE username = %s", (username,))
        user = cur.fetchone()
        cur.close()
        
        if user and check_password_hash(user[1], password):
            session['admin_id'] = user[0]
            return redirect(url_for('admin'))
        else:
            flash('Invalid credentials')
    
    return render_template('admin_login.html')

@app.route('/admin/logout')
def admin_logout():
    session.pop('admin_id', None)
    return redirect(url_for('admin_login'))

@app.route('/admin/approve/<int:id>')
def approve_beneficiary(id):
    cur = mysql.connection.cursor()
    cur.execute("UPDATE beneficiaries SET status = 'approved' WHERE id = %s", (id,))
    mysql.connection.commit()
    cur.close()
    flash('Beneficiary approved successfully')
    return redirect(url_for('admin'))

@app.route('/admin/reject/<int:id>')
def reject_beneficiary(id):
    cur = mysql.connection.cursor()
    cur.execute("UPDATE beneficiaries SET status = 'rejected' WHERE id = %s", (id,))
    mysql.connection.commit()
    cur.close()
    flash('Beneficiary rejected successfully')
    return redirect(url_for('admin'))

@app.route('/submit_beneficiary', methods=['POST'])
def submit_beneficiary():
    name = request.form['name']
    email = request.form['email']
    phone = request.form['phone']
    address = request.form['address']
    reason = request.form['reason']
    amount_needed = request.form['amount_needed']
    
    cur = mysql.connection.cursor()
    cur.execute("INSERT INTO beneficiaries (name, email, phone, address, reason, amount_needed, status) VALUES (%s, %s, %s, %s, %s, %s, 'pending')", 
                (name, email, phone, address, reason, amount_needed))
    mysql.connection.commit()
    cur.close()
    
    flash('Your request has been submitted successfully. It will be reviewed by our admin.')
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)