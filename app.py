from flask import Flask, render_template, request, redirect, url_for, session, flash
import mysql.connector
import bcrypt

app = Flask(__name__)
app.secret_key = 'your_secret_key_here'

# MySQL Configuration
db_config = {
    'host': 'localhost',
    'user': 'root',
    'password': 'Qazplm123$$$',
    'database': 'flask_login'
}

# Home Route
@app.route('/')
def home():
    if 'username' in session:
        return f'Welcome, {session["username"]}! <a href="/logout">Logout</a>'
    return redirect(url_for('login'))

# Login Route
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']

        connection = mysql.connector.connect(**db_config)
        cursor = connection.cursor()
        cursor.execute('SELECT * FROM users WHERE username = %s', (username,))
        user = cursor.fetchone()
        cursor.close()
        connection.close()

        if user and bcrypt.checkpw(password.encode('utf-8'), user[2].encode('utf-8')):
            session['username'] = username
            return redirect(url_for('home'))
        else:
            flash('Invalid username or password', 'error')
    return render_template('login.html')

# Registration Route
@app.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']

        # Hash the password
        hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

        connection = mysql.connector.connect(**db_config)
        cursor = connection.cursor()
        try:
            cursor.execute('INSERT INTO users (username, password) VALUES (%s, %s)', 
                           (username, hashed_password))
            connection.commit()
            flash('Registration successful! Please login.', 'success')
            return redirect(url_for('login'))
        except mysql.connector.Error as err:
            flash(f'Error: {str(err)}', 'error')
        finally:
            cursor.close()
            connection.close()

    return render_template('register.html')

# Logout Route
@app.route('/logout')
def logout():
    session.pop('username', None)
    return redirect(url_for('login'))

if __name__ == '__main__':
    app.run(debug=True)
