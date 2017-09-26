/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.entidades.Menu;
import com.produccion.entidades.Usuarios;
import com.produccion.interfaz.MenuFacadeLocal;
import com.produccion.seguridad.configuracion.BeanFormulario;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
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
public class menuController extends BeanFormulario implements Serializable{
    
    @EJB
    private MenuFacadeLocal MenuEJB;
    private List<Menu> lista;
    private MenuModel model;
    
    @PostConstruct
    public void init(){
        listarOpcionesMenu();
        model = new DefaultMenuModel();
        this.crearMenu();
    }
    
    public void listarOpcionesMenu(){
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
    
    
    public void crearMenu(){
        Usuarios usu = (Usuarios) getUsuarioSession("Usuarios");
        DefaultMenuItem menItm = new DefaultMenuItem("Principal");
        menItm.setIcon("icon-home-outline");
        menItm.setTitle("Pagina Principal");
        menItm.setOutcome("/sistema/principal");
        menItm.setPartialSubmit(true);
        menItm.setProcess("@this");
        menItm.setContainerStyleClass("layout-menubar-active");
        model.addElement(menItm);
        for (Menu opcion : lista) {
            if (opcion.getTipo().equals("S") && opcion.getMenuPadre() == null){
            DefaultSubMenu submenu = new DefaultSubMenu(opcion.getOpcion());
            System.out.println(opcion.getOpcion() + "----------------------------");
            submenu.setId("OPTH" + opcion.getIdmenu());
            submenu.setIcon(opcion.getRutaImagen());
            Iterator<Menu> opcionesHijas = opcion.getMenuPadreeList().iterator();
            while (opcionesHijas.hasNext()) {
                Menu opcionHija = opcionesHijas.next();
                if (opcionHija.getRol().getIdrol() == usu.getRol().getIdrol()) {
                    if (opcionHija.getEstado().equals("A")) {
                        submenu.getElements().add(getMenuHija(opcionHija));
                    }
                    System.out.println("rol es " + usu.getRol().getIdrol());
                    System.out.println("estan en el rol" + opcionHija.getIdmenu());
                }
            }
            model.addElement(submenu);
        }
        }
    
/*            for (Menu m : lista) {
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
                            item.setUrl("principal.jsf");
                            item.setUrl(i.getAccion());
                            firsSubMenu.addElement(item);
                        }
                    }
                }
                model.addElement(firsSubMenu);
            }else{
                if (m.getMenuPadre() == null && m.getRol().getIdrol()==usu.getRol().getIdrol()) {
                    DefaultMenuItem item = new DefaultMenuItem(m.getOpcion());
                    item.setIcon(m.getRutaImagen());
                    item.setUrl("principal.jsf");
                    item.setUrl(m.getAccion());
                    model.addElement(item);
                }
            }
        }*/
    
    
    }
    
    @SuppressWarnings("el-syntax")
    private MenuElement getMenuHija(Menu opcionPadre) {
        Usuarios usu = (Usuarios) getUsuarioSession("Usuarios");
        if (opcionPadre.getMenuPadreeList()== null || opcionPadre.getMenuPadreeList().size() < 1) {
            DefaultMenuItem menItm = new DefaultMenuItem(opcionPadre.getOpcion());
            menItm.setIcon(opcionPadre.getRutaImagen());
            menItm.setTitle(opcionPadre.getDescripcion());
            if (opcionPadre.getTipo().equals("P")) {
                
            }else {
                menItm.setOnclick("pantalla.show()");
            }
            menItm.setUrl(opcionPadre.getAccion());
            //menItm.setCommand("#{verticalMenu.navegarUrl('"+ opcionPadre.getAccion() + "')}");
            menItm.setPartialSubmit(true);
            menItm.setProcess("@this");
            return menItm;                    
        } else {
            DefaultSubMenu menItm = new DefaultSubMenu(opcionPadre.getOpcion());
            menItm.setIcon(opcionPadre.getRutaImagen());
            Iterator<Menu> opcionesHijas = opcionPadre.getMenuPadreeList().iterator();
            while (opcionesHijas.hasNext()) {
                Menu opcionHija = opcionesHijas.next();
                if (opcionHija.getRol().getIdrol() == usu.getRol().getIdrol()) {
                    if (opcionHija.getEstado().equals("A")) {
                        menItm.getElements().add(getMenuHija(opcionHija));
                    }
                }
            }
            return menItm;
        }
    }
}