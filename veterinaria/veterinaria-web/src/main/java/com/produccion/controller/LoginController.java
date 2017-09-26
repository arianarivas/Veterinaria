/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.entidades.Usuarios;
import com.produccion.interfaz.PersonasFacadeLocal;
import com.produccion.interfaz.UsuariosFacadeLocal;
import com.produccion.seguridad.configuracion.BeanFormulario;
import com.produccion.seguridad.configuracion.UtilCryptography;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author mplua
 */

@Named
@SessionScoped
public class LoginController extends BeanFormulario implements Serializable {
    
    @EJB
    private UsuariosFacadeLocal usuarioEJB;
    @EJB
    private PersonasFacadeLocal personasEJB;
    private Usuarios usuarios;
    private Usuarios usu;
    
    private String cambContrase1;
    private String cambContrase2;
    
    @PostConstruct
    public void init(){
        usuarios = new Usuarios();
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

    public String getCambContrase1() {
        return cambContrase1;
    }

    public void setCambContrase1(String cambContrase1) {
        this.cambContrase1 = cambContrase1;
    }

    public String getCambContrase2() {
        return cambContrase2;
    }

    public void setCambContrase2(String cambContrase2) {
        this.cambContrase2 = cambContrase2;
    }
    
    public String autenticar() {
        String redireccion = null;
        try {
            usu = usuarioEJB.autenticar(usuarios);
            if (usu != null) {
                //Almacena la sesion de jsf
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Usuarios", usu);
                setUsuarioSession("Usuarios", usu);
                System.out.println("******************************** INICIO DE SESION ********************************");
                System.out.println("Usuario logeado " + usu.getUsername());
                redireccion = "/sistema/principal?faces-redirect=true";
            }else{
               System.out.println("USUARIO/CLAVE Invalidos");
               addError("Error", "USUARIO/CLAVE Invalidos");
            } 
        } catch (Exception e) {
            System.out.println("ERROR");
            addError("Error", "ERROR");
        }
        return redireccion;
    }
    
    public void logout(){
        System.out.println("******************************** FIN DE SESION ********************************");
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        cerrarSession();
    } 
    
    public void cambiarContrasenia() {
            /*if (!cambContrase1.equals(cambContrase2)) {
                    addError("Error", "Las contraseñas no coninciden");
                    return;
            }

            if (cambContrase1.length() < 8) {
                    addError("Error", "Ingrese una contraseña de 8 caracteres mínimo");
                    return;
            }*/
            try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuarios modificarUsuario = (Usuarios) context.getExternalContext().getSessionMap().get("Usuarios");
                    modificarUsuario.setPassword(UtilCryptography.encriptar(cambContrase1));
                    System.out.println("ses " + modificarUsuario.getPersonas().getIdpersonas());
                    //System.out.println("ses " + modificarUsuario.getId());
                    usuarioEJB.edit(modificarUsuario);
                    addMensaje("Su contraseña fue cambiada con éxito");
            } catch (Throwable e) {
                    addError("Imposible realizar el cambio de contraseña");
                    e.printStackTrace();
            }
    }

    
}