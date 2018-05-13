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
public class DatosBean {
    int cantidadUsuarios;
    int cantidadTotalItems;
    int cantidadDisponibleItems;
    int cantidadPrestadoItems;

    public int getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    public void setCantidadUsuarios(int cantidadUsuarios) {
        this.cantidadUsuarios = cantidadUsuarios;
    }

    public int getCantidadTotalItems() {
        return cantidadTotalItems;
    }

    public void setCantidadTotalItems(int cantidadTotalItems) {
        this.cantidadTotalItems = cantidadTotalItems;
    }

    public int getCantidadDisponibleItems() {
        return cantidadDisponibleItems;
    }

    public void setCantidadDisponibleItems(int cantidadDisponibleItems) {
        this.cantidadDisponibleItems = cantidadDisponibleItems;
    }

    public int getCantidadPrestadoItems() {
        return cantidadPrestadoItems;
    }

    public void setCantidadPrestadoItems(int cantidadPrestadoItems) {
        this.cantidadPrestadoItems = cantidadPrestadoItems;
    }
    
}
