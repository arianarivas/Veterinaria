/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.seguridad.configuracion;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        
	protected HttpSession getsession(){
		
		return (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	protected HttpServletRequest getRequest(){
		return (HttpServletRequest) getContext().getExternalContext().getRequest();				
	}
	
	protected HttpServletResponse getResponse(){
		return (HttpServletResponse) getContext().getExternalContext().getResponse();
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

	public String getMensaje(String key) {
		return (String) getExpresion("etiqueta['" + key + "']");		
	}

	
	public boolean isAutenticado() {
		return JSFUtils.isAutenticado();
	}
	
	@SuppressWarnings("el-syntax")
	private Object getExpresion(String expression) {
		FacesContext ctx = getContext();
		ExpressionFactory factory = ctx.getApplication().getExpressionFactory();
		ValueExpression ex = factory.createValueExpression(ctx.getELContext(),
				"#{" + expression + "}", Object.class);
		return ex.getValue(ctx.getELContext());

	}
	
	protected HashMap<String, Object> getAtributosVisibles()
	{
		@SuppressWarnings("rawtypes")
		Class clase = this.getClass();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		try {
			
			Method[] metodos = clase.getMethods();
			for (Method metodo : metodos) {
				String nombreMetodo = metodo.getName();
				if (nombreMetodo.toUpperCase().indexOf("GET") != -1) {
					Object obj = metodo.invoke(this);
					if (obj != null) 
					{
						parametros.put(nombreMetodo.toUpperCase(), obj);
					}
				}
			}
		}catch (Throwable e) 
		{
			e.printStackTrace();
		}
		return parametros;
	}

	
	protected Properties getPropiedadesGenerales() {
		return propiedadesGenerales;
	}
	
    
    protected void setPropiedadesGenerales(Properties propiedadesGenerales) {
		this.propiedadesGenerales = propiedadesGenerales;
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
	
	//VIZUALIZACION DE ERRORES 
	public String[] ajaxErrorHandler(Exception e2){
		System.out.println("Error Handler");
		e2.printStackTrace();
		String errorNameAjax = e2.getMessage();
		String errorMsgAjax = e2.toString(); 
		for (StackTraceElement err : e2.getStackTrace()) {
			errorMsgAjax = errorMsgAjax + "\n" + err.toString();
		}
		String[] resultado =   new String[3];
		resultado[0]=errorNameAjax;
		resultado[1]=errorMsgAjax;
		return resultado;
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return JSFUtils.getHttpServletRequest();
	}

}