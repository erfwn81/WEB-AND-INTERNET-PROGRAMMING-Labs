<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body class="auth-body">

<div class="auth-wrapper">
    <div class="auth-card">

        <h1 class="auth-title">REGISTER</h1>

        <form action="/register" method="post">

            <!-- Email -->
            <div class="form-group-custom">
                <input type="email" name="email" class="form-control auth-input"
                       value="${email!''}" placeholder=" ">
                <label class="auth-label">Email</label>
            </div>

            <!-- Name -->
            <div class="form-group-custom">
                <input type="text" name="name" class="form-control auth-input"
                       value="${name!''}" placeholder=" ">
                <label class="auth-label">Name</label>
            </div>

            <!-- Password -->
            <div class="form-group-custom">
                <input type="password" name="password" class="form-control auth-input"
                       placeholder=" ">
                <label class="auth-label">Password</label>
            </div>

            <!-- Validation / error message -->
            <#if error??>
                <p class="auth-error">${error}</p>
            </#if>

            <button type="submit" class="btn auth-btn">Register</button>

        </form>

        <p class="text-center mt-4 auth-signup-text">
            Already have an account? <a href="/login" class="auth-link-highlight">Login</a>
        </p>

    </div>
</div>

</body>
</html>
