<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Add Film</title>
    <!-- Bootstrap CSS -->
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
                </select>
            </div>
            <div class="form-group">
                <label for="overview">Overview:</label>
                <textarea class="form-control" id="overview" name="overview" rows="3" required></textarea>
            </div>
            <button type="button" class="btn btn-primary" id="submitButton">Add Film</button>
            <a class="btn btn-primary" href="/dashboard">Back to dashboard</a>
        </form>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
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
</script>
</body>
</html>
