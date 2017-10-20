/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;


import com.produccion.entidades.Rol;
import com.produccion.interfaz.RolFacadeLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author mplua
 */
@Named
@ViewScoped
public class rolController implements Serializable{
    
    @EJB
    private RolFacadeLocal rolEJB;
    private Rol rol;
    private List<Rol> listaRoles;
    private String[] selectedRol;
    private Rol nuevoRol;
    
    @PostConstruct
    public void init(){
        inicilizacion();
    }
    
    private void inicilizacion(){
        rol = new Rol();
        listaRoles = rolEJB.findAll();
        nuevoRol = new Rol();
    }
    public Rol getRol() {
        return rol;
    }

    public Rol getNuevoRol() {
        return nuevoRol;
    }

    public void setNuevoRol(Rol nuevoRol) {
        this.nuevoRol = nuevoRol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public String[] getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(String[] selectedRol) {
        this.selectedRol = selectedRol;
    }
    
    void limpiar(){
       rol = new Rol(); 
    }
    public void registrarRol(){
        try {
            rol.setFechaRegistro(new Date());
            rol.setEstado("A");
            this.rolEJB.create(this.rol);
            limpiar();
        } catch (Exception e) {
        }
    }
    
    public void guardarRolNuevo(){
            nuevoRol.setRol(nuevoRol.getRol().toUpperCase());
            nuevoRol.setEstado("A");
            nuevoRol.setFechaRegistro(new Date());
            rolEJB.create(nuevoRol);
            inicilizacion();
    }
}
