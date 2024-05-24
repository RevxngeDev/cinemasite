<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Reset Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: 'Arial', sans-serif;
        }
        .reset-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            border: 1px solid #bb86fc;
            border-radius: 10px;
            background-color: #1f1f1f;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        .reset-header {
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
    </style>
</head>
<body>
<div class="reset-container">
    <div class="reset-header">
        <h2>Reset Password</h2>
    </div>
    <form action="/reset-password" method="post">
        <input type="hidden" name="token" value="${token}">
        <div class="mb-3">
            <label for="password" class="form-label">New Password</label>
            <input name="password" type="password" class="form-control" id="password" required>
        </div>
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Confirm Password</label>
            <input name="confirmPassword" type="password" class="form-control" id="confirmPassword" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Reset Password</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
