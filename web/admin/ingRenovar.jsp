<%-- 
    Document   : ingRenovar
    Created on : 14-may-2018, 22:27:31
    Author     : Susan
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*,java.text.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<% 
    DateFormat df = new SimpleDateFormat("dd/MM/yy");
    String formattedDate = df.format(new Date());
    if(session.getAttribute("UsuarioActual")== null){
    response.sendRedirect("../login.jsp");
   }else{
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>.:: Amigos de Don Bosco - <fmt:message key="label.titleAdmin"/> ::.</title>
  <!-- Bootstrap core CSS-->
  <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Page level plugin CSS-->
  <link href="../vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <!-- Custom styles for this template-->
  <link href="../css/sb-admin.css" rel="stylesheet">
  <link href="css/sb-admin.css" rel="stylesheet">
  <script type="text/javascript">
      function validacion(){
        var chks=document.getElementsByName('idValue[]');
        var hasChecked=false;
        for (var i = 0; i < chks.length; i++) {
             if(chks[i].checked){
                 hasChecked=true;
                 break;
             }
        }
        if(hasChecked==false){
         alert('Seleccione al menos un préstamo');
         return false;
        }
        else return true;
      }
  </script>
</head>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
      <a class="navbar-brand" href="#"><fmt:message key="label.prestamos"/> - <fmt:message key="label.titleAdmin"/></a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Principal">
          <a class="nav-link" href="http://localhost:8083/BibliotecaWeb/admin/homeAdmin.jsp">
            <i class="fa fa-fw fa-dashboard"></i>
            <span class="nav-link-text"><fmt:message key="label.main"/></span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Menu Levels">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMulti" data-parent="#exampleAccordion">
            <i class="fa fa-fw fa-book"></i>
            <span class="nav-link-text"><fmt:message key="label.catalogo"/></span>
          </a>
          <ul class="sidenav-second-level collapse" id="collapseMulti">
            <li>
              <a href="#"><fmt:message key="label.ingreso"/></a>
            </li>
            <li>
              <a href="#"><fmt:message key="label.consultar"/></a>
            </li>
          </ul>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Users">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMultiples" data-parent="#exampleAccordion">
            <i class="fa fa-fw fa-users"></i>
            <span class="nav-link-text"><fmt:message key="label.usuarios"/></span>
          </a>
          <ul class="sidenav-second-level collapse" id="collapseMultiples">
            <li>
              <a href="#"><fmt:message key="label.ingreso"/></a>
            </li>
            <li>
              <a href="#"><fmt:message key="label.consultar"/></a>
            </li>
          </ul>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Prestamos">
          <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseMultipleP" data-parent="#exampleAccordion">
            <i class="fa fa-fw fa-bookmark"></i>
            <span class="nav-link-text"><fmt:message key="label.prestamos"/></span>
          </a>
          <ul class="sidenav-second-level collapse" id="collapseMultipleP">
            <li>
              <a href="http://localhost:8083/BibliotecaWeb/admin/regPrestamo.jsp"><fmt:message key="label.ingreso"/> <fmt:message key="label.prestamos"/></a>
            </li>
            <li>
              <a href="http://localhost:8083/BibliotecaWeb/admin/regRenovar.jsp"><fmt:message key="label.renovar"/> <fmt:message key="label.prestamos"/></a>
            </li>
            <li>
              <a href="http://localhost:8083/BibliotecaWeb/admin/regDevolucion.jsp"><fmt:message key="label.devolver"/> <fmt:message key="label.prestamos"/></a>
            </li>
            <li>
              <a href="http://localhost:8083/BibliotecaWeb/cargarP"><fmt:message key="label.consultar"/> <fmt:message key="label.prestamos"/></a>
            </li>
          </ul>
        </li>
      </ul>
      <ul class="navbar-nav sidenav-toggler">
        <li class="nav-item">
          <a class="nav-link text-center" id="sidenavToggler">
            <i class="fa fa-fw fa-angle-left"></i>
          </a>
        </li>
      </ul>
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
            <i class="fa fa-fw fa-sign-out"></i><fmt:message key="label.logout"/> (${UsuarioActual.nombre} ${UsuarioActual.apellido})</a>
        </li>
      </ul>
    </div>
  </nav>
  <div class="content-wrapper">
    <div class="container-fluid">
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#">Dashboard</a>
        </li>
        <li class="breadcrumb-item active"><fmt:message key="label.titleR"/></li>
      </ol>
    </div>
      <c:if test="${not empty ErrorUser}">
                <div class="form-group">
                    <div class="alert alert-danger">
                        <strong>ERROR! </strong><fmt:message key="label.errorUsuario"/>
                    </div>
                </div>
     </c:if>
      <div class="container-fluid">
          <form method="post" action="http://localhost:8083/BibliotecaWeb/ingresarR" onsubmit="return validacion()">
              <div class="form-group">
                  <center><h4>Información Usuario &nbsp;</h4></center>
                <div class="breadcrumb">
                    <div class="col-10">
                        <table>
                            <tr><td><h6><strong>ID Biblioteca</strong></td><td> ${Usuario.idUsuario}</h6></td></tr>
                            <tr><td><h6><strong>Carnet</strong></td><td> ${Usuario.carnet}</h6></td></tr>
                            <tr><td><h6><strong>Nombres</strong></td><td> ${Usuario.nombre}</h6></td></tr>
                            <tr><td><h6><strong>Apellidos</strong></td><td> ${Usuario.apellido}</h6></td></tr>
                            <tr><td><h6><strong>Categor&iacute;a</strong></td><td> ${Usuario.categoria}</h6></td></tr>
                            <tr><td><h6><strong>Mora</strong></td><td> ${Usuario.mora}</h6></td></tr>
                            <tr><td><h6><strong>Cantidad M&aacute;xima Pr&eacute;stamos</strong></td><td> ${Usuario.maxPrestamos}</h6></td></tr>
                            <tr><td><h6><strong>Total pr&eacute;stamos</strong></td><td> ${Usuario.totalPrestamos}</h6></td></tr>
                        </table>
                        <c:if test="${Usuario.totalPrestamos >0}">
                    <div class="card-body">
                        <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                            <th>Seleccionar</th>
                            <th>ID Ejemplar</th>
                            <th>T&iacute;tulo</th>
                            <th>Fecha Pr&eacute;stamo</th>
                            <th>Fecha Devoluci&oacute;n</th>
                            </tr>
                        </thead>
                         <c:forEach items="${Ejemplar.arrayCatalogo}" var="object">
                             <tr> 
                            
                               <td><input type="checkbox" name="idValue[]" value="${object.idPrestamo}"></td>
                                <td>${object.idEjemplar}</td>
                                <td>${object.titulo}</td>
                                <td>${object.fechaPrestamo}</td>
                                <td>${object.fechaDevolucion}</td>                        
                             </tr>
                          </c:forEach>
                        </table>
                        </div>
                    </div>
                    </c:if>
                    </div>
                    
                </div>
              </div>
                <input type="hidden" name="carnet" value="${Usuario.carnet}" />
            <input class="btn btn-primary btn-block" type="submit" value="Renovar"></input>
          </form>
                        
    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
            <small>Copyright © Biblioteca Amigos de Don Bosco 2018</small>
        </div>
      </div>
    </footer>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa fa-angle-up"></i>
    </a>
    <!-- Logout Modal-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="label.ready"/></h5>
            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <div class="modal-body"><fmt:message key="label.confirm"/></div>
          <div class="modal-footer">
            <button class="btn btn-secondary" type="button" data-dismiss="modal"><fmt:message key="label.cancel"/></button>
            <a class="btn btn-primary" href="http://localhost:8083/BibliotecaWeb/logout"><fmt:message key="label.logout"/></a>
          </div>
        </div>
      </div>
    </div>
    <!-- Bootstrap core JavaScript-->
    <script src="../vendor/jquery/jquery.min.js"></script>
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Page level plugin JavaScript-->
    <script src="../vendor/chart.js/Chart.min.js"></script>
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="../vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="../vendor/datatables/dataTables.bootstrap4.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="../js/sb-admin.min.js"></script>
    <script src="js/sb-admin.min.js"></script>
    <!-- Custom scripts for this page-->
    <script src="../js/sb-admin-datatables.min.js"></script>
    <script src="js/sb-admin-datatables.min.js"></script>
    <script src="../js/sb-admin-charts.min.js"></script>
    <script src="js/sb-admin-charts.min.js"></script>
  </div>
</body>
</html>
<%}%>
