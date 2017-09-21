package com.produccion.implementacion.eao;

import com.produccion.eao.MenuFacadeLocal;
import com.produccion.entidades.Menu;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * Proyecto - Multicentro de Mascotas
 * @author mpluas - arivas
 * @version 1.0
 * 
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> implements MenuFacadeLocal {

    @PersistenceContext(unitName = "veterinariaPool")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenuFacade() {
        super(Menu.class);
    }
    
}
