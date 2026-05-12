<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login / Register</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/Styles.css">
</head>
<body>
<div class="container auth-container">

    <div id="loginCard" class="card auth-card">
        <div class="card-body">
            <h3 class="text-center mb-3">User Login</h3>
            <div class="mb-2">
                <label class="form-label">Email:</label>
                <input type="email" id="loginEmail" class="form-control" placeholder="Please enter your email">
            </div>
            <div class="mb-2">
                <label class="form-label">Password:</label>
                <input type="password" id="loginPassword" class="form-control" placeholder="Please enter your password">
            </div>
            <div id="loginError" class="text-danger text-center small"></div>
            <div class="text-center mt-3">
                <button id="loginBtn" class="btn btn-primary">Login</button>
                <button id="showRegisterBtn" class="btn btn-secondary">Register</button>
            </div>
        </div>
    </div>

    <div id="registerCard" class="card auth-card d-none">
        <div class="card-body">
            <h3 class="text-center mb-3">User Registration</h3>
            <div class="mb-2">
                <label class="form-label">Email:</label>
                <input type="email" id="regEmail" class="form-control">
            </div>
            <div class="mb-2">
                <label class="form-label">Name:</label>
                <input type="text" id="regName" class="form-control">
            </div>
            <div class="mb-2">
                <label class="form-label">Password:</label>
                <input type="password" id="regPassword" class="form-control">
            </div>
            <div id="registerError" class="text-danger text-center small"></div>
            <div class="text-center mt-3">
                <button id="registerBtn" class="btn btn-primary">Register</button>
                <button id="showLoginBtn" class="btn btn-secondary">Login</button>
            </div>
        </div>
    </div>

</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/javascript/LoginAndRegistrationAndMessageBoard.js"></script>
</body>
</html>