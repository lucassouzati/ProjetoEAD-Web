<%-- 
    Document   : loginProfessor
    Created on : 27/10/2009, 23:51:20
    Author     : lefoly
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Login Professor</title>
        <link rel="stylesheet" type="text/css" href="../login.css" />
    </head>
    <body background = "../Recursos/fundo7.jpg">
        <div id="divmodulos" >
            <h1 align="center">M�dulo Professor</h1>
                    <form action="CadProfessor?cmd=validar" method="post">
                        <div>
                            
                            <h2>Login: </h2>
                            <input type="text" name="login" />
                            <br>                            
                            <h2>Senha: </h2>
                            <input type="password" name="senha" />
                            <br>
                            <br>
                            <input type="submit" name="btLogar" value="Enviar" />
                            <a href="loginNovoProfessor.jsp"/>Ainda n�o tenho cadastro</a>
                        </div>
                    </form>
        </div>        
    </body>
</html>

