/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.cad.model.beans;

import java.util.ArrayList;

/**
 *
 * @author Susan
 */
public class UsuarioBean {
    private String carnet;
    private String password;
    private int idCategoria;
    private String nombre;
    private String apellido;
    private float mora;
    private int idUsuario;
    private String categoria;
    private int maxPrestamos;
    private int totalPrestamos;
    private ArrayList<Object[]> prestamosUsuario;

    public UsuarioBean(){
        prestamosUsuario = new ArrayList<Object[]>();
    }
    
    public ArrayList<Object[]> getPrestamosUsuario() {
        return prestamosUsuario;
    }

    public void setPrestamosUsuario(ArrayList<Object[]> prestamosUsuario) {
        this.prestamosUsuario = prestamosUsuario;
    }
    
    public int getTotalPrestamos() {
        return totalPrestamos;
    }

    public void setTotalPrestamos(int totalPrestamos) {
        this.totalPrestamos = totalPrestamos;
    }

    public int getMaxPrestamos() {
        return maxPrestamos;
    }

    public void setMaxPrestamos(int maxPrestamos) {
        this.maxPrestamos = maxPrestamos;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet.toUpperCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public float getMora() {
        return mora;
    }

    public void setMora(float mora) {
        this.mora = mora;
    }
    
}
