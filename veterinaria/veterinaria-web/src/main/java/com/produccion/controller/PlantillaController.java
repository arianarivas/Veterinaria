/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.entidades.Usuarios;
import com.produccion.seguridad.configuracion.BeanFormulario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author mplua
 */
@Named
@ViewScoped
public class PlantillaController extends BeanFormulario implements Serializable{
    public void verificarSesion(){
        try {
            //FacesContext context = FacesContext.getCurrentInstance();
            //Usuarios usu = (Usuarios) context.getExternalContext().getSessionMap().get("Usuarios");
            Usuarios usu = (Usuarios) getVerificarSession("Usuarios");
            if (usu == null) {
                //context.getExternalContext().redirect("./../sistema/permisos.xhtml");
                redireccionar("./../sistema/permisos.xhtml");
            }
        } catch (Exception e) {
        }
    }
}
