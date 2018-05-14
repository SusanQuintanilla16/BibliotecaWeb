/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.cad.model.beans;

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
