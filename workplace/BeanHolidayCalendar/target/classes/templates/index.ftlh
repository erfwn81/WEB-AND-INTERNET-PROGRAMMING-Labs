<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Holiday Calendar</title>
    <link rel="stylesheet" href="/css/Styles.css">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1>US Holidays</h1>

    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Holiday</th>
                <th>Update | Delete</th>
            </tr>
        </thead>
        <tbody>
            <#list holidays as h>
            <tr>
                <td>${h.formattedDate}</td>
                <td>${h.holiday}</td>
                <td>
                    <a href="/updateHoliday/${h.id}">Update</a>
                    |
                    <a href="/deleteHoliday/${h.id}">Delete</a>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>

    <!-- Inline style overrides Bootstrap so the white button always shows correctly -->
    <a href="/addHoliday"
       style="display:inline-block; padding:8px 28px; background:#ffffff; color:#000000;
              border:2px solid #555; border-radius:4px; font-size:1rem;
              text-decoration:none; margin-top:10px; cursor:pointer;">
        Add Holiday
    </a>
</div>
</body>
</html>
