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
import sv.edu.cad.model.beans.CatalogoBean;
import sv.edu.cad.model.beans.DatosBean;
import sv.edu.cad.model.beans.UsuarioBean;

/**
 *
 * @author Susan
 */
public class IngPrestamoServlet extends HttpServlet {

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
            throws ServletException, IOException {
        Conexion conexion = new Conexion();
        UsuarioBean user = new UsuarioBean();
        CatalogoBean ejemplar = new CatalogoBean();
        try {
            
            user.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
            user.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
            user.setCarnet(request.getParameter("carnet"));
            ejemplar.setCuota(Float.parseFloat(request.getParameter("cuota")));
            ejemplar.setIdEjemplar(Integer.parseInt(request.getParameter("idEjemplar")));
            ejemplar.setIdTiempo(Integer.parseInt(request.getParameter("idTiempo")));
            
            conexion.conectar();
            if(user.getIdCategoria() == 3){
                conexion.ingresoPrestamoEstudiante(ejemplar, user);
            }else{
                conexion.ingresoPrestamoDocenteAdmin(ejemplar, user);
            }
            
            //Actualizando registros
            HttpSession session = request.getSession();
            DatosBean Datos = conexion.cargaInfoHomeA();
            session.setAttribute("Datos", Datos);
            
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Préstamo ingresado con éxito);");
            out.println("</script>");
            
            //Redirigiendo a servlet
            request.setAttribute("carnet",user.getCarnet());
            request.setAttribute("idEjemplar", ejemplar.getIdEjemplar());
            request.getRequestDispatcher("VerPrestamo").forward(request, response); 
            
        } catch (SQLException ex) {
            Logger.getLogger(IngPrestamoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
