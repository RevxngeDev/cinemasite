<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación de Reserva</title>
    <style>
        body {
            background-color: #121212;
            color: #e0e0e0;
            font-family: 'Arial', sans-serif;
        }
        .confirmation-container {
            max-width: 600px;
            margin: 100px auto;
            padding: 20px;
            border: 1px solid #bb86fc;
            border-radius: 10px;
            background-color: #1f1f1f;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }
        .confirmation-container ul {
            list-style-type: none;
            padding: 0;
        }
        .confirmation-container ul li {
            background-color: #1f1f1f;
            padding: 10px;
            margin: 5px 0;
            border-radius: 5px;
            border: 1px solid #bb86fc;
        }
    </style>
</head>
<body style="background-color: #121212; color: #e0e0e0; font-family: 'Arial', sans-serif;">
<div class="confirmation-container" style="max-width: 600px; margin: 100px auto; padding: 20px; border: 1px solid #bb86fc; border-radius: 10px; background-color: #1f1f1f; box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);">
    <h2 style="color: #bb86fc;">Confirmación de Reserva</h2>
    <p style="color: #e0e0e0;">Gracias por reservar sus asientos para <strong>${filmName}</strong>.</p>
    <p style="color: #e0e0e0;">Los asientos reservados son:</p>
    <ul style="list-style-type: none; padding: 0;">
        <#list seatIds as seatId>
            <li style="background-color: #1f1f1f; padding: 10px; margin: 5px 0; border-radius: 5px; border: 1px solid #bb86fc; color: #e0e0e0;">${seatId}</li>
        </#list>
    </ul>
</div>
</body>
</html>


