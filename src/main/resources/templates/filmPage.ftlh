<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${film.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Font Awesome -->
    <script src="https://kit.fontawesome.com/28eab93eea.js" crossorigin="anonymous"></script>
    <style>
        .film-content {
            display: flex;
            justify-content: center;
            align-items: flex-start; /* Alinea la parte superior de los elementos */
        }
        .film-img {
            max-width: 300px; /* Ajusta este valor según tus necesidades */
            margin-right: 20px; /* Ajusta el espacio entre la imagen y el texto si es necesario */
            margin-bottom: 20px;
        }
        .film-overview {
            max-width: 400px; /* Ajusta este valor según tus necesidades */
        }
        .comments-section {
            max-height: 300px; /* Ajusta este valor según tus necesidades */
            overflow-y: scroll;
            border: 1px solid #ccc;
            padding: 10px;
            margin-top: 20px;
        }
        .video {
            max-width: 800px; /* Ajusta el ancho máximo según tus preferencias */
            margin: auto; /* Centra horizontalmente */
        }

        .video iframe {
            width: 100%; /* Ajusta el ancho al 100% */
            height: 450px; /* Ajusta la altura según tus preferencias */
            border: none; /* Elimina el borde del iframe */
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
                            <button type="submit" class="btn btn-danger"><i class="fa-solid fa-thumbs-down"></i></i> Dislike</button>
                        </form>
                    <#else>
                        <form action="/films/${film.id}/like" method="post">
                            <button type="submit" class="btn btn-success"><i class="fa-solid fa-thumbs-up"></i> Like</button>
                        </form>
                    </#if>
                </div>
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
        <div class="mt-3">
            <a href="/tickets" class="btn btn-secondary">Buy a tickets!</a>
        </div>
    </#if>
</div>

<!-- Agregar título de la película -->
<h2>${film.name}</h2>

<!-- Formato de reserva de asientos -->
<div>
    <h3>Reserva de Asientos</h3>
    <p>Selecciona tus asientos:</p>
    <form action="/reserve-seats" method="post">
        <input type="hidden" name="filmId" value="${film.id}">
        <input type="hidden" name="userId" value="${currentUser.id}"> <!-- Asegúrate de que currentUser tenga el ID del usuario actual -->
        <div class="row">
            <#list 1..16 as seatId>
            <div class="col-2" style="margin-bottom: 10px;">
                <label for="seat${seatId}" style="margin-bottom: 0;">Asiento ${seatId}:</label>
                <input type="checkbox" id="seat${seatId}" name="seats" value="${seatId}" style="margin-top: 0;">
            </div>
            <#if seatId % 4 == 0>
        </div><div class="row">
            </#if>
            </#list>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Reservar</button>
    </form>
</div>

<div class="container mt-5">
    <h2>Comentarios</h2>
    <div class="comments-section">
        <#list comments as comment>
            <div>
                <p><strong>${comment.user.firstName} ${comment.user.lastName}</strong>:</p>
                <p>${comment.text}</p>
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
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

