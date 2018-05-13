/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.cad.controller.login;

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
import javax.servlet.http.HttpSession;

import sv.edu.cad.dao.Conexion;
import sv.edu.cad.model.beans.DatosBean;
import sv.edu.cad.model.beans.UsuarioBean;

/**
 *
 * @author Susan
 */
public class LoginServlet extends HttpServlet {

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
        try {
            //Obteniendo usuario y contraseña
            String usuario = request.getParameter("usuario").toUpperCase();
            String password = request.getParameter("password");
            
            UsuarioBean UsuarioActual = new UsuarioBean();
            UsuarioActual.setCarnet(usuario);
            UsuarioActual.setPassword(password);
            
            //Instanciando a Conexion
            conexion.conectar();
            boolean validLogin = conexion.verificarUsuario(UsuarioActual);
            if(!validLogin){
                String error="Ocurrio un error";
                request.setAttribute("Error", error );
                ServletContext sc = getServletContext();
                RequestDispatcher requestDispatcher = sc.getRequestDispatcher("/login.jsp");
                requestDispatcher.forward(request, response);
            }
            else{
                HttpSession session = request.getSession();
                session.setAttribute("UsuarioActual", UsuarioActual);
                ServletContext sc = getServletContext();
                if(UsuarioActual.getIdCategoria() == 1){
                    //Redirigir a home Administrador
                    DatosBean Datos = conexion.cargaInfoHomeA();
                    session.setAttribute("Datos", Datos);
                    RequestDispatcher requestDispatcher = sc.getRequestDispatcher("/admin/homeAdmin.jsp");
                    requestDispatcher.forward(request, response);
                }
                else if(UsuarioActual.getIdCategoria() == 2){
                    RequestDispatcher requestDispatcher = sc.getRequestDispatcher("/index.html");
                    requestDispatcher.forward(request, response);
                }
                else if(UsuarioActual.getIdCategoria() == 3){
                    RequestDispatcher requestDispatcher = sc.getRequestDispatcher("/index.html");
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
