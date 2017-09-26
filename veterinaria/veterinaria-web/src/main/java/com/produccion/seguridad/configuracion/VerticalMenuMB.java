/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.seguridad.configuracion;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author mpluas
 */
@SuppressWarnings("deprecation")
@Named("verticalMenu")
@SessionScoped
public class VerticalMenuMB extends BeanFormulario implements Serializable {

	/**
	 * 
	 */ 
	/*@Inject
	@ITestServicioMenuOpcionesVertical
	private IServiciosMenu<CfOpciones> opcionesMenu;
	
	@Inject
	@ITestParseMenu
	private TestParseMenu parseMenu;
	
	@Inject
	private ErrorMB errorMB;
	
	@EJB
	private ServiciosMantenimientoSeguridad serviciosMantenimientoSeguridad;
	*/
	private static final long serialVersionUID = 1L;
	//private MenuModel simpleMenuModel;

	@PostConstruct
	public void init() {
			//simpleMenuModel = parseMenu.parseMenuOpciones(opcionesMenu.getMenuOpciones());
	}
	
	@SuppressWarnings({"static-access", "el-syntax"})
	public String navegarUrl(String url)
	{
		System.out.println("Url a Navegar => " + url);		
//		CfOpciones opcion = serviciosMantenimientoSeguridad.obtenerOpcionPorUrl(url);
////		if(opcion!=null){
////			FacesContext context = FacesContext.getCurrentInstance();
////			Application application = context.getApplication();
////			String bean = new InicializaBeans().getBeansinit().get(opcion.getAccion());
////			MethodBinding methodBinding = application.createMethodBinding("#{"+bean+".init}", null);
////			if (methodBinding != null){
////				try {
////					methodBinding.invoke(context, null);
////				} catch (Exception e) {
////					errorMB.llenarVariablesError(ajaxErrorHandler(e));
////				}
////			}
////		}
		return url+"?faces-redirect=true";
	}

	/*public MenuModel getSimpleMenuModel() {
		return simpleMenuModel;
	}

	public void setSimpleMenuModel(MenuModel simpleMenuModel) {
		this.simpleMenuModel = simpleMenuModel;
	}
	*/
	public String redirect(){
		return "/home.jsf?faces-redirect=true";
	}

}

