<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CinemaCity</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .card-custom {
            width: 18rem;
            height: 100%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .card-img-top-custom {
            height: 12rem;
            object-fit: cover;
        }
    </style>
</head>
<body>

<header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
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
                </ul>
                <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active btn btn-success" aria-current="page" href="/signIn">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/signUp">Sign Up</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<main>
    <div class="container">
        <h2>Bienvenido a CinemaCity</h2>
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
            <!-- Aquí se mostrarán las películas -->
        </div>

        <!-- Paginación -->
        <nav aria-label="Page navigation" id="paginationNav">
            <!-- Aquí se mostrará la paginación -->
        </nav>
    </div>
</main>

<footer class="footer mt-auto py-3 bg-body-tertiary text-center">
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

