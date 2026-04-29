<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet"
href="<%= request.getContextPath() %>/assets/css/Styles.css">

<style>
body { margin:0; padding:40px; background: linear-gradient(120deg, #2980b9, #8e44ad); font-family: Arial, Helvetica, sans-serif; min-height:100vh; box-sizing:border-box; }
h2 { font-size:60px; margin-bottom:20px; color:white; }
.login-container { margin-top:30px; max-width:600px; padding:30px; background-color:rgba(255,255,255,0.15); border-radius:8px; }
.form-row { margin-bottom:20px; display:flex; align-items:center; }
label { font-size:28px; font-weight:bold; color:white; width:200px; margin-right:20px; }
.text-input { font-size:24px; padding:12px; width:400px; border:2px solid black; border-radius:4px; box-sizing:border-box; }
input[type="submit"] { font-size:24px; padding:12px 30px; background-color:#1a73e8; color:white; border:none; border-radius:8px; cursor:pointer; margin-top:20px; }
input[type="submit"]:hover { background-color:#1558b0; }
.error-msg { color:#ff4444; font-size:24px; margin-bottom:15px; font-weight:bold; padding:15px; background-color:rgba(255,0,0,0.2); border-radius:4px; border-left:4px solid #ff4444; }
.success-msg { color:yellowgreen; font-size:24px; margin-bottom:15px; font-weight:bold; padding:15px; background-color:rgba(0,255,0,0.1); border-radius:4px; border-left:4px solid yellowgreen; }
</style>

</head>

<body>

<div class="page">

<h2>Login</h2>

<% if (request.getParameter("error") != null) { %>
<div class="error-msg">❌ Email and password does not match</div>
<% } %>

<form method="post" action="Login" class="login-container">

<div class="form-row">
<label>Email</label>
<input class="text-input" type="text" name="email" placeholder="Enter your email" required>
</div>

<div class="form-row">
<label>Password</label>
<input class="text-input" type="password" name="password" placeholder="Enter your password" required>
</div>

<input type="submit" value="Login">

</form>

<% if (request.getParameter("logout") != null) { %>
<div class="success-msg">✅ You are logged out. Please login again.</div>
<% } %>

</div>

</body>
</html>