<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: aliceblue;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Users</h1>
    <div class="mb-3">
        <form action="/users" method="get" class="d-flex mb-3">
            <select name="searchType" class="form-select me-2">
                <option value="name">Nombre</option>
                <option value="email">Email</option>
            </select>
            <input type="text" name="q" class="form-control me-2" placeholder="Buscar...">
            <button type="submit" class="btn btn-primary">Buscar</button>
        </form>
        <a href="/users?sort=asc" class="btn btn-primary">Ordenar por más antiguos</a>
        <a href="/users?sort=desc" class="btn btn-primary">Ordenar por más recientes</a>
    </div>
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead class="table-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Email</th>
                <th scope="col">Nombre</th>
                <th scope="col">Apellido</th>
                <th scope="col">Acciones</th>
            </tr>
            </thead>
            <tbody>
            <#list usersList as user>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>
                        <form action="/users/${user.id}/delete" method="post" class="d-inline">
                            <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <a href="/dashboard" class="btn btn-secondary mt-3">Volver al dashboard</a>
</div>

<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>
