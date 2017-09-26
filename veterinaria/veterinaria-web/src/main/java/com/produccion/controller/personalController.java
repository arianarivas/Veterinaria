/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;


import com.produccion.entidades.Personal;
import com.produccion.entidades.Rol;
import com.produccion.entidades.Usuarios;
import com.produccion.interfaz.PersonalFacadeLocal;
import com.produccion.interfaz.UsuariosFacadeLocal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author mpluas
 */
@Named
@ViewScoped
public class personalController implements Serializable{
    
    @EJB
    private PersonalFacadeLocal personalEJB;
    @EJB
    private UsuariosFacadeLocal usuariosEJB;
    
    private Personal personal;
    private Usuarios usuarios;
    private Rol rol;
    private UploadedFile archivo;
    
    @PostConstruct
    public void init(){
        personal = new Personal();
        usuarios = new Usuarios();
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }
    
    public void limpiar(){
        personal = new Personal();
        usuarios = new Usuarios();
    }
    public void registrarPersonal(){
        try {
            personal.setFechaRegistro(new Date());
            personal.setEstado("A");
            this.personalEJB.create(this.personal);
            personal.setUsuarios(this.usuarios);
            usuarios.setImagen(archivo.getContents());
            usuarios.setPassword(DigestUtils.md5Hex(usuarios.getPassword()));
            rol.setIdrol(1);
            usuarios.setRol(rol);
            this.usuariosEJB.create(this.usuarios);
            limpiar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se registro el empleado correctamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Abiso", "No se registro el empleado"));
        }
    }
}
