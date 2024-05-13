<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Profile</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .profile-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #e9ecef;
            border-radius: 10px;
            background-color: #f8f9fa;
            margin-top: 50px;
        }

        .profile-image {
            max-width: 200px; /* Ajusta el tamaño según lo necesites */
            height: auto;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="profile-container">
        <h1 class="text-center">${userName}</h1>
        <!-- Mostrar la imagen -->
        <div class="text-center">
            <#if profilePicture??>
            <img class="profile-image" src="/profilePicture/${profilePicture}" alt="Profile Picture">
            <#else>
                <p>User has no profile picture. </p>
            </#if>
        </div>
        <form action="/uploadProfilePicture" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="profilePicture">Profile Picture:</label>
                <input type="file" class="form-control-file" id="profilePicture" name="profilePicture">
            </div>
            <button type="submit" class="btn btn-primary">Upload</button>
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
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
