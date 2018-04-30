<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>.:: Amigos de Don Bosco - Login ::.</title>
  <!-- Bootstrap core CSS-->
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">
  <script type="text/javascript">
      function validacion(){
        usuario = document.getElementById("usuario").value;
        password = document.getElementById("password").value;
        if( usuario == null || usuario.length == 0 || /^\s+$/.test(usuario) ) {
            alert('Ingrese su usuario');
            return false;
        }
        else if( password == null || password.length == 0 || /^\s+$/.test(password) ) {
            alert('Ingrese su contraseña');
            return false;
        }
        else return true;
      }
  </script>
</head>

<body class="bg-dark" >
  <div class="container">
    <div class="card card-login mx-auto mt-5">
        <div class="card-header"><center><strong>.:: Amigos de Don Bosco - Login ::.</strong></center></div>
        
      <div class="card-body">
          <form method="POST" action="login">
            <div class="form-group">
                <center><img src="img/DonBosco.png" width="150" height="200"></center>
            </div>
          <div class="form-group">
            <label for="usuario"><fmt:message key="label.usuario"/></label>
            <input class="form-control" name="usuario" id="usuario" type="text" aria-describedby="emailHelp" placeholder="<fmt:message key="label.phusuario"/>" required>
          </div>
          <div class="form-group">
            <label for="paswword"><fmt:message key="label.clave"/></label>
            <input class="form-control" name="password" id="password" type="password" placeholder="<fmt:message key="label.phclave"/>" required="required">
          </div>
            <a class="btn btn-primary btn-block" href="index.html" onclick="return validacion()"><fmt:message key="label.login"/></a>
        </form>
        <div class="text-center">
            <br>
            <label><strong><fmt:message key="label.olvidar"/></strong></label>
        </div>
      </div>
    </div>
  </div>
  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
</body>
</html>
