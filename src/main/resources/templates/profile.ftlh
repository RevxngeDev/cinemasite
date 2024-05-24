<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
        }
        .profile-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #bb86fc;
            border-radius: 10px;
            background-color: #1f1f1f;
            margin-top: 50px;
        }
        .profile-image {
            max-width: 200px;
            height: auto;
            margin-bottom: 20px;
        }
        .btn-primary {
            background-color: #bb86fc;
            border-color: #bb86fc;
        }
        .btn-primary:hover {
            background-color: #e0e0e0;
            color: #121212;
            border-color: #e0e0e0;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="profile-container">
        <h1 class="text-center">${userName}</h1>
        <div class="text-center">
            <#if profilePicture??>
                <img class="profile-image" id="profilePicture" src="/profilePicture/${profilePicture}" alt="Profile Picture">
            <#else>
                <p>User has no profile picture.</p>
            </#if>
        </div>
        <form id="uploadForm" enctype="multipart/form-data">
            <div class="form-group">
                <label for="profilePicture">Profile Picture:</label>
                <input type="file" class="form-control-file" id="fileInput" name="file">
            </div>
            <button type="button" class="btn btn-primary" id="uploadButton">Upload</button>
        </form>
        <hr>
        <form>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" value="${userName}" readonly>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="first_name">First Name:</label>
                    <input type="text" class="form-control" id="first_name" value="${firstName}" readonly>
                </div>
                <div class="form-group col-md-6">
                    <label for="last_name">Last Name:</label>
                    <input type="text" class="form-control" id="last_name" value="${lastName}" readonly>
                </div>
            </div>
        </form>
        <p>"<-"<a href="/home">Volver</a>.</p>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    $(document).ready(function () {
        $("#uploadButton").click(function () {
            var formData = new FormData();
            var file = $("#fileInput")[0].files[0];
            formData.append("file", file);
            $.ajax({
                url: "/uploadProfilePicture",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.status === "success") {
                        window.location.reload();
                    } else {
                        console.error("Error: " + data.message);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("Error al enviar la solicitud: " + error);
                }
            });
        });
    });
</script>
</body>
</html>

