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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.cad.dao.Conexion;
import sv.edu.cad.model.beans.DatosBean;
import sv.edu.cad.model.beans.UsuarioBean;

/**
 *
 * @author Susan
 */
public class IngDevolucionServlet extends HttpServlet {

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
        Conexion conexion = new Conexion();
        UsuarioBean user = new UsuarioBean();
        try {
            user.setCarnet(request.getParameter("carnetUsuario"));
            String descripcion = request.getParameter("descripcion");
            int idPrestamo = Integer.parseInt(request.getParameter("idPrestamo"));
            int idEjemplar = Integer.parseInt(request.getParameter("idEjemplar"));
            
            conexion.conectar();
            conexion.ingresoDevolucion(descripcion, idPrestamo, idEjemplar);
            
            request.setAttribute("idEjemplar", idEjemplar);
            request.setAttribute("carnet",user.getCarnet());
            //Actualizando registros
            HttpSession session = request.getSession();
            DatosBean Datos = conexion.cargaInfoHomeA();
            session.setAttribute("Datos", Datos);
            conexion.cerrarConexion();
            //Redirigiendo a servlet 

            request.getRequestDispatcher("VerDevolucion").forward(request, response); 
            
            
        } catch (SQLException ex) {
            Logger.getLogger(IngDevolucionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
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
            Logger.getLogger(IngDevolucionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(IngDevolucionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
