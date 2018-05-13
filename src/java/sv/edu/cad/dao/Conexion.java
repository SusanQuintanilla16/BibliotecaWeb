/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.cad.dao;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.cad.model.beans.DatosBean;
import sv.edu.cad.model.beans.UsuarioBean;

/**
 *
 * @author Susan
 */
public class Conexion {
    private Connection conexion = null;
    private Statement s = null;
    //Constructor de la clase
    public void conectar() throws SQLException
    {
     try
     {
         Class.forName("com.mysql.jdbc.Driver");         
         conexion = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca","root","");
         s = conexion.createStatement();
     }catch(ClassNotFoundException e1)
     {
         
         System.out.println("ERROR: No encuentro el driver de BDD"+e1.getMessage());
     }
    }
    
    //Función para verificar la existencia del usuario en el sistema
    public boolean verificarUsuario(UsuarioBean Usuario){
        try{
            PreparedStatement query;
            ResultSet resultado;
            String sql = "select * from usuario where Carnet = ? and Password = SHA2(?,256)";
            query=(PreparedStatement) conexion.prepareStatement(sql);
            query.setString(1, Usuario.getCarnet());
            query.setString(2, Usuario.getPassword());
            resultado = query.executeQuery();
            if(resultado.next()){
                Usuario.setIdUsuario(resultado.getInt(1));
                Usuario.setIdCategoria(resultado.getInt(2));
                Usuario.setNombre(resultado.getString(3));
                Usuario.setApellido(resultado.getString(4));
                Usuario.setMora(resultado.getFloat(7));
                return true;
            }
            else return false;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    //Función para obtener datos para el home de administrador
    public DatosBean cargaInfoHomeA(){
        DatosBean Datos = new DatosBean();
        try {
            String query="SELECT count(usuario.idUsuario) from usuario";
            ResultSet resultado = s.executeQuery(query);
            if(resultado.next()){
                Datos.setCantidadUsuarios(resultado.getInt(1));
            }
            query="SELECT count(ejemplar.idEjemplar) from ejemplar";
            resultado = s.executeQuery(query);
            if(resultado.next()){
                Datos.setCantidadTotalItems(resultado.getInt(1));
            }
            query="SELECT count(ejemplar.idEjemplar) from ejemplar where ejemplar.idEstado=1";
            resultado = s.executeQuery(query);
            if(resultado.next()){
                Datos.setCantidadDisponibleItems(resultado.getInt(1));
            }
            query="SELECT count(ejemplar.idEjemplar) from ejemplar where ejemplar.idEstado=2";
            resultado = s.executeQuery(query);
            if(resultado.next()){
                Datos.setCantidadPrestadoItems(resultado.getInt(1));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            return Datos;
        }
    }
    
    //Funcion para cerrar la conexion
    public void cerrarConexion() throws SQLException
    {
        s.close();
        conexion.close();
    }
}
