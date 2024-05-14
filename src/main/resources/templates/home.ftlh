<!DOCTYPE html>
<html>
<head>
    <title>CinemaCity</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        .card-custom {
            width: 18rem; /* Ajusta este valor según tus necesidades */
        }
        .card-img-top-custom {
            height: 24rem; /* Ajusta este valor según tus necesidades */
            object-fit: cover; /* Mantiene la relación de aspecto de la imagen */
        }
        .card-body-custom {
            text-align: center; /* Centra el texto del título */
        }
    </style>
</head>
<body>
<!-- Header -->
<header>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
                <a class="navbar-brand" href="#">CinemaCity</a>
                <ul class="navbar-nav flex-grow-1 justify-content-center mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active text-center" aria-current="page" href="#">Home</a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/signIn">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active btn btn-success" aria-current="page" href="/signUp">Sign Up</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<!-- Contenido principal -->
<main>
    <div class="container">
        <h2>Bienvenido a CinemaCity</h2>
        <div class="row">
            <#list films as film>
                <div class="col-md-4 d-flex justify-content-center mb-4">
                    <a href="/films/${film.id}" class="text-decoration-none">
                        <div class="card card-custom shadow-sm">
                            <img src="/images/${film.picture}" class="card-img-top card-img-top-custom" alt="${film.name}">
                            <div class="card-body card-body-custom">
                                <h5 class="card-title">${film.name}</h5>
                            </div>
                        </div>
                    </a>
                </div>
            </#list>
        </div>
    </div>
</main>

<!-- Footer -->
<footer class="footer mt-auto py-3 bg-body-tertiary text-center">
    <div class="container">
        <span class="text-muted">© 2024 CinemaCity. Todos los derechos reservados.</span>
    </div>
</footer>
</body>
</html>

