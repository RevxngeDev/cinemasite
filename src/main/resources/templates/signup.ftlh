<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SignUp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<form action="/signUp" method="post">
    <div class="mb-3">
        <label for="email" class="form-label">Email address</label>
        <input name="email" class="form-control" id="email" >
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input name="password" class="form-control" id="password">
    </div>
    <div class="mb-3">
        <label for="firstname" class="form-label">First Name</label>
        <input name="firstname" class="form-control" id="firstname">
    </div>
    <div class="mb-3">
        <label for="lastname" class="form-label">Last Name</label>
        <input name="lastname" class="form-control" id="lastname">
    </div>
    <div class="mb-3">
        <label for="phone" class="form-label">Phone</label>
        <input name="phone" class="form-control" id="phone">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<p>Ya tienes una cuenta? <a href="/signIn">Inicia sesion</a>.</p>
</body>
</html>