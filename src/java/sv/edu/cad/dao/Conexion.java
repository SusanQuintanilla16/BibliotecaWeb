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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.cad.model.beans.CatalogoBean;
import sv.edu.cad.model.beans.DatosBean;
import sv.edu.cad.model.beans.UsuarioBean;

/**
 *
 * @author Susan
 */
public class Conexion {
    private Connection conexion = null;
    private Statement s = null;
    ArrayList <String> fechaPrestamos;
    ArrayList <Float> montoPrestamos;
    ArrayList <Integer> idPrestamos;
    
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
    
    //Método para mostrar la información del usuario para realizar el ingreso del préstamo
    public void cargarUsuario(UsuarioBean user){
        try {
            String Datos[]= new String[7];
            String query ="SELECT usuario.idUsuario,categoriausuario.NombreCategoria"
                    + ",usuario.Nombre,usuario.Apellido,categoriausuario.MaxPrestamos,usuario.Mora,usuario.idCategoria from usuario "
                    + "inner join categoriausuario on categoriausuario.idCategoria=usuario.idCategoria "
                    + "where usuario.Carnet='"+user.getCarnet()+"'";
            ResultSet Contenido = s.executeQuery(query);
            if(Contenido.next()){                
                user.setIdUsuario(Contenido.getInt(1));
                user.setCategoria(Contenido.getString(2));
                user.setNombre(Contenido.getString(3));
                user.setApellido(Contenido.getString(4));
                user.setMaxPrestamos(Contenido.getInt(5));
                user.setMora(Contenido.getFloat(6));
                user.setIdCategoria(Contenido.getInt(7));
                mostrarTotalPrestamos(user);
                Contenido.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTotalPrestamos(UsuarioBean user){
        try {
            String query="select count(prestamo.idUsuario) from prestamo "
                    + "where prestamo.idUsuario="+ user.getIdUsuario()+ " and prestamo."
                    + "EstadoPrestamo=\"PRESTADO\"";
            ResultSet total = s.executeQuery(query);
            if(total.next()){
                int prestamos = total.getInt(1);
                user.setTotalPrestamos(prestamos);
            }      
            total.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    //Método para ver los préstamos en la tabla
    public void muestraPrestamos(UsuarioBean user){
        try
        {
            fechaPrestamos = new ArrayList<String>();
            montoPrestamos = new ArrayList<Float>();
            idPrestamos = new ArrayList <Integer>();
            ArrayList<Object[]> prestamos = new ArrayList<Object[]>();
            Object [][] data = null;
            String query= "select prestamo.idEjemplar,catalogo.Titulo,"
                    + "prestamo.FechaPrestamo,prestamo.FechaDevolucion,prestamo.montodia"
                    + ",prestamo.idPrestamo from "
                    + "prestamo inner join ejemplar on ejemplar.idEjemplar="
                    + "prestamo.idEjemplar inner join catalogo on "
                    + "catalogo.idCatalogo=ejemplar.idCatalogo where prestamo.idUsuario "
                    + "= "+user.getIdUsuario()+" and prestamo.EstadoPrestamo=\"PRESTADO\"";
            ResultSet catalogo = s.executeQuery(query);
            while(catalogo.next())
            {
                Object[] row={catalogo.getInt(1),catalogo.getString(2),
                catalogo.getString(3),catalogo.getString(4)};
                prestamos.add(row);
                fechaPrestamos.add(catalogo.getString(4));
                montoPrestamos.add(catalogo.getFloat(5));
                idPrestamos.add(catalogo.getInt(6));
            }
            user.setPrestamosUsuario(prestamos);
            catalogo.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     //Método para ver los préstamos en la tabla (Manipulacion de checkbox)
    public void muestraPrestamosArrayCatalogo(UsuarioBean user,CatalogoBean ejemplar){
        try
        {
            ArrayList<CatalogoBean> arrayCat = new ArrayList<CatalogoBean>();
            Object [][] data = null;
            String query= "select prestamo.idEjemplar,catalogo.Titulo,"
                    + "prestamo.FechaPrestamo,prestamo.FechaDevolucion,prestamo.montodia"
                    + ",prestamo.idPrestamo from "
                    + "prestamo inner join ejemplar on ejemplar.idEjemplar="
                    + "prestamo.idEjemplar inner join catalogo on "
                    + "catalogo.idCatalogo=ejemplar.idCatalogo where prestamo.idUsuario "
                    + "= "+user.getIdUsuario()+" and prestamo.EstadoPrestamo=\"PRESTADO\"";
            ResultSet catalogo = s.executeQuery(query);
            while(catalogo.next())
            {
                CatalogoBean aux= new CatalogoBean();
                aux.setIdEjemplar(catalogo.getInt(1));
                aux.setTitulo(catalogo.getString(2));
                aux.setFechaPrestamo(catalogo.getString(3));
                aux.setFechaDevolucion(catalogo.getString(4));
                aux.setCuota(catalogo.getFloat(5));
                aux.setIdPrestamo(catalogo.getInt(6));
                arrayCat.add(aux);
            }
            ejemplar.setArrayCatalogo(arrayCat);
            catalogo.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método para calcular las moras 
    // Como el arraylist anterior solo toma los prestamos que aun no han sido regresados
    // comparará la fecha de devolución con la actual y si ya paso la fecha lo tomará como 
    // prestamo vencido y actualizará la mora
    public void calcularMora(UsuarioBean user){
        ArrayList<Object[]> prestamosVencidos = new ArrayList<Object[]>();
        LocalDate fechaActual = LocalDate.now();
        float mora = 0;
        for (int i = 0; i < fechaPrestamos.size(); i++) {
            String [] arregloFecha = fechaPrestamos.get(i).split("-");
            LocalDate fechaDevolucion = LocalDate.of(Integer.parseInt(arregloFecha[0]),
                    Integer.parseInt(arregloFecha[1]),
                    Integer.parseInt(arregloFecha[2]));
            int dias = (int) ChronoUnit.DAYS.between(fechaDevolucion, fechaActual);
            if(dias>0){
                mora += dias*montoPrestamos.get(i);
                prestamosVencidos.add(new Object[] {idPrestamos.get(i),dias,dias*montoPrestamos.get(i)});
            }
        }
        
        if(mora > 0)
        {
            try {
                String query = "update usuario set usuario.mora = "+mora+" where "
                        + "usuario.idUsuario = "+user.getIdUsuario();
                s.executeUpdate(query);
                //Se pasa el arraylist a una matriz multidimensional de tipo objeto
                Object[][] arrayVencimiento = new Object[prestamosVencidos.size()][3];
                for (int i = 0; i <prestamosVencidos.size(); i++) {
                    Object[] row = prestamosVencidos.get(i);
                    arrayVencimiento[i] = row;
                }
                
                String queryMultiple;
                for (int i = 0; i < arrayVencimiento.length; i++) {
                    Conexion conexion2= new Conexion();
                    query= "select mora.idprestamo from mora where mora.idprestamo = "
                            + arrayVencimiento[i][0];
                    conexion2.conectar();
                    ResultSet resultado = conexion2.s.executeQuery(query);
                    if(resultado.next()){
                        queryMultiple="update mora set estadomora='PENDIENTE',"
                                + "montomora="+arrayVencimiento[i][2]+",diasbajomora="
                                + arrayVencimiento[i][1] + " where idprestamo = "
                                + arrayVencimiento[i][0];
                    }
                    else{
                        queryMultiple="insert into mora(idprestamo,estadomora,montomora,"
                                + "diasbajomora) values("+arrayVencimiento[i][0]
                                +",'PENDIENTE',"+arrayVencimiento[i][2]+","
                                +arrayVencimiento[i][1]+")";
                    }
                    Conexion conexion3 = new Conexion();
                    conexion3.conectar();
                    conexion3.s.executeUpdate(queryMultiple);
                    conexion3.cerrarConexion();
                    resultado.close();
                    conexion2.cerrarConexion();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //Funcion para obtener la mora actualizada
    public float obtenerMoraActualizada(int id)
    {
        try {
            String query="select usuario.mora from usuario where usuario.idUsuario="+id;
            ResultSet mora=s.executeQuery(query);
            if(mora.next()){
                return mora.getFloat(1);
            }
            else return 0;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    //Método para mostrar la informacion del ejemplar seleccionado
    public void mostrarEjemplar(CatalogoBean catalogo){
        try {
            String query ="select ejemplar.idEjemplar,biblioteca.Nombre,"
                    + "estado.Estado,catalogo.idCatalogo,catalogo.Titulo,"
                    + "material.NombreMaterial,tiempoprestamo.Tiempo,"
                    + "ejemplar.idTiempo,cuotamora.cuotamora from "
                    + "ejemplar inner join catalogo on ejemplar.idCatalogo="
                    + "catalogo.idCatalogo inner join biblioteca on biblioteca."
                    + "idBiblioteca=ejemplar.idBiblioteca inner join estado "
                    + "on estado.idEstado=ejemplar.idEstado inner join material"
                    + " on catalogo.idMaterial=material.idMaterial inner join "
                    + "tiempoprestamo on tiempoprestamo.idTiempo=ejemplar."
                    + "idTiempo inner join cuotamora on cuotamora.idcuota= "
                    + "catalogo.idcuota where ejemplar.idEjemplar="+catalogo.getIdEjemplar()+"";
            ResultSet Contenido = s.executeQuery(query);
            if(Contenido.next()){                
                catalogo.setIdEjemplar(Contenido.getInt(1));
                catalogo.setBiblioteca(Contenido.getString(2));
                catalogo.setEstado(Contenido.getString(3));
                catalogo.setIdCatalogo(Contenido.getInt(4));
                catalogo.setTitulo(Contenido.getString(5));
                catalogo.setMaterial(Contenido.getString(6));
                catalogo.setTiempoPrestamo(Contenido.getString(7));
                catalogo.setIdTiempo(Contenido.getInt(8));
                catalogo.setCuota(Contenido.getFloat(9));
                Contenido.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int devuelveTiempo(int idTiempo){
        int dias = 0;
        if(idTiempo==2)
            dias=1;
        else if(idTiempo==3)
            dias=3;
        else if(idTiempo==4)
            dias=15;
        return dias;
    }
    
    //Método para el ingreso  de Prestamos
    public void ingresoPrestamoEstudiante(CatalogoBean catalogo, UsuarioBean user){
        try {
                int dias = devuelveTiempo(catalogo.getIdTiempo());
                LocalDate fechaPrestamo = LocalDate.now();
                LocalDate fechaDevolucion = fechaPrestamo.plusDays(dias);//sumo días para la devolución
                String query = "INSERT INTO `prestamo`(`idUsuario`, "
                        + "`idEjemplar`, `FechaPrestamo`, `EstadoPrestamo`, `DuracionDias`"
                        + ", `MontoDia`, `FechaDevolucion`) VALUES ("
                        + user.getIdUsuario()+","+catalogo.getIdEjemplar()+",'"+fechaPrestamo.toString()
                        +"','PRESTADO',"+dias+","+catalogo.getCuota()+",'"+fechaDevolucion.toString()+"')";
                s.executeUpdate(query);
                query  = "UPDATE ejemplar set ejemplar.idEstado = 2 where ejemplar.idEjemplar="
                        +catalogo.getIdEjemplar();
                s.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void ingresoPrestamoDocenteAdmin(CatalogoBean catalogo, UsuarioBean user){
        try {
                int dias = 15;
                LocalDate fechaPrestamo = LocalDate.now();
                LocalDate fechaDevolucion = fechaPrestamo.plusDays(dias);//sumo días para la devolución
                String query = "INSERT INTO `prestamo`(`idUsuario`, "
                        + "`idEjemplar`, `FechaPrestamo`, `EstadoPrestamo`, `DuracionDias`"
                        + ", `MontoDia`, `FechaDevolucion`) VALUES ("
                        + user.getIdUsuario()+","+catalogo.getIdEjemplar()+",'"+fechaPrestamo.toString()
                        +"','PRESTADO',"+dias+","+catalogo.getCuota()+",'"+fechaDevolucion.toString()+"')";
                s.executeUpdate(query);
                query  = "UPDATE ejemplar set ejemplar.idEstado = 2 where ejemplar.idEjemplar="
                        +catalogo.getIdEjemplar();
                s.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    //Función para mostrar datos del préstamo
    public void mostrarItemPrestamo(CatalogoBean ejemplar){
        try { 
            String query = "SELECT usuario.Carnet,prestamo.FechaPrestamo,"
                    + "prestamo.FechaDevolucion,prestamo.idPrestamo from usuario "
                    + "inner join prestamo on prestamo.idUsuario=usuario.idUsuario"
                    + " inner join ejemplar on ejemplar.idEjemplar=prestamo.idEjemplar"
                    + " WHERE prestamo.idEjemplar="+ejemplar.getIdEjemplar()+" and prestamo.EstadoPrestamo='PRESTADO'";
            ResultSet resultado = s.executeQuery(query);
            if(resultado.next()){
                ejemplar.setCarnetPrestamo(resultado.getString(1));
                ejemplar.setFechaPrestamo(resultado.getString(2));
                ejemplar.setFechaDevolucion(resultado.getString(3));
                ejemplar.setIdPrestamo(resultado.getInt(4));
                resultado.close();
            }
            else{
                resultado.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    //Función para realizar la devolución
    public void ingresoDevolucion(String Descripcion,int idPrestamo,int idEjemplar){
        try {
            LocalDate today = LocalDate.now();
            String query = "INSERT INTO `devolucion`(`idPrestamo`, `Observaciones`) VALUES ("
                    + idPrestamo+",'"+Descripcion+"')";
            s.executeUpdate(query);
            query = "update prestamo set estadoprestamo = 'DEVUELTO',FechaDevolucion='"+today.toString()
                    + "' where "
                    + "prestamo.idPrestamo = "+idPrestamo;
            s.executeUpdate(query);
            query = "update ejemplar set idEstado = 1 where ejemplar.idEjemplar "
                    + "= "+idEjemplar;
            s.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método que obtiene los días de un idprestamo
    public int getDiasPrestamos(String idPrestamo){
        try {
            int dias=0;
            String query="SELECT prestamo.DuracionDias from prestamo where prestamo.idPrestamo="+idPrestamo;
            ResultSet result = s.executeQuery(query);
            if(result.next()){
                dias = result.getInt(1);
            }
            return dias;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    //Método para la renovación
    public void Renovar(String idrenovar,int dias){
        try {
            LocalDate today = LocalDate.now();
            LocalDate devolucion = today.plusDays(dias);
            String query = "INSERT INTO `renovacion`(`idPrestamo`,"
                    + " `FechaRenovacion`, `FechaInicio`, `FechaVencimiento`) VALUES ("
                    + idrenovar+",'"+today.toString()+"','"+today.toString()+"','"
                    + devolucion.toString()+"')";
            s.executeUpdate(query);
            query = "update prestamo set FechaPrestamo='"+today.toString()+"' , "
                    +"FechaDevolucion = '"+devolucion.toString()+"' where idPrestamo = "
                    +idrenovar;
            s.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Función para generar la lista de todos los préstamos registrados
    public ArrayList<String[]> verPrestamos(){
        try {
            ArrayList<String[]> arrayPrestamos = new ArrayList<String[]>();
            String query="SELECT catalogo.Titulo, material.NombreMaterial, usuario.Carnet, prestamo.FechaPrestamo "
                    + ",prestamo.FechaDevolucion ,prestamo.EstadoPrestamo from usuario inner join prestamo on "
                    + "usuario.idUsuario=prestamo.idUsuario inner join ejemplar on prestamo.idEjemplar=ejemplar.idEjemplar"
                    + " inner join catalogo on catalogo.idCatalogo=ejemplar.idCatalogo inner join material on "
                    + "catalogo.idMaterial=material.idMaterial order by prestamo.EstadoPrestamo desc";
            ResultSet datos = s.executeQuery(query);
            while(datos.next()){
                String[] row={datos.getString(1),datos.getString(2),
                datos.getString(3),datos.getString(4),datos.getString(5),
                datos.getString(6)};
                arrayPrestamos.add(row);
            }
            return arrayPrestamos;
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    //Funcion para cerrar la conexion
    public void cerrarConexion() throws SQLException
    {
        s.close();
        conexion.close();
    }
}
