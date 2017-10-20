/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.controller;

import com.produccion.entidades.Rol;
import com.produccion.entidades.Usuarios;
import com.produccion.entidades.UsuariosRol;
import com.produccion.interfaz.RolFacadeLocal;
import com.produccion.interfaz.UsuariosFacadeLocal;
import com.produccion.interfaz.UsuariosRolFacadeLocal;
import com.produccion.seguridad.configuracion.BeanFormulario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author mplua
 */
@Named
@ViewScoped
public class usuarioRolController extends BeanFormulario implements Serializable{
    
    @EJB
    private UsuariosFacadeLocal usuarioEJB;
    @EJB
    private RolFacadeLocal rolEJB;
    @EJB
    private UsuariosRolFacadeLocal usuarioRolEJB;
    private List<Usuarios> listaUsuariosActivos;
    private List<Rol> listaRolesActivos;
    private Boolean[][] opcionesAsignadas;
    private Rol nuevoRol;
    private List<Rol> listaRoles;
    private boolean presentarBotonActvivarRol;
    
    @PostConstruct
    public void init(){
        inicilizacion();
    }
    
    private void inicilizacion(){
        listaUsuariosActivos = usuarioEJB.findAllActivos();
        listaRolesActivos = rolEJB.findAllActivos();
        opcionesAsignadas();
        listaRoles = rolEJB.findAll();
        nuevoRol = new Rol();
        presentarBotonActvivarRol=false;
    }

    public List<Usuarios> getListaUsuariosActivos() {
        return listaUsuariosActivos;
    }

    public void setListaUsuariosActivos(List<Usuarios> listaUsuariosActivos) {
        this.listaUsuariosActivos = listaUsuariosActivos;
    }

    public List<Rol> getListaRolesActivos() {
        return listaRolesActivos;
    }

    public void setListaRolesActivos(List<Rol> listaRolesActivos) {
        this.listaRolesActivos = listaRolesActivos;
    }

    public boolean isPresentarBotonActvivarRol() {
        return presentarBotonActvivarRol;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public void setPresentarBotonActvivarRol(boolean presentarBotonActvivarRol) {
        this.presentarBotonActvivarRol = presentarBotonActvivarRol;
    }

    public Boolean[][] getOpcionesAsignadas() {
        return opcionesAsignadas;
    }

    public void setOpcionesAsignadas(Boolean[][] opcionesAsignadas) {
        this.opcionesAsignadas = opcionesAsignadas;
    }
    
    public void opcionesAsignadas(){
            int cont=0, iRol=0, iOpc=0;
            opcionesAsignadas = new Boolean[listaRolesActivos.size()][listaUsuariosActivos.size()];
            for (Rol i : listaRolesActivos) {			
                    List <Usuarios> opcionesTemporalesAsignadas = usuarioEJB.findAllRolUsuario(i.getIdrol());
                    for (Usuarios opcTerm : listaUsuariosActivos) {
                            cont=1;
                            for (Usuarios opcAs : opcionesTemporalesAsignadas) {
                                    if(opcTerm.getId()==opcAs.getId()){
                                            opcionesAsignadas[iOpc][iRol]= true;
                                            iRol++;
                                            break;
                                    }else if(opcionesTemporalesAsignadas.size()==cont){	
                                            opcionesAsignadas[iOpc][iRol]= false;
                                            iRol++;
                                    }
                                    cont++;
                            }							
                    }
                    iRol=0;
                    iOpc++;			
            }
    }
    
    public void ingresoActualizarOpcionesRoles(Integer rol, Integer opc){
        System.out.println("Usuario es " + opc);
        System.out.println("Rol es " + rol);
            Rol seedRol = new Rol();
            seedRol.setIdrol(rol);		
            Usuarios seedOpc = new Usuarios();
            seedOpc.setId(opc);		
            UsuariosRol opcRol = usuarioRolEJB.obtenerUsuarioRol(opc, rol);
            if(opcRol!=null){
                    if(opcRol.getEstado().equals("A")){
                            usuarioRolEJB.actualizarUsuarioRol("I", opcRol.getId_usuariosRol());
                            addMensaje("Transacción realizada con éxito");
                    }else{
                            usuarioRolEJB.actualizarUsuarioRol("A", opcRol.getId_usuariosRol());
                            addMensaje("Transacción realizada con éxito");
                    }			
            }else{
                    UsuariosRol opcRolIng = new UsuariosRol(); 
                    opcRolIng.setRol(seedRol);
                    opcRolIng.setUsuarios(seedOpc);
                    opcRolIng.setObservacion("Creado por Sistema");
                    opcRolIng.setEstado("A");
                    opcRolIng.setFechaRegistro(new Date());
                    usuarioRolEJB.create(opcRolIng);			
            addMensaje("Transacción realizada con éxito");
            }
    }

    public Rol getNuevoRol() {
        return nuevoRol;
    }

    public void setNuevoRol(Rol nuevoRol) {
        this.nuevoRol = nuevoRol;
    }
    
    public void guardarRolNuevo(){
            nuevoRol.setRol(nuevoRol.getRol());
            nuevoRol.setEstado("A");
            nuevoRol.setObservacion("Creado en Sistema");
            nuevoRol.setFechaRegistro(new Date());
            rolEJB.create(nuevoRol);
            inicilizacion();
    }
    
    public void activarInactivarRol(Rol rol){
            rol.setEstado(rol.getEstado().equals("A")?"I":"A");
            rol.setFechaActualizacion(new Date());
            rolEJB.edit(rol);
            inicilizacion();
    }
}
