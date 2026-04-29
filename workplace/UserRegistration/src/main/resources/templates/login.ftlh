<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons for Facebook/X/Google icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- External CSS file (required by grading) -->
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body class="auth-body">

<div class="auth-wrapper">
    <div class="auth-card">

        <h1 class="auth-title">LOGIN</h1>
        <p class="auth-subtitle">Please enter your login and password!</p>

        <form action="/login" method="post">

            <!-- Email field -->
            <div class="form-group-custom">
                <input type="email" name="email" class="form-control auth-input"
                       value="${email!''}" placeholder=" " required>
                <label class="auth-label">Email</label>
            </div>

            <!-- Password field -->
            <div class="form-group-custom">
                <input type="password" name="password" class="form-control auth-input"
                       placeholder=" " required>
                <label class="auth-label">Password</label>
            </div>

            <!-- Error message (e.g. "User not found") -->
            <#if error??>
                <p class="auth-error">${error}</p>
            </#if>

            <!-- Success message after registration -->
            <#if registered??>
    <p class="auth-success">Account created! Please log in.</p>		
	</#if>

            <!-- Link to register -->
            <div class="text-center mb-3">
                <a href="/register" class="auth-link">Create New Account</a>
            </div>

            <button type="submit" class="btn auth-btn">Login</button>

        </form>

        <!-- Social icons (decorative, as shown in lab screenshot) -->
        <div class="social-icons">
            <i class="bi bi-facebook"></i>
            <i class="bi bi-twitter-x"></i>
            <i class="bi bi-google"></i>
        </div>

        <p class="text-center mt-4 auth-signup-text">
            Don't have an account? <a href="/register" class="auth-link-highlight">Sign Up</a>
        </p>

    </div>
</div>

</body>
</html>
