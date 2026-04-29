<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
		<link rel="stylesheet" type="text/css" href="./assets/css/styles.css">
		<title>Birthday Calendar</title>
	</head>
	<body>
		<div class="center header">User Login</div>
		<br />
		<form action="Login" method="POST">
			<div class="divTable">
				<div class="divTableBody">
					<div class="divTableRow">
						<div class="divTableCell">
							<label for="email">Email:</label>
						</div>
						<div class="divTableCell">
							<input type="text" placeholder="Please enter your email" name="email" id="email" required />
						</div>
					</div>
					<div class="divTableRow">
						<div class="divTableCell">
							<label for="password">Password:</label>
						</div>
						<div class="divTableCell">
							<input type="password" placeholder="Please enter your password" name="password" id="password" required />
						</div>
					</div>
				</div>
			</div>
			<%
				if (request.getAttribute("systemMessage") == null || request.getAttribute("systemMessage") == "") {
					out.println("<br />");
				} else {
					out.println("<div class='systemMessage'>" + request.getAttribute("systemMessage") + "</div>");
				}
			%>
			<div class="center">
				<button type="submit" class="btn btn-primary">Login</button>
				<a href="Register" class="btn btn-primary">Register</a>
			</div>
		</form>
	</body>
</html>