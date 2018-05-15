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
public class CatalogoBean {
    int idEjemplar;
    String biblioteca;
    String Estado;
    int idCatalogo;
    String titulo;
    String material;
    String tiempoPrestamo;
    float cuota;
    int idTiempo;
    ArrayList<CatalogoBean> arrayCatalogo; 
    
        //Variables para prestamo
    String carnetPrestamo;
    String fechaDevolucion;
    String fechaPrestamo;
    int idPrestamo;


    public ArrayList<CatalogoBean> getArrayCatalogo() {
        return arrayCatalogo;
    }

    public void setArrayCatalogo(ArrayList<CatalogoBean> arrayCatalogo) {
        this.arrayCatalogo = arrayCatalogo;
    }
    
    public String getCarnetPrestamo() {
        return carnetPrestamo;
    }

    public void setCarnetPrestamo(String carnetPrestamo) {
        this.carnetPrestamo = carnetPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdTiempo() {
        return idTiempo;
    }

    public void setIdTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }
    
    public String getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(String biblioteca) {
        this.biblioteca = biblioteca;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(int idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTiempoPrestamo() {
        return tiempoPrestamo;
    }

    public void setTiempoPrestamo(String tiempoPrestamo) {
        this.tiempoPrestamo = tiempoPrestamo;
    }

    public float getCuota() {
        return cuota;
    }

    public void setCuota(float cuota) {
        this.cuota = cuota;
    }

    public int getIdEjemplar() {
        return idEjemplar;
    }

    public void setIdEjemplar(int idEjemplar) {
        this.idEjemplar = idEjemplar;
    }
    
}
