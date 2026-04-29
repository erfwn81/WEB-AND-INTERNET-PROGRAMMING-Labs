<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body class="error-body">
    <div class="error-container">
        <p class="error-message">An error occurred (${statusCode!500})</p>
        <a href="/login" class="btn btn-outline-light mt-3">Go Back to Login</a>
    </div>
</body>
</html>
