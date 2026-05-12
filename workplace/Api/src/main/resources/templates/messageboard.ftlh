is this loolking right??<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Message Board</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/Styles.css">
</head>
<body>
<nav class="navbar navbar-dark bg-primary">
    <div class="container-fluid">
        <span class="navbar-brand">Message Board</span>
        <span class="text-white">Welcome, <span id="currentUserName"></span>
            &nbsp;<button id="logoutBtn" class="btn btn-light btn-sm">Logout</button>
        </span>
    </div>
</nav>

<div class="container my-4">

    <div class="card mb-3">
        <div class="card-body">
            <h5 id="formTitle">New Message</h5>
            <input type="hidden" id="editingId" value="">
            <textarea id="messageContent" class="form-control mb-2" rows="3"
                      placeholder="Write a message..."></textarea>
            <button id="saveBtn" class="btn btn-primary">Post</button>
            <button id="cancelEditBtn" class="btn btn-secondary d-none">Cancel</button>
        </div>
    </div>

    <div id="messagesContainer"></div>

</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="/javascript/LoginAndRegistrationAndMessageBoard.js"></script>
</body>
</html>