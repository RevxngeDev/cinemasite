<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${film.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/28eab93eea.js" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
        }
        .film-content {
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }
        .film-img {
            max-width: 300px;
            margin-right: 20px;
            margin-bottom: 20px;
        }
        .film-overview {
            max-width: 400px;
        }
        .comments-section {
            max-height: 300px;
            overflow-y: scroll;
            border: 1px solid #bb86fc;
            padding: 10px;
            margin-top: 20px;
            background-color: #1f1f1f;
        }
        .video {
            max-width: 800px;
            margin: auto;
        }
        .video iframe {
            width: 100%;
            height: 450px;
            border: none;
        }
        .seat-layout {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }
        .seat {
            margin: 5px;
        }
        .reservation-container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .screen {
            background-color: grey;
            height: 80px;
            width: 40%;
            margin: 15px auto;
            transform: perspective(1000px) rotateX(-30deg);
            box-shadow: 0 3px 10px rgba(255, 255, 255, 0.7);
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .reserve-btn-container {
            display: flex;
            justify-content: center;
            width: 100%;
        }
        .btn-primary, .btn-danger, .btn-success {
            background-color: #bb86fc;
            border-color: #bb86fc;
        }
        .btn-primary:hover, .btn-danger:hover, .btn-success:hover {
            background-color: #e0e0e0;
            color: #121212;
            border-color: #e0e0e0;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="film-content">
            <div class="text-center">
                <img src="/images/${film.picture}" class="img-fluid film-img" alt="${film.name}">
                <div id="like-dislike-buttons">
                    <#if userLikedFilm>
                        <button id="dislike-btn" class="btn btn-danger"><i class="fa-solid fa-thumbs-down"></i> Dislike</button>
                    <#else>
                        <button id="like-btn" class="btn btn-success"><i class="fa-solid fa-thumbs-up"></i> Like</button>
                    </#if>
                </div>
                <p class="mt-3"><span id="likes-count">${likesCount}</span> personas han dado "like" a esta película</p>
            </div>
            <div class="film-overview">
                <h1>${film.name}</h1>
                <p>${film.overView}</p>
                <p><strong>Type:</strong> ${film.type}</p>
                <a href="/home" class="btn btn-primary">Volver a la página principal</a>
            </div>
        </div>
    </div>
</div>

<div class="video text-center mt-5">
    <#if film.youtubeLink?has_content>
        <div class="mt-3">
            <iframe width="560" height="315" src="https://www.youtube.com/embed/${film.youtubeLink}" frameborder="0" allowfullscreen></iframe>
        </div>
    </#if>
</div>

<h2 class="text-center">${film.name}</h2>
<p class="text-center"><strong>Precio por asiento:</strong> $${film.price}</p>

<div class="reservation-container">
    <h3>Reserva de Asientos</h3>
    <p>Selecciona tus asientos:</p>
    <div class="screen">Screen</div>
    <form action="/reserve-seats" method="post">
        <input type="hidden" name="filmId" value="${film.id}">
        <input type="hidden" name="userId" value="${currentUser.id}">
        <div class="seat-layout">
            <#list 1..16 as seatId>
            <div class="seat">
                <label for="seat${seatId}" style="margin-bottom: 0;">${seatId}</label>
                <input type="checkbox" id="seat${seatId}" name="seats" value="${seatId}" style="margin-top: 0;" class="seat-checkbox"
                       data-price="${film.price}"
                       <#if reservedSeats?seq_contains(seatId)>disabled</#if>>
            </div>
            <#if seatId % 4 == 0>
        </div><div class="seat-layout">
            </#if>
            </#list>
        </div>
        <div class="reserve-btn-container">
            <button type="submit" class="btn btn-primary mt-3">Reservar</button>
        </div>
    </form>
</div>

<div class="total-container text-center mt-3">
    <h4>Total: $<span id="totalAmount">0.00</span></h4>
</div>

<div class="container mt-5">
    <h2>Comentarios</h2>
    <div class="comments-section" id="comments-section">
        <#list comments as comment>
            <div class="d-flex justify-content-between align-items-center comment" data-comment-id="${comment.id}">
                <div>
                    <p><strong>${comment.user.firstName} ${comment.user.lastName}</strong>:</p>
                    <p>${comment.text}</p>
                </div>
                <#if currentUser?has_content && currentUser.id == comment.user.id>
                    <button class="btn btn-danger btn-sm delete-comment-btn">Eliminar</button>
                </#if>
            </div>
        </#list>
    </div>

    <#if currentUser??>
        <form id="add-comment-form" class="mt-3">
            <textarea name="text" rows="3" class="form-control" required></textarea><br>
            <button type="submit" class="btn btn-primary">Añadir comentario</button>
        </form>
    </#if>
</div>
<script>
    $(document).ready(function () {
        function updateLikeDislikeButtons(liked) {
            if (liked) {
                $('#like-dislike-buttons').html('<button id="dislike-btn" class="btn btn-danger"><i class="fa-solid fa-thumbs-down"></i> Dislike</button>');
            } else {
                $('#like-dislike-buttons').html('<button id="like-btn" class="btn btn-success"><i class="fa-solid fa-thumbs-up"></i> Like</button>');
            }
        }

        $(document).on('click', '#like-btn', function () {
            $.post("/films/${film.id}/like", function (data) {
                if (data.status === "success") {
                    updateLikeDislikeButtons(true);
                    $('#likes-count').text(data.likesCount);
                }
            });
        });

        $(document).on('click', '#dislike-btn', function () {
            $.post("/films/${film.id}/dislike", function (data) {
                if (data.status === "success") {
                    updateLikeDislikeButtons(false);
                    $('#likes-count').text(data.likesCount);
                }
            });
        });

        $('#add-comment-form').submit(function (event) {
            event.preventDefault();
            var text = $(this).find('textarea[name="text"]').val();
            $.post("/films/${film.id}/comment", {text: text}, function (data) {
                if (data.status === "success") {
                    var comment = data.comment;
                    var newComment = '<div class="d-flex justify-content-between align-items-center comment" data-comment-id="' + comment.id + '">';
                    newComment += '<div><p><strong>' + comment.user.firstName + ' ' + comment.user.lastName + '</strong>:</p><p>' + comment.text + '</p></div>';
                    newComment += '<button class="btn btn-danger btn-sm delete-comment-btn">Eliminar</button></div>';
                    $('#comments-section').append(newComment);
                    $('#add-comment-form')[0].reset();
                }
            });
        });

        $(document).on('click', '.delete-comment-btn', function () {
            var commentId = $(this).closest('.comment').data('comment-id');
            $.post("/films/${film.id}/comments/" + commentId + "/delete", function (data) {
                if (data.status === "success") {
                    $('[data-comment-id="' + commentId + '"]').remove();
                }
            });
        });

        const checkboxes = document.querySelectorAll('.seat-checkbox');
        const totalAmountElement = document.getElementById('totalAmount');

        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', () => {
                let total = 0;
                checkboxes.forEach(cb => {
                    if (cb.checked) {
                        total += parseFloat(cb.getAttribute('data-price'));
                    }
                });
                totalAmountElement.textContent = total.toFixed(2);
            });
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


