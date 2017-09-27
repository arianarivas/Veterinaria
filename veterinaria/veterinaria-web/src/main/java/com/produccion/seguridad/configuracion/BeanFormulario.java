/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.seguridad.configuracion;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author mplua
 */
public abstract class BeanFormulario implements Serializable{
	
    private Properties propiedadesGenerales;
	
	private static final long serialVersionUID = 1L;

	protected FacesContext getContext() {
            return FacesContext.getCurrentInstance();
	}
	//Guarda el usuario de session
        protected ExternalContext setUsuarioSession(String patron, Object entidad){
            return (ExternalContext) getContext().getExternalContext().getSessionMap().put(patron, entidad);
        }
	//Retorna si esta activa la session
        protected Object getVerificarSession(String patron){
            return (ExternalContext) getContext().getExternalContext().getSessionMap().get(patron);
        }
	//Retorna el usuario de session
        protected Object getUsuarioSession(String patron){
            return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(patron);
        }
	//Ir pagina
        protected void redireccionar(String url) throws IOException{
            getContext().getExternalContext().redirect(url);
        }
        //Cerrar session
        protected void cerrarSession(){
            getContext().getExternalContext().invalidateSession();
        }

	public void addMensaje(String msg) {
		addMensaje(msg, FacesMessage.SEVERITY_INFO);
	}

	public void addError(String msg) {
		addMensaje(msg, FacesMessage.SEVERITY_ERROR);
	}

	public void addMensaje(String msg, String detail) {
		addMensaje(msg, detail,FacesMessage.SEVERITY_INFO);
	}

	public void addError(String msg, String detail) {
		addMensaje(msg, detail, FacesMessage.SEVERITY_ERROR);
	}
	
	private void addMensaje(String msg ,Severity severity) {
		FacesMessage message = new FacesMessage(msg);	
		message.setSeverity(severity);
		FacesContext ctx = getContext();
		ctx.addMessage(null, message);
	}
	
	private void addMensaje(String msg, String detail ,Severity severity) {
		FacesMessage message = new FacesMessage(msg, detail);
		message.setSeverity(severity);
		FacesContext ctx = getContext();
		ctx.addMessage(null, message);
	}

        /*Inicio Metos Edwin Amaguaya*/
	protected void redireccionarPagina(String urlPagina) throws IOException
	{
		FacesContext ctx = getContext();
		ExternalContext extContext = ctx.getExternalContext();
		String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, urlPagina));
		System.out.println("url a redireccionar " + url);
		extContext.redirect(url);
	}
	/*Fin Metos Edwin Amaguaya*/

}