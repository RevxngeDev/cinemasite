<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SignIn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: 'Arial', sans-serif;
        }
        .signin-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            border: 1px solid #bb86fc;
            border-radius: 10px;
            background-color: #1f1f1f;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        .signin-header {
            text-align: center;
            margin-bottom: 20px;
        }
        .form-control, .btn-primary {
            border-radius: 30px;
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
        .alternative-option {
            text-align: center;
            margin-top: 20px;
        }
        .alternative-option a {
            color: #bb86fc;
            text-decoration: none;
        }
        .alternative-option a:hover {
            color: #e0e0e0;
        }
        .btn-google {
            background-color: #db4437;
            border: none;
            color: white;
            width: 100%;
            border-radius: 30px;
            margin-top: 10px;
        }
        .btn-google:hover {
            background-color: #c23321;
        }
        .forgot-password {
            text-align: center;
            margin-top: 20px;
        }
        .forgot-password a {
            color: #bb86fc;
            text-decoration: none;
        }
        .forgot-password a:hover {
            color: #e0e0e0;
        }
    </style>
</head>
<body>
<div class="signin-container">
    <div class="signin-header">
        <h2>Sign In</h2>
    </div>
    <form action="/signIn" method="post">
        <div class="mb-3">
            <label for="email" class="form-label">Email address</label>
            <input name="email" type="email" class="form-control" id="email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input name="password" type="password" class="form-control" id="password" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Submit</button>
    </form>
    <div class="alternative-option">
        <p>No tienes una cuenta? <a href="/signUp">Crea una</a>.</p>
        <button class="btn btn-google" role="link" onclick="window.location='/oauth2/authorization/google'">Sign in with Google</button>
    </div>
    <div class="forgot-password">
        <p><a href="/forgot-password">¿Olvidaste tu contraseña?</a></p>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
