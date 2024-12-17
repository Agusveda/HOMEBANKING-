<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GIF Ajustado</title>
<style>
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        background-color: #3f51b5;
    }
    .gif-container {
        position: relative;
        width: 500px; /* Tamaño más pequeño */
        height: 500px;
        overflow: hidden;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Agrega sombra */
        border-radius: 10px; /* Bordes redondeados */
    }
    .gif-container iframe {
        width: 100%;
        height: 100%;
        border: none;
    }
    /* Ocultar logo al hacer hover */
    .gif-container::after {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0);
        z-index: 2;
    }
</style>
</head>
<body>
    <div class="gif-container">
        <iframe src="https://giphy.com/embed/NyniJ2Nf2ZzlE8GYsl" allowFullScreen></iframe>
    </div>
</body>
</html>
