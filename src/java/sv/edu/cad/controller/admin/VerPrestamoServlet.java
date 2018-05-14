/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.cad.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.cad.dao.Conexion;
import sv.edu.cad.model.beans.CatalogoBean;
import sv.edu.cad.model.beans.UsuarioBean;

/**
 *
 * @author Susan
 */
public class VerPrestamoServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        Conexion conexion = new Conexion();
        try (PrintWriter out = response.getWriter()) {
           String carnet = request.getParameter("carnet");
           int idEjemplar = Integer.parseInt(request.getParameter("idEjemplar"));
           
           //Instancias hacia las clases
           CatalogoBean ejemplar = new CatalogoBean();
           UsuarioBean user = new UsuarioBean();
           
           user.setCarnet(carnet);
           ejemplar.setIdEjemplar(idEjemplar);
           
           conexion.conectar();
           conexion.cargarUsuario(user);

           if(user.getTotalPrestamos()>0){
               conexion.muestraPrestamos(user);
               conexion.calcularMora(user);
               user.setMora(conexion.obtenerMoraActualizada(user.getIdUsuario()));
           }
           
           conexion.mostrarEjemplar(ejemplar);
           
           if(ejemplar.getIdCatalogo()==0||user.getIdUsuario()==0){
               String error="Ocurrio un error";
                request.setAttribute("ErrorEjemplar", error );
                ServletContext sc = getServletContext();
                RequestDispatcher requestDispatcher = sc.getRequestDispatcher("/admin/regPrestamo.jsp");
                requestDispatcher.forward(request, response);
           }else{
                 request.setAttribute("Usuario", user);
                 request.setAttribute("Ejemplar", ejemplar);
                 ServletContext sc = getServletContext();
                 RequestDispatcher requestDispatcher = sc.getRequestDispatcher("/admin/ingPrestamo.jsp");
                 requestDispatcher.forward(request, response);
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(VerPrestamoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            conexion.cerrarConexion();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(VerPrestamoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(VerPrestamoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
