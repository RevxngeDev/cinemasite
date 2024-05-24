<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>SignUp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: 'Arial', sans-serif;
        }
        .signup-container {
            max-width: 500px;
            margin: 100px auto;
            padding: 20px;
            border: 1px solid #bb86fc;
            border-radius: 10px;
            background-color: #1f1f1f;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        .signup-header {
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
    </style>
</head>
<body>
<div class="signup-container">
    <div class="signup-header">
        <h2>Sign Up</h2>
    </div>
    <form action="/signUp" method="post">
        <div class="mb-3">
            <label for="email" class="form-label">Email address</label>
            <input name="email" type="email" class="form-control" id="email" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input name="password" type="password" class="form-control" id="password" required>
        </div>
        <div class="mb-3">
            <label for="firstname" class="form-label">First Name</label>
            <input name="firstname" type="text" class="form-control" id="firstname" required>
        </div>
        <div class="mb-3">
            <label for="lastname" class="form-label">Last Name</label>
            <input name="lastname" type="text" class="form-control" id="lastname" required>
        </div>
        <div class="mb-3">
            <label for="phone" class="form-label">Phone</label>
            <input name="phone" type="text" class="form-control" id="phone" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Submit</button>
    </form>
    <div class="alternative-option">
        <p>Ya tienes una cuenta? <a href="/signIn">Inicia sesión</a>.</p>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>