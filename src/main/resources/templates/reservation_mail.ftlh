<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmación de Reserva</title>
</head>
<body>
<h2>Confirmación de Reserva</h2>
<p>Gracias por reservar sus asientos para <strong>${filmName}</strong>.</p>
<p>Los asientos reservados son:</p>
<ul>
    <#list seatIds as seatId>
        <li>${seatId}</li>
    </#list>
</ul>
</body>
</html>
