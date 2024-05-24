<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${film.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/28eab93eea.js" crossorigin="anonymous"></script>
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
                <div>
                    <#if userLikedFilm>
                        <form action="/films/${film.id}/dislike" method="post">
                            <button type="submit" class="btn btn-danger"><i class="fa-solid fa-thumbs-down"></i> Dislike</button>
                        </form>
                    <#else>
                        <form action="/films/${film.id}/like" method="post">
                            <button type="submit" class="btn btn-success"><i class="fa-solid fa-thumbs-up"></i> Like</button>
                        </form>
                    </#if>
                </div>
                <p class="mt-3">${likesCount} personas han dado "like" a esta película</p>
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
    <div class="comments-section">
        <#list comments as comment>
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <p><strong>${comment.user.firstName} ${comment.user.lastName}</strong>:</p>
                    <p>${comment.text}</p>
                </div>
                <#if currentUser?has_content && currentUser.id == comment.user.id>
                    <form action="/films/${film.id}/comments/${comment.id}/delete" method="post">
                        <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                    </form>
                </#if>
            </div>
        </#list>
    </div>

    <#if currentUser??>
        <form action="/films/${film.id}/comment" method="post" class="mt-3">
            <textarea name="text" rows="3" class="form-control" required></textarea><br>
            <button type="submit" class="btn btn-primary">Añadir comentario</button>
        </form>
    </#if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', (event) => {
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
</body>
</html>


