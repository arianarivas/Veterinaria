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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author mpluas
 */
@Named
@ViewScoped
public class usuarioController extends BeanFormulario implements Serializable{
    
    @EJB
    private UsuariosFacadeLocal usuarioEJB;
    @EJB
    private UsuariosRolFacadeLocal usuarioRolEJB;
    @EJB
    private RolFacadeLocal rolEJB;
    private List<Usuarios> listaUsuarios;
    private List<Rol> listaRoles;
    private List<UsuariosRol> listaUsuariosRol;
    private Usuarios usuarios;
    private UsuariosRol usuariosRol;
    private Boolean[][] opcionesAsignadas;
    
    @PostConstruct
    public void init(){
        inicilizacion();

    }
private void inicilizacion(){
        
        listaUsuarios = usuarioEJB.findAll();
        listaRoles = rolEJB.findAll();
        opcionesAsignadas();
        listaUsuariosRol = new ArrayList<>();
            usuarios = new Usuarios();
        
}
    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public UsuariosRol getUsuariosRol() {
        return usuariosRol;
    }

    public void setUsuariosRol(UsuariosRol usuariosRol) {
        this.usuariosRol = usuariosRol;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    
    public List<UsuariosRol> ListaUsuarioRol(Usuarios usuario){
        listaUsuariosRol = usuarioRolEJB.findAllRolUsuarios(usuario);
        return listaUsuariosRol;
    }
    
    public List<UsuariosRol> getListaUsuariosRol() {
        return listaUsuariosRol;
    }

    public void setListaUsuariosRol(List<UsuariosRol> listaUsuariosRol) {
        this.listaUsuariosRol = listaUsuariosRol;
    }
    
    public String estado(Usuarios usuario){
        String estado = "";
        if (usuario.getEstado().equals("A")) {
            estado = "Activo";
        }else{
            estado = "Inactivo";
        }
        return estado;
    }
   

    public Boolean[][] getOpcionesAsignadas() {
        return opcionesAsignadas;
    }

    public void setOpcionesAsignadas(Boolean[][] opcionesAsignadas) {
        this.opcionesAsignadas = opcionesAsignadas;
    }

    public void opcionesAsignadas(){
            int cont=0, iRol=0, iOpc=0;
            opcionesAsignadas = new Boolean[listaRoles.size()][listaUsuarios.size()];
            for (Rol i : listaRoles) {			
                    List <Usuarios> opcionesTemporalesAsignadas = usuarioEJB.findAllRolUsuario(i.getIdrol());//servicioMantenimiento.listaOpcionesAsignada(i.getRol());		
                    for (Usuarios opcTerm : listaUsuarios) {
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
                    }else{
                            usuarioRolEJB.actualizarUsuarioRol("A", opcRol.getId_usuariosRol());				
                    }			
            }else{
                    UsuariosRol opcRolIng = new UsuariosRol(); 
                    opcRolIng.setRol(seedRol);
                    opcRolIng.setUsuarios(seedOpc);
                    opcRolIng.setObservacion("Creado por Sistema");
                    opcRolIng.setEstado("A");
                    opcRolIng.setFechaRegistro(new Date());
                    usuarioRolEJB.create(opcRolIng);			
                    //INGRESO DE OPCION PADRE 
                    /*CfOpciones OpcPadre = servicioMantenimiento.obtenerOpcionPadre(seedOpc.getId().intValue());
                    CfOpciones IngOpcPad = new CfOpciones();
                    IngOpcPad.setId(OpcPadre.getOpcionPadre().getId());
                    CfOpcionesRoles opcRolPadreI = servicioMantenimiento.obtenerOpcionRol(IngOpcPad.getId().intValue(), rol.intValue());
                    if(opcRolPadreI==null){
                            CfOpcionesRoles opcRolIngP = new CfOpcionesRoles(); 
                            opcRolIngP.setRol(seedRol);
                            opcRolIngP.setOpciones(IngOpcPad);
                            opcRolIngP.setEstado("A");
                            opcRolIngP.setFechaRegistro(new Date());
                            servicioMantenimiento.ingresarOpcionRol(opcRolIngP);
                    }
            }		
            CfOpciones OpcPadre = servicioMantenimiento.obtenerOpcionPadre(opc.intValue());
            CfOpcionesRoles opcRolPadre = servicioMantenimiento.obtenerOpcionRol(OpcPadre.getOpcionPadre().getId().intValue(), rol.intValue());		
            if(opcRolPadre!=null){
                    if(servicioMantenimiento.cantidadOpcionesPorPadre(rol.intValue(),OpcPadre.getOpcionPadre().getId().intValue()) < 1 ){
                            servicioMantenimiento.actualizarOpcionesRol("I", opcRolPadre.getId().intValue());				
                    }else{
                            servicioMantenimiento.actualizarOpcionesRol("A", opcRolPadre.getId().intValue());
                    }*/
        addMensaje("Transacción realizada con éxito");
            }
    }
    }
    

