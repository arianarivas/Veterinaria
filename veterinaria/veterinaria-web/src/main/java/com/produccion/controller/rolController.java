/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.eao.RolFacadeLocal;
import com.produccion.entidades.Rol;
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
    
    @PostConstruct
    public void init(){
        rol = new Rol();
        listaRoles = rolEJB.findAll();
    }

    public Rol getRol() {
        return rol;
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
    
    public void registrarRol(){
        try {
            rol.setFechaRegistro(new Date());
            rol.setEstado("A");
            this.rolEJB.create(this.rol);
        } catch (Exception e) {
        }
    }
}
