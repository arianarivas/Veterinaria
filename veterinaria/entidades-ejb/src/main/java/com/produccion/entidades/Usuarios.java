/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author mplua
 */
@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable{

    private static final long serialVersionUID = 1L;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @Size(max = 150)
    @Column(name = "password")
    private String password;
    @Size(max = 250)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Column(name = "ultima_sesion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaSesion;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;
    @Column(name = "estado")
    private String estado;
    @Lob
    @Column(name = "imagen")
    private byte[] imagen;
    @Id 
    @Column(name="personas_idpersonas") 
    Integer id;
    
    @MapsId 
    @OneToOne(mappedBy = "usuarios")
    @JoinColumn(name = "personas_idpersonas")
    private Personas personas;
//    @ManyToOne
//    @JoinColumn(name = "rol_idrol", referencedColumnName = "idrol")
//    private Rol rol;
    @OneToMany(mappedBy="usuarios", fetch = FetchType.EAGER)
    private List<UsuariosRol> usuariosRoles;
    
    public Usuarios() {
        this.usuariosRoles = new ArrayList<>();
    }

    public Usuarios(String username, String password, String nombreCompleto, byte[] imagen, Date ultimaSesion, Date fechaRegistro, Date fechaActualizacion, String estado) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.imagen = imagen;
        this.ultimaSesion = ultimaSesion;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
        this.estado = estado;
        this.usuariosRoles = new ArrayList<>();
    }

    public Usuarios(Personas personas) {
        this.personas = personas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getUltimaSesion() {
        return ultimaSesion;
    }

    public void setUltimaSesion(Date ultimaSesion) {
        this.ultimaSesion = ultimaSesion;
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
        if(this.personas.getUsuarios()!= this){
            this.personas.setUsuarios(this);
        }
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<UsuariosRol> getUsuariosRoles() {
        return usuariosRoles;
    }

    public void setUsuariosRoles(List<UsuariosRol> usuariosRoles) {
        this.usuariosRoles = usuariosRoles;
    }

//    public Rol getRol() {
//        return rol;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public void setRol(Rol rol) {
//        this.rol = rol;
//        if(!this.rol.getUsuariosList().contains(this)){
//            this.rol.getUsuariosList().add(this);
//        }
//    }

}
    
