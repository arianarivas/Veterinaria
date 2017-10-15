/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.entidades.Usuarios;
import com.produccion.entidades.UsuariosRol;
import com.produccion.interfaz.PersonasFacadeLocal;
import com.produccion.interfaz.UsuariosFacadeLocal;
import com.produccion.seguridad.configuracion.BeanFormulario;
import com.produccion.seguridad.configuracion.UtilCryptography;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author mplua
 */

@Named
@SessionScoped
public class LoginController extends BeanFormulario implements Serializable {
    
    @EJB
    private UsuariosFacadeLocal usuarioEJB;
    private Usuarios usuarios;
    private Usuarios usu;
    private StreamedContent myImage;
    private String fechaActual;
    private String cambContrase1;
    private String cambContrase2;
    private String rolesUsuario;
    private String username;
    private String password;
    
    @PostConstruct
    public void init(){
        usu = (Usuarios) getUsuarioSession("Usuarios");
        SimpleDateFormat simpleFom = new SimpleDateFormat("EEEE, dd-MMMM-yyyy",  new Locale("es", "ES"));
        fechaActual = simpleFom.format(new Date());
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRolesUsuario() {
        List<UsuariosRol> listaUsuariosRoles = usu.getUsuariosRoles();
        String rolesUsuario = "";
        for (UsuariosRol rol : listaUsuariosRoles) {
                if (rol.getEstado().equals("A")) {
                        rolesUsuario = rolesUsuario + rol.getRol().getRol() + ", ";
                }
        }
        return rolesUsuario;
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

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }
    
    public String autenticar() {
        if (usu != null) {
            logout();
        }
        String redireccion = null;
        try {
            usu = usuarioEJB.autenticar(username,password);
            if (usu != null) {
                setUsuarioSession("Usuarios", usu);
                System.out.println("******************************** INICIO DE SESION ********************************");
                System.out.println("Usuario logeado " + usu.getUsername());
                usuarioEJB.registrarSession(usu);
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
        cerrarSession();
    } 
    
    public void cambiarContrasenia() {
            if (!cambContrase1.equals(cambContrase2)) {
                    addError("Error", "Las contraseñas no coninciden");
                    return;
            }

            if (cambContrase1.length() < 8) {
                    addError("Error", "Ingrese una contraseña de 8 caracteres mínimo");
                    return;
            }
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