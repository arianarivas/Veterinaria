/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.interfaz;

import com.produccion.entidades.Usuarios;
import com.produccion.entidades.UsuariosRol;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mpluas
 */
@Local
public interface UsuariosRolFacadeLocal {

    void create(UsuariosRol usuariosRol);

    void edit(UsuariosRol usuariosRol);

    void remove(UsuariosRol usuariosRol);

    UsuariosRol find(Object id);

    List<UsuariosRol> findAll();

    List<UsuariosRol> findRange(int[] range);

    int count();
    
    List<UsuariosRol> findAllRolUsuarios(Usuarios usuario);
    
    UsuariosRol obtenerUsuarioRol(Integer idUsuario, Integer idRol);
    
    void actualizarUsuarioRol(String estado, Integer idOpRol);
    
}
