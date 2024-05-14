<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Users</title>
</head>
<body>
<h1>Para los usuarios</h1>
<table>
    <tr>
        <th>id</th>
        <th>email</th>
        <th>name</th>
        <th>lastname</th>
    </tr>
    <#list usersList as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.email}</td>
            <td>${user.firstname}</td>
            <td>${user.lastname}</td>
        </tr>
    </#list>
    <br>
    <a href="/dashboard"> Volver al dashboard </a>
</table>
</body>
</html>