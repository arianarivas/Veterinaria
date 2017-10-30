/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.interfaz;

import com.produccion.entidades.Menu;
import com.produccion.entidades.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mplua
 */
@Local
public interface MenuFacadeLocal {

    void create(Menu menu);

    void edit(Menu menu);

    void remove(Menu menu);

    Menu find(Object id);

    List<Menu> findAll();

    List<Menu> findRange(int[] range);

    int count();

    boolean permitirAccesoOpcion(Menu opcion, Usuarios usuario);

    List<Menu> findAllActivos();

    List<Menu> findAllMenuRol(Integer menu);

    List<Menu> findAllMenuPadre();

    Menu buscaMenuCodigo(Integer codmenu);

}
