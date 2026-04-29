<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Message</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="page-wrapper">

    <!-- Title shows the logged-in user's name -->
    <h2>${userName}'s New Message</h2>

    <form method="post" action="/guestbook/add">

        <textarea class="msg-area" name="message"
                  placeholder="Write your message here..."></textarea>

        <#-- Validation error for empty message -->
        <#if messageError??>
            <div class="form-error">${messageError}</div>
        </#if>

        <div class="btn-center">
            <a href="/guestbook" class="btn-blue">Back</a>
            <button type="submit" class="btn-blue">Submit</button>
        </div>

    </form>
</div>

</body>
</html>
