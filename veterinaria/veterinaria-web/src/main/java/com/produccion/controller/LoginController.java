/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;


import com.produccion.eao.UsuariosFacadeLocal;
import com.produccion.entidades.Usuarios;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author mpluas
 */
@ManagedBean(name = "loginFinal")
@ViewScoped
public class LoginController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //Con inject no hay necesidad de declarar en el init

    private Usuarios usuarios;  
    private Usuarios usu;
    private String fechaActual;
    @EJB
    private UsuariosFacadeLocal usuarioEBJ;
    private StreamedContent myImage;
    
    @PostConstruct
    protected void init() {
        usuarios = new Usuarios();
        //usu = new Usuarios();
        fechaActual = new Date().toString();
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Usuarios getUsu() {
        return usu;
    }

    public void setUsu(Usuarios usu) {
        this.usu = usu;
    }

    public UsuariosFacadeLocal getUsuarioEBJ() {
        return usuarioEBJ;
    }

    public void setUsuarioEBJ(UsuariosFacadeLocal usuarioEBJ) {
        this.usuarioEBJ = usuarioEBJ;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public StreamedContent getMyImage() {
        if (usu.getImagen() != null) {
    InputStream is = new ByteArrayInputStream((byte[]) usu.getImagen());
    myImage = new DefaultStreamedContent(is, "image/png");
        }
        return myImage;
    }

    public void setMyImage(StreamedContent myImage) {
        this.myImage = myImage;
    }
public void imagenes(){
    InputStream is = new ByteArrayInputStream((byte[]) usu.getImagen());
    myImage = new DefaultStreamedContent(is, "image/png");
}
    public String autenticar() {
        String redireccion = null;
        try {
            usu = usuarioEBJ.autenticar(usuarios);
            if (usu != null) {
                //Almacena la sesion de jsf
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Usuarios", usu);
                System.out.println("******************************** INICIO DE SESION ********************************");
                System.out.println("nombre " + usu.getNombreCompleto());
                redireccion = "/sistema/principal?faces-redirect=true";
            }else{
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "USUARIO/CLAVE Invalidos")); 
            } 
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error"));
        }
        return redireccion;
    }
       
    public void logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }  
}
