/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.entidades.Rol;
import com.produccion.entidades.Usuarios;
import com.produccion.entidades.UsuariosRol;
import com.produccion.interfaz.RolFacadeLocal;
import com.produccion.interfaz.UsuariosFacadeLocal;
import com.produccion.interfaz.UsuariosRolFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author mpluas
 */
@Named
@ViewScoped
public class usuarioController implements Serializable{
    
    @EJB
    private UsuariosFacadeLocal usuarioEJB;
    @EJB
    private UsuariosRolFacadeLocal usuarioRolEJB;
    private List<Usuarios> listaUsuarios;
    private List<UsuariosRol> listaUsuariosRol;
    private Usuarios usuarios;
    
    
    @PostConstruct
    public void init(){
        usuarios = new Usuarios();
        listaUsuarios = usuarioEJB.findAll();
        listaUsuariosRol = new ArrayList<>();
    }

    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<UsuariosRol> ListaUsuarioRol(Usuarios usuario){
        System.out.println("El id del usuario es " + usuario.getId());
        listaUsuariosRol = usuarioRolEJB.findAllRolUsuario(usuario);
        return listaUsuariosRol;
    }
    
    public List<UsuariosRol> getListaUsuariosRol() {
        return listaUsuariosRol;
    }

    public void setListaUsuariosRol(List<UsuariosRol> listaUsuariosRol) {
        this.listaUsuariosRol = listaUsuariosRol;
    }
    
    
}
