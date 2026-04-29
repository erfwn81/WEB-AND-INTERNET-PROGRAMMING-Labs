<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <!-- Bootstrap 5 CDN -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <!-- External custom stylesheet (required by lab) -->
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="page-wrapper">
    <h2>Login</h2>

    <form method="post" action="/login">

        <table class="form-table">
            <tr>
                <td>Email</td>
                <td>
                    <input type="text" name="email"
                           value="${(email)!''}" />
                    <#if emailError??>
                        <span class="field-error">${emailError}</span>
                    </#if>
                </td>
            </tr>
            <tr>
                <td>Password</td>
                <td>
                    <input type="password" name="password" />
                    <#if passwordError??>
                        <span class="field-error">${passwordError}</span>
                    </#if>
                </td>
            </tr>
        </table>

        <#-- Login credential error shown below the fields -->
        <#if loginError??>
            <div class="form-error">${loginError}</div>
        </#if>

        <div class="btn-center">
            <button type="submit" class="btn-blue">Login</button>
            <a href="/register" class="btn-blue">Register</a>
        </div>

    </form>
</div>

</body>
</html>
