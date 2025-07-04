<%-- 
    Document   : login
    Created on : 20 jun. 2025, 00:01:56
    Author     : User
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Iniciar Sesión</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #a1c4fd, #c2e9fb);
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-box {
            display: flex;
            width: 880px;
            max-width: 95%;
            background-color: #fff;
            border-radius: 20px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.2);
            overflow: hidden;
            transition: transform 0.3s ease;
        }

        .login-box:hover {
            transform: translateY(-5px);
        }

        .image-side {
            width: 50%;
            background: url('imagenes/fondo-login.jpg') center center no-repeat;
            background-size: cover;
            filter: brightness(0.85);
        }

        .form-side {
            width: 50%;
            padding: 50px 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            background-color: #fcfcfc;
        }

        .form-side h2 {
            text-align: center;
            margin-bottom: 25px;
            font-size: 26px;
            color: #2c3e50;
        }

        .form-side input {
            width: 100%;
            padding: 14px;
            margin: 12px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
        }

        .form-side input[type="submit"] {
            background-color: #3498db;
            color: white;
            font-weight: bold;
            border: none;
            transition: background-color 0.3s;
        }

        .form-side input[type="submit"]:hover {
            background-color: #2c80b4;
        }

        .error {
            color: red;
            font-size: 13px;
            margin-top: 5px;
            text-align: center;
        }

        .extra-links {
            text-align: center;
            margin-top: 20px;
        }

        .extra-links a {
            text-decoration: none;
            color: #2980b9;
            font-weight: bold;
        }

        .extra-links a:hover {
            text-decoration: underline;
        }

        .social-login {
            margin-top: 25px;
        }

        .social-login button {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 100%;
            padding: 10px;
            margin: 6px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-weight: bold;
            cursor: pointer;
            font-size: 14px;
            background-color: #fff;
            transition: background-color 0.3s ease;
        }

        .social-login button:hover {
            background-color: #f1f1f1;
        }

        .social-login img {
            width: 20px;
            height: 20px;
            margin-right: 10px;
        }

        @media (max-width: 768px) {
            .login-box {
                flex-direction: column;
                width: 95%;
            }

            .image-side {
                height: 200px;
                width: 100%;
            }

            .form-side {
                width: 100%;
                padding: 40px 20px;
            }
        }
    </style>
</head>
<body>

<div class="login-box">
    <div class="image-side"></div>

    <div class="form-side">
        <h2>Iniciar Sesión</h2>
        <form action="LoginServlet" method="post">
            <input type="email" name="correo" placeholder="Correo electrónico" required />
            <input type="password" name="clave" placeholder="Contraseña" required />
            <input type="submit" value="Ingresar" />
            <p class="error">${error != null ? error : ""}</p>
        </form>

        <div class="extra-links">
            <p>¿Eres nuevo? <a href="UsuarioServlet?accion=nuevo&esCliente=true">Regístrate</a></p>
        </div>

        <div class="social-login">
            <button class="google">
                <img src="${pageContext.request.contextPath}/imagenes/google-icon.png" alt="Google Icon" />
                Ingresar con Google
            </button>
            <button class="facebook">
                <img src="${pageContext.request.contextPath}/imagenes/facebook-icon.png" alt="Facebook Icon" />
                Ingresar con Facebook
            </button>
            <button class="microsoft">
                <img src="${pageContext.request.contextPath}/imagenes/microsoft-icon.png" alt="Microsoft Icon" />
                Ingresar con Microsoft
            </button>
        </div>
    </div>
</div>

</body>
</html>
