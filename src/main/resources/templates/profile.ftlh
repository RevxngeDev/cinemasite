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
        <h1 class="text-center" id="userName">${user.email}</h1>
        <div class="text-center">
            <img class="profile-image" id="profile-picture" src="/profile-picture" alt="Profile Picture">
        </div>
        <form id="upload-profile-picture-form">
            <div class="form-group">
                <label for="profilePicture">Profile Picture:</label>
                <input type="file" class="form-control-file" id="profilePicture" name="file">
            </div>
            <button type="submit" class="btn btn-primary">Upload</button>
        </form>
        <hr>
        <form>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" value="${user.email}" readonly>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="first_name">First Name:</label>
                    <input type="text" class="form-control" id="first_name" value="${user.firstName}" readonly>
                </div>
                <div class="form-group col-md-6">
                    <label for="last_name">Last Name:</label>
                    <input type="text" class="form-control" id="last_name" value="${user.lastName}" readonly>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        // Enviar el formulario de carga de imagen de perfil utilizando AJAX
        $("#upload-profile-picture-form").submit(function(event) {
            event.preventDefault(); // Evitar el envío del formulario por defecto

            var formData = new FormData(this);

            $.ajax({
                type: "POST",
                url: "/uploadProfilePicture",
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    console.log("Imagen de perfil cargada correctamente");
                    // Recargar la imagen de perfil después de la carga
                    loadProfilePicture();
                },
                error: function(xhr, status, error) {
                    console.error("Error al cargar la imagen de perfil: " + error);
                }
            });
        });

        // Función para cargar la imagen de perfil utilizando AJAX
        function loadProfilePicture() {
            $.ajax({
                type: "GET",
                url: "/profile-picture",
                success: function(data) {
                    $("#profile-picture").attr("src", URL.createObjectURL(data));
                },
                error: function(xhr, status, error) {
                    console.error("Error al cargar la imagen de perfil: " + error);
                }
            });
        }

        // Cargar la imagen de perfil al cargar la página
        loadProfilePicture();
    });
</script>
</body>
</html>
