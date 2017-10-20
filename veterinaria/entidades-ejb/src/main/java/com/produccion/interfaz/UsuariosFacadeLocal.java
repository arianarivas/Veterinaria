/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.interfaz;

import com.produccion.entidades.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mplua
 */
@Local
public interface UsuariosFacadeLocal {

    void create(Usuarios usuarios);

    void edit(Usuarios usuarios);

    void remove(Usuarios usuarios);

    Usuarios find(Object id);

    List<Usuarios> findAll();

    List<Usuarios> findRange(int[] range);

    int count();
    
    Usuarios autenticar(String username, String password);
    
    void cabiarClaveUsuario(Usuarios usuario);
    
    void registrarSession(Usuarios usuario);
    
    List<Usuarios> findAllRolUsuario(Integer usuario);
    
    List<Usuarios> findAllActivos();
}
