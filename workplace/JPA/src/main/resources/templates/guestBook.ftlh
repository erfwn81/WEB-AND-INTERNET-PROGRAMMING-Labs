<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GuestBook</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>

<div class="page-wrapper-wide">

    <!-- Custom welcome message with the logged-in user's name (Task 5) -->
    <h2>Welcome ${userName}</h2>

    <!-- Action buttons -->
    <div class="welcome-actions">
        <a href="/guestbook/add" class="btn-blue">New Message</a>
        <a href="/logout" class="btn-blue">Logout</a>
    </div>

    <#-- Show table if there are messages, otherwise show "No Message" -->
    <#if messages?has_content>

        <table class="guestbook-table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Message</th>
                    <th>Date</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <#-- Iterate over all guestbook entries (Task 6: multiple rows + hover) -->
                <#list messages as entry>
                <tr>
                    <td class="name-col">${entry.user.name}</td>
                    <td>${entry.message}</td>
                    <td>${entry.formattedDate}</td>
                    <td>
                        <a href="/guestbook/edit/${entry.id}" class="btn-blue">Edit</a>
                    </td>
                    <td>
                        <!-- DELETE uses POST form to avoid accidental deletion via GET -->
                        <form class="delete-form"
                              method="post"
                              action="/guestbook/delete/${entry.id}">
                            <button type="submit" class="btn-blue">Delete</button>
                        </form>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>

    <#else>
        <div class="no-message">No Message</div>
    </#if>

</div>

</body>
</html>
