<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="page-wrapper">
    <h2>Register</h2>

    <form method="post" action="/register">

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
                <td>Name</td>
                <td>
                    <input type="text" name="name"
                           value="${(name)!''}" />
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

        <#-- Already registered error shown below fields -->
        <#if registerError??>
            <div class="form-error">${registerError}</div>
        </#if>

        <div class="btn-center">
            <a href="/" class="btn-blue">Login</a>
            <button type="submit" class="btn-blue">Register</button>
        </div>

    </form>
</div>

</body>
</html>
