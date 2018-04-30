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
    
    //Funcion para cerrar la conexion
    public void cerrarConexion() throws SQLException
    {
        s.close();
        conexion.close();
    }
}
