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
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>

