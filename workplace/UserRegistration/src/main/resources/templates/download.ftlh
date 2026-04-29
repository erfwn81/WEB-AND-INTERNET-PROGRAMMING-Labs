<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Download</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body class="download-body">

    <div class="logout-bar">
        <a href="/logout" class="btn btn-sm btn-danger logout-btn">Logout</a>
    </div>

    <div class="download-container">
        <table class="table download-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>File Name</th>
                    <th>Preview</th>
                    <th>Download</th>
                </tr>
            </thead>
            <tbody>
                <#list images as img>
                <tr>
                    <td>${img?index + 1}</td>
                    <td>${img[0]}</td>
                    <td>
                        <img src="/${img[1]}" alt="${img[0]}" class="preview-img">
                    </td>
                    <td>
                        <a href="/download/file?file=${img[1]}" class="btn btn-primary btn-sm">
                            Download
                        </a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>

</body>
</html>