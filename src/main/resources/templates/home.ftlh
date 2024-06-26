<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CinemaCity</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
        }
        .navbar, .footer {
            background-color: #1f1f1f;
        }
        .navbar-brand, .nav-link {
            color: #e0e0e0 !important;
        }
        .navbar-brand:hover, .nav-link:hover {
            color: #bb86fc !important;
        }
        .btn-outline-primary {
            border-color: #bb86fc;
            color: #bb86fc;
        }
        .btn-outline-primary:hover {
            background-color: #bb86fc;
            color: #121212;
        }
        .card-custom {
            background-color: #1f1f1f;
            color: #e0e0e0;
            border: 1px solid #bb86fc;
        }
        .card-img-top-custom {
            height: 25rem;
            object-fit: cover;
        }
        .carousel-image-wrapper {
            position: relative;
            height: 600px;
        }
        .carousel-image-wrapper img {
            filter: brightness(1.0);
            height: 100%;
            width: 100%;
            object-fit: cover;
        }
        .carousel-caption h5 {
            font-size: 2.5rem;
            font-weight: bold;
            color: #bb86fc;
        }
        .carousel-image-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(to right, rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0));
        }
        .carousel-caption p {
            font-size: 1.2rem;
            color: #e0e0e0;
        }
        .carousel-caption h5 {
            font-size: 3rem;
            font-weight: bold;
        }
        .carousel-caption {
            left: 15%;
            text-align: left;
        }
        .carousel-control-prev-icon, .carousel-control-next-icon {
            filter: invert(1);
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
        .input-group .form-control, .input-group .form-select {
            background-color: #1f1f1f;
            color: #e0e0e0;
            border: 1px solid #bb86fc;
        }
        .pagination .page-link {
            background-color: #1f1f1f;
            color: #e0e0e0;
            border: 1px solid #bb86fc;
        }
        .pagination .page-link:hover {
            background-color: #bb86fc;
            color: #121212;
        }
        .pagination .active .page-link {
            background-color: #bb86fc;
            border-color: #bb86fc;
        }
    </style>
</head>
<body>

<header>
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <a class="navbar-brand" href="/home">CinemaCity</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/home">Home</a>
                    </li>
                    <#if role == 'ADMIN'>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/dashboard">Dashboard</a>
                        </li>
                    </#if>
                </ul>
                </ul>
                <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <span class="nav-link active">
                            <#if userName??>
                                <#if profilePicture??>
                                    <img src="/profilePicture/${profilePicture}" alt="Profile Picture" style="width: 35px; height: 35px; border-radius: 50%;">
                                </#if>
                                Bienvenido, ${userName}
                            </#if>
                        </span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-outline-primary" aria-current="page" href="/profile">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/logout">logOut</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<main>
    <h2 class="text-center my-4">Top 3 Películas</h2>
    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <#assign index = 0>
            <#list topFilms as film>
                <#if index < 3>
                    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="${index}" class="<#if index == 0>active</#if>" aria-current="true" aria-label="Slide ${index + 1}"></button>
                    <#assign index = index + 1>
                </#if>
            </#list>
        </div>
        <div class="carousel-inner">
            <#assign index = 0>
            <#list topFilms as film>
                <#if index < 3>
                    <div class="carousel-item <#if index == 0>active</#if>">
                        <div class="carousel-image-wrapper">
                            <img src="/images/${film.picture}" class="d-block w-100" alt="${film.name}">
                            <div class="carousel-image-overlay"></div>
                        </div>
                        <div class="carousel-caption d-none d-md-block">
                            <h5>${film.name}</h5>
                            <p>${film.overView}</p>
                            <a href="/films/${film.id}" class="btn btn-primary">Ver Película</a>
                        </div>
                    </div>
                    <#assign index = index + 1>
                </#if>
            </#list>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <div class="container my-5">
        <h2 class="text-center mb-4">Bienvenido a CinemaCity</h2>
        <form id="searchForm" class="mb-3">
            <div class="input-group">
                <input type="text" class="form-control" id="searchInput" placeholder="Buscar películas...">
                <select id="genreSelect" class="form-select">
                    <option value="ALL">Todos los géneros</option>
                    <option value="COMEDY">Comedia</option>
                    <option value="HORROR">Horror</option>
                    <option value="ROMANCE">Romance</option>
                    <option value="ANIMATED">Animada</option>
                    <option value="ACTION">Acción</option>
                    <option value="FANTASY">Fantasía</option>
                </select>
                <button class="btn btn-outline-primary" type="submit">Buscar</button>
            </div>
        </form>
        <div class="row" id="filmList">
        </div>

        <nav aria-label="Page navigation" id="paginationNav">
        </nav>
    </div>
</main>

<footer class="footer mt-auto py-3 text-center">
    <div class="container">
        <span class="text-muted">© 2024 CinemaCity. Todos los derechos reservados.</span>
    </div>
</footer>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    $(document).ready(function() {
        loadFilms(0);

        // Manejo del formulario de búsqueda
        $('#searchForm').submit(function(event) {
            event.preventDefault();
            let query = $('#searchInput').val().trim();
            let genre = $('#genreSelect').val();
            searchFilms(query, genre, 0);
        });
    });

    function loadFilms(page) {
        $.get("/films-partial?page=" + page, function(data) {
            $("#filmList").html(renderFilms(data.films));
            updatePagination(data.currentPage, data.totalPages);
        });
    }

    function renderFilms(films) {
        let html = '';
        for (let i = 0; i < films.length; i++) {
            let film = films[i];
            html += '<div class="col-md-4 d-flex justify-content-center mb-4">';
            html += '<a href="/films/' + film.id + '" class="text-decoration-none">';
            html += '<div class="card card-custom shadow-sm">';
            html += '<img src="/images/' + film.picture + '" class="card-img-top card-img-top-custom" alt="' + film.name + '">';
            html += '<div class="card-body card-body-custom">';
            html += '<h5 class="card-title">' + film.name + '</h5>';
            html += '</div></div></a></div>';
        }
        return html;
    }

    function updatePagination(currentPage, totalPages) {
        let pagination = $("#paginationNav");
        pagination.empty();

        let prevDisabled = (currentPage === 0) ? 'disabled' : '';
        let nextDisabled = (currentPage === totalPages - 1) ? 'disabled' : '';

        let paginationHtml = '<ul class="pagination justify-content-center">';
        paginationHtml += '<li class="page-item ' + prevDisabled + '">';
        paginationHtml += '<a class="page-link" href="#" onclick="loadFilms(' + (currentPage - 1) + ')" aria-label="Previous">';
        paginationHtml += '<span aria-hidden="true">&laquo;</span></a></li>';

        for (let i = 0; i < totalPages; i++) {
            let active = (i === currentPage) ? 'active' : '';
            paginationHtml += '<li class="page-item ' + active + '">';
            paginationHtml += '<a class="page-link" href="#" onclick="loadFilms(' + i + ')">' + (i + 1) + '</a></li>';
        }

        paginationHtml += '<li class="page-item ' + nextDisabled + '">';
        paginationHtml += '<a class="page-link" href="#" onclick="loadFilms(' + (currentPage + 1) + ')" aria-label="Next">';
        paginationHtml += '<span aria-hidden="true">&raquo;</span></a></li></ul>';

        pagination.html(paginationHtml);
    }

    function searchFilms(query, genre, page) {
        $.get("/film/search?q=" + query + "&genre=" + genre + "&page=" + page, function(data) {
            $("#filmList").html(renderFilms(data.films));
            updatePagination(data.currentPage, data.totalPages);
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error fetching films: ", textStatus, errorThrown);
        });
    }
</script>
</body>
</html>