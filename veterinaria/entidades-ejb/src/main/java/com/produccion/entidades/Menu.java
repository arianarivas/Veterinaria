/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author mpluas
 */
@Entity
@Table(name = "menu")
public class Menu implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmenu")
    private Integer idmenu;
    @Size(max = 2)
    @Column(name = "estado")
    private String estado;
    @Size(max = 250)
    @Column(name = "accion")
    private String accion;
    @Size(max = 250)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 250)
    @Column(name = "opcion")
    private String opcion;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 250)
    @Column(name = "ruta_imagen")
    private String rutaImagen;
    @Size(max = 2)
    @Column(name = "tipo")
    private String tipo;
//    @ManyToOne
//    @JoinColumn(name = "rol_idrol", referencedColumnName = "idrol")
//    private Rol rol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuPadre", fetch = FetchType.EAGER)
    @OrderBy("orden ASC")
    private List<Menu> menuPadreeList;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_padre", referencedColumnName = "idmenu")
    private Menu menuPadre;
    @OneToMany(mappedBy="menu")
    private List<MenuRol> menuRoles;
    
    public Menu() {
        this.menuPadreeList = new ArrayList<>();
        this.menuRoles = new ArrayList<>();
    }

    public Menu(Integer idmenu) {
        this.idmenu = idmenu;
        this.menuPadreeList = new ArrayList<>();
        this.menuRoles = new ArrayList<>();
    }

    public Menu(String estado, String accion, String descripcion, String opcion, Integer orden, String rutaImagen, String tipo) {
        this.estado = estado;
        this.accion = accion;
        this.descripcion = descripcion;
        this.opcion = opcion;
        this.orden = orden;
        this.rutaImagen = rutaImagen;
        this.tipo = tipo;
        this.menuPadreeList = new ArrayList<>();
        this.menuRoles = new ArrayList<>();
    }
    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Menu getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(Menu menuPadre) {
        this.menuPadre = menuPadre;
    }

    public Integer getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(Integer idmenu) {
        this.idmenu = idmenu;
    }

//    public Rol getRol() {
//        return rol;
//    }

//    public void setRol(Rol rol) {
//        this.rol = rol;
//        if(!this.rol.getMenuList().contains(this)){
//            this.rol.getMenuList().add(this);
//        }
//    }

    public List<Menu> getMenuPadreeList() {
        return menuPadreeList;
    }

    public void setMenuPadreeList(List<Menu> menuPadreeList) {
        this.menuPadreeList = menuPadreeList;
    }

    public List<MenuRol> getMenuRoles() {
        return menuRoles;
    }

    public void setMenuRoles(List<MenuRol> menuRoles) {
        this.menuRoles = menuRoles;
    }
    
}

