<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bootstrap Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/css/bootstrap.min.css">
    <script src="https://kit.fontawesome.com/ae360af17e.js" crossorigin="anonymous"></script>
</head>
<script>
    const sidebarToggle = document.querySelector("#sidebar-toggle");
    sidebarToggle.addEventListener("click",function(){
        document.querySelector("#sidebar").classList.toggle("collapsed");
    });
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins&display=swap');

    /* Reset y ajustes generales */
    *,
    ::after,
    ::before {
        box-sizing: border-box;
    }

    body {
        font-family: 'Poppins', sans-serif;
        font-size: 0.875rem;
        margin: 0;
        overflow-y: scroll;
        opacity: 1;
    }

    a {
        cursor: pointer;
        text-decoration: none;
    }

    /* Estilo de la barra de navegación */
    .navbar-expand .navbar-nav {
        margin-left: auto;
    }

    /* Listas sin viñetas */
    li {
        list-style: none;
    }

    /* Títulos */
    h4 {
        font-size: 1.275rem;
        color: var(--bs-emphasis-color);
    }

    /* Diseño del sidebar */
    .wrapper {
        display: flex;
        width: 100%;
    }

    #sidebar {
        background: var(--bs-dark);
        max-width: 264px;
        min-width: 264px;
        transition: all 0.35s ease-in-out;
    }

    .main {
        background: var(--bs-dark-bg-subtle);
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        min-width: 0;
        overflow: hidden;
        transition: all 0.35s ease-in-out;
        width: 100%;
    }

    /* Estilo de los elementos del sidebar */
    .sidebar-logo {
        padding: 1.15rem;
    }

    .sidebar-logo a {
        color: #e9ecef;
        font-size: 1.15rem;
        font-weight: 600;
    }

    /* Aquí va el resto de tu CSS... */
</style>
<body>
<div class="wrapper">
    <aside id="sidebar" class="js-sidebar">
        <!-- Content For Sidebar -->
        <div class="h-100">
            <div class="sidebar-logo">
                <a href="#">Cine</a>
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-header">
                    Admin Elements
                </li>
                <li class="sidebar-item">
                    <a href="#" class="sidebar-link">
                        <i class="fa-solid fa-list pe-2"></i>
                        Dashboard
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="/films" class="sidebar-link">
                        <i class="fa-solid fa-file-lines pe-2"></i>
                        Films
                    </a>
                </li>
                <li class="sidebar-item">
                    <a href="/users" class="sidebar-link">
                        <i class="fa-regular fa-user pe-2"></i>
                        Users
                    </a>
                </li>
            </ul>
        </div>
    </aside>
    <div class="main">
        <nav class="navbar navbar-expand px-3 border-bottom">
            <button class="btn" id="sidebar-toggle" type="button">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse navbar">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a href="#" data-bs-toggle="dropdown" class="nav-icon pe-md-0">
                            <img src="image/profile.jpg" class="avatar img-fluid rounded" alt="">
                        </a>
                        <div class="dropdown-menu dropdown-menu-end">
                            <a href="#" class="dropdown-item">Profile</a>
                            <a href="#" class="dropdown-item">Setting</a>
                            <a href="#" class="dropdown-item">Logout</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        <main class="content px-3 py-2">
            <div class="container-fluid">
                <div class="mb-3">
                    <h4>Admin Dashboard</h4>
                </div>
                <div class="row">
                    <div class="col-12 col-md-6 d-flex">
                        <div class="card flex-fill border-0 illustration">
                            <div class="card-body p-0 d-flex flex-fill">
                                <div class="row g-0 w-100">
                                    <div class="col-6">
                                        <div class="p-3 m-1">
                                            <h4>Welcome Back, Admin</h4>
                                            <p class="mb-0">Admin Dashboard, CodzSword</p>
                                        </div>
                                    </div>
                                    <div class="col-6 align-self-end text-end">
                                        <img src="image/customer-support.jpg" class="img-fluid illustration-img"
                                             alt="">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <footer class="footer">
            <div class="container-fluid">
                <div class="row text-muted">
                    <div class="col-6 text-start">
                        <p class="mb-0">
                            <a href="#" class="text-muted">
                                <strong>CodzSwod</strong>
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/script.js"></script>
</body>

</html>