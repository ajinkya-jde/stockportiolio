function showLogin() {
    document.getElementById('loginForm').style.display = 'flex';
    document.getElementById('registerForm').style.display = 'none';
    document.querySelectorAll('.tab')[0].classList.add('active');
    document.querySelectorAll('.tab')[1].classList.remove('active');
}

function showRegister() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'flex';
    document.querySelectorAll('.tab')[0].classList.remove('active');
    document.querySelectorAll('.tab')[1].classList.add('active');
}

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            body: new URLSearchParams(formData)
        });
        
        const data = await response.json();
        const messageEl = document.getElementById('login-message');
        
        if (data.success) {
            messageEl.className = 'message success';
            messageEl.textContent = 'Login successful! Redirecting...';
            messageEl.style.display = 'block';
            setTimeout(() => {
                window.location.href = 'dashboard.html';
            }, 1000);
        } else {
            messageEl.className = 'message error';
            messageEl.textContent = data.message;
            messageEl.style.display = 'block';
        }
    } catch (error) {
        const messageEl = document.getElementById('login-message');
        messageEl.className = 'message error';
        messageEl.textContent = 'An error occurred. Please try again.';
        messageEl.style.display = 'block';
    }
});

document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    try {
        const response = await fetch('/api/register', {
            method: 'POST',
            body: new URLSearchParams(formData)
        });
        
        const data = await response.json();
        const messageEl = document.getElementById('register-message');
        
        if (data.success) {
            messageEl.className = 'message success';
            messageEl.textContent = 'Registration successful! Please login.';
            messageEl.style.display = 'block';
            setTimeout(() => {
                showLogin();
            }, 1500);
        } else {
            messageEl.className = 'message error';
            messageEl.textContent = data.message;
            messageEl.style.display = 'block';
        }
    } catch (error) {
        const messageEl = document.getElementById('register-message');
        messageEl.className = 'message error';
        messageEl.textContent = 'An error occurred. Please try again.';
        messageEl.style.display = 'block';
    }
});
