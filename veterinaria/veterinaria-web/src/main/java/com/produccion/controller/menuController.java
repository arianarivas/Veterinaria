/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;


import com.produccion.eao.MenuFacadeLocal;
import com.produccion.entidades.Menu;
import com.produccion.entidades.Usuarios;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author mplua
 */
@Named
@SessionScoped
public class menuController implements Serializable{
    
    @EJB
    private MenuFacadeLocal MenuEJB;
    private List<Menu> lista;
    private MenuModel model;
    
    @PostConstruct
    public void init(){
        this.listarMenu();
        model = new DefaultMenuModel();
        this.establecerPermisos();
    }
    public void listarMenu(){
        try {
            lista = MenuEJB.findAll();
        } catch (Exception e) {
            //mensaje jsf
        }   
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }
    
    
    public void establecerPermisos(){
        Usuarios usu = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Usuarios");
        System.out.println("id usu " + usu.getRol().getIdrol());
        DefaultMenuItem menItm = new DefaultMenuItem("Principal");
        menItm.setIcon("icon-home-outline");
        menItm.setTitle("Pagina Principal");
        menItm.setOutcome("/sistema/principal");
        menItm.setPartialSubmit(true);
        menItm.setProcess("@this");
        menItm.setContainerStyleClass("layout-menubar-active");
        model.addElement(menItm);
        for (Menu m : lista) {
            System.out.println("id menu " + m.getIdmenu());
            if (m.getTipo().equals("S") && m.getMenuPadre() == null && m.getRol().getIdrol()==usu.getRol().getIdrol()) {
                DefaultSubMenu firsSubMenu = new DefaultSubMenu(m.getOpcion());
                firsSubMenu.setIcon(m.getRutaImagen());
                for (Menu i : lista) {
                    Menu submenu = i.getMenuPadre();
                    if (submenu != null) {
                        if (submenu.getIdmenu()==m.getIdmenu()){
                            DefaultMenuItem item = new DefaultMenuItem(i.getOpcion());
                            item.setIcon(i.getRutaImagen());
                            item.setUrl(i.getAccion());
        item.setPartialSubmit(true);
        item.setProcess("@this");
                            firsSubMenu.addElement(item);
                        }
                    }
                }
                model.addElement(firsSubMenu);
            }else{
                if (m.getMenuPadre() == null && m.getRol().getIdrol()==usu.getRol().getIdrol()) {
                    DefaultMenuItem item = new DefaultMenuItem(m.getOpcion());
                    item.setIcon(m.getRutaImagen());
                    item.setUrl(m.getAccion());
                    model.addElement(item);
                }
            }
        }
//        for (Menu opcion : lista) {
//            if (opcion.getTipo().equals("S") && opcion.getMenuPadre() == null){
//            DefaultSubMenu submenu = new DefaultSubMenu(opcion.getOpcion());
//            System.out.println(opcion.getOpcion()
//                            + "----------------------------");
//            //submenu.setId("OPTH" + opcion.getIdmenu());
//            submenu.setIcon(opcion.getRutaImagen());
//            Iterator<Menu> opcionesHijas = opcion.getMenuPadreeList()
//                            .iterator();
//            while (opcionesHijas.hasNext()) {
//                Menu opcionHija = opcionesHijas.next();
//                // Solo si hay permisos para esa opcion
//                System.out.println("opciones " + opcionHija.getIdmenu());
//                if (opcionHija.getEstado().equals("A") && opcionHija.getMenuPadre() != null) {
//                        submenu.getElements().add(getMenuHija(opcionHija));
//                }
//            }
//            model.addElement(submenu);
//        }}
//        return (DefaultMenuModel) model;
//    }
//    
//	@SuppressWarnings("el-syntax")
//	private MenuElement getMenuHija(Menu opcionPadre) {
//
//		if (opcionPadre.getMenuPadreeList()== null
//				|| opcionPadre.getMenuPadreeList().size() < 1) {
//
//			DefaultMenuItem menItm = new DefaultMenuItem(opcionPadre.getOpcion());
//			// menItm.setValue(opcionPadre.getOpcion());
//			menItm.setIcon(opcionPadre.getRutaImagen());
//			// menItm.setAjax(true);
//			menItm.setTitle(opcionPadre.getDescripcion());
//                        menItm.setUrl(opcionPadre.getAccion());
//			// menItm.setOncomplete("showDialogProceso("+opcionPadre.getOnComplete()+");");
//
//			// menItm.getAttributes().put("cfOpcion", opcionPadre);
//
////			if (opcionPadre.getTipo().equals("S")) {
////				// menItm.setUpdate(":MAIN_TARGET");
////			} else {
////				menItm.setOnclick("pantalla.show()");
////			}
//			// menItm.setImmediate(true);
//			// menItm.setUrl(opcionPadre.getAccion());
////			menItm.setCommand("#{verticalMenu.navegarUrl('"
////					+ opcionPadre.getAccion() + "')}");
//			//menItm.setPartialSubmit(true);
//			//menItm.setProcess("@this");
//			/*
//			 * menItm.setProcess("@all"); menItm.setUpdate("@all");
//			 */
//			// menItm.addActionListener(this);
//			return menItm;
//
//		} else {
//
//			DefaultSubMenu menItm = new DefaultSubMenu(opcionPadre.getOpcion());
//			menItm.setIcon(opcionPadre.getRutaImagen());
//			// menItm.setLabel(opcionPadre.getOpcion());
//
//			Iterator<Menu> opcionesHijas = opcionPadre.getMenuPadreeList()
//					.iterator();
//
//			while (opcionesHijas.hasNext()) {
//				Menu opcionHija = opcionesHijas.next();
//				// Solo voy a recorrer si hay permisos para esa opcion
//                                    if (opcionHija.getMenuPadre() != null) {
//                                menItm.getElements().add(getMenuHija(opcionHija));
//                            }
//					
//			}
//			return menItm;
//		}
//
}
}