<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add Film</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .film-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #e9ecef;
            border-radius: 10px;
            background-color: #f8f9fa;
            margin-top: 50px;
        }

        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="film-container">
        <h1 class="text-center">Add New Film</h1>
        <form id="filmForm" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Film Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="picture">Film Picture:</label>
                <input type="file" class="form-control-file" id="picture" name="picture" required>
            </div>
            <div class="form-group">
                <label for="type">Type:</label>
                <select class="form-control" id="type" name="type" required>
                    <option value="COMEDY">Comedy</option>
                    <option value="HORROR">Horror</option>
                    <option value="ROMANCE">Romance</option>
                    <option value="ANIMATED">Animated</option>
                    <option value="ACTION">Action</option>
                    <option value="FANTASY">Fantasy</option>
                </select>
            </div>
            <div class="form-group">
                <label for="overview">Overview:</label>
                <textarea class="form-control" id="overview" name="overview" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label for="youtubeLink">YouTube Link:</label>
                <input type="url" class="form-control" id="youtubeLink" name="youtubeLink" required>
            </div>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="text" id="price" name="price"><br>
            </div>
            <button type="button" class="btn btn-primary" id="submitButton">Add Film</button>
            <a class="btn btn-primary" href="/dashboard">Back to dashboard</a>
        </form>
    </div>
</div>
<div class="container mt-5">
    <h1 class="mb-4">Películas</h1>
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead class="table-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Picture</th>
                <th scope="col">Nombre</th>
                <th scope="col">Tipo</th>
                <th scope="col">Precio</th>
                <th scope="col">Acciones</th>
            </tr>
            </thead>
            <tbody>
            <#list filmsList as film>
                <tr>
                    <td>${film.id}</td>
                    <td>${film.picture}</td>
                    <td>${film.name}</td>
                    <td>${film.type}</td>
                    <td>${film.price}</td>
                    <td>
                        <form action="/films/${film.id}/delete" method="post" onsubmit="return confirm('¿Estás seguro de que quieres eliminar esta película?');">
                            <button type="submit" class="btn btn-danger">Eliminar</button>
                        </form>
                        <button type="button" class="btn btn-primary edit-button" data-toggle="modal" data-target="#editFilmModal" data-id="${film.id}">Edit</button>
                    </td>

                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<div class="modal fade" id="editFilmModal" tabindex="-1" role="dialog" aria-labelledby="editFilmModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editFilmModalLabel">Edit Film</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editFilmForm" enctype="multipart/form-data">
                    <input type="hidden" id="editFilmId" name="id" value="">
                    <div class="form-group">
                        <label for="editName">Film Name:</label>
                        <input type="text" class="form-control" id="editName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="editPicture">Film Picture:</label>
                        <input type="file" class="form-control-file" id="editPicture" name="picture">
                    </div>
                    <div class="form-group">
                        <label for="editType">Type:</label>
                        <select class="form-control" id="editType" name="type" required>
                            <option value="COMEDY">Comedy</option>
                            <option value="HORROR">Horror</option>
                            <option value="ROMANCE">Romance</option>
                            <option value="ANIMATED">Animated</option>
                            <option value="ACTION">Action</option>
                            <option value="FANTASY">Fantasy</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="editOverview">Overview:</label>
                        <textarea class="form-control" id="editOverview" name="overview" rows="3" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editYoutubeLink">YouTube Link:</label>
                        <input type="url" class="form-control" id="editYoutubeLink" name="youtubeLink" required>
                    </div>
                    <div class="form-group">
                        <label for="editPrice">Price:</label>
                        <input type="text" id="editPrice" name="price" required><br>
                    </div>
                    <button type="button" class="btn btn-primary" id="editFilmButton">Edit Film</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#submitButton").click(function () {
            var formData = new FormData($("#filmForm")[0]);
            $.ajax({
                url: "/addFilm",
                type: "POST",
                data: formData,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.status === "success") {
                        alert("Film added successfully!");
                        $("#filmForm")[0].reset();
                    } else {
                        alert("Error: " + data.message);
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error submitting the form: " + xhr.responseText);
                }
            });
        });
    });
    $(".edit-button").click(function () {
        var filmId = $(this).data("id");
        $.ajax({
            url: "/getFilmDetails/" + filmId,
            type: "GET",
            success: function (data) {
                $("#editFilmId").val(data.id);
                $("#editName").val(data.name);
                $("#editType").val(data.type);
                $("#editOverview").val(data.overview);
                $("#editYoutubeLink").val(data.youtubeLink);
                $("#editPrice").val(data.price);
            },
            error: function (xhr, status, error) {
                alert("Error retrieving film data: " + xhr.responseText);
            }
        });
    });
    $("#editFilmButton").click(function () {
        var formData = new FormData($("#editFilmForm")[0]);
        $.ajax({
            url: "/updateFilm/" + $("#editFilmId").val(),
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.status === "success") {
                    alert("Film updated successfully!");
                    location.reload();
                } else { alert("Error: " + data.message);
                }
            },
            error: function (xhr, status, error) {
                alert("Error updating the film: " + xhr.responseText);
            }
        });
    });
</script>
</body>
</html>
