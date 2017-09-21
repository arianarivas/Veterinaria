package com.produccion.eao;

import com.produccion.entidades.Rol;
import java.util.List;
import javax.ejb.Local;

/**
 * 
 * Proyecto - Multicentro de Mascotas
 * @author mpluas - arivas
 * @version 1.0
 * 
 */
@Local
public interface RolFacadeLocal {

    void create(Rol rol);

    void edit(Rol rol);

    void remove(Rol rol);

    Rol find(Object id);

    List<Rol> findAll();

    List<Rol> findRange(int[] range);

    int count();
    
}
