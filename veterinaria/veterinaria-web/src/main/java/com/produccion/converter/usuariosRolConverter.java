/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.converter;

import com.produccion.entidades.UsuariosRol;
import com.produccion.interfaz.UsuariosRolFacadeLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author mpluas
 */
@FacesConverter(value = "usuariosRolConverter")
public class usuariosRolConverter implements Converter {
    
    private UsuariosRolFacadeLocal projectEJB;
    
    public usuariosRolConverter(){        
        try {
            InitialContext cxt = new InitialContext();
            this.projectEJB = (UsuariosRolFacadeLocal)cxt.lookup("com.produccion.interfaz.UsuariosRolFacadeLocal");
        } catch (NamingException ex) {
            Logger.getLogger(UsuariosRolFacadeLocal.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0){
            return null;
        }
        
        try {
            UsuariosRol project = projectEJB.find(Integer.valueOf(value));
            return project;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null || !(value instanceof UsuariosRol)){
            return null;
        }
        
        return ((UsuariosRol)value).getRol().getIdrol().toString();
    }
}
