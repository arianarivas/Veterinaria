/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.EAO;

import com.produccion.interfaz.MenuFacadeLocal;
import com.produccion.entidades.Menu;
import com.produccion.entidades.MenuRol;
import com.produccion.entidades.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mplua
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
    
    @Override
    public boolean permitirAccesoOpcion(Menu opcion, Usuarios usuario) {
            String querySql = "select * "
                            + " from menu_rol f "
                            + " where f.rol_idrol in "
                            + "     (select d.rol_idrol from usuarios_rol d where d.usuarios_idpersonas = ? "
                            + " and d.estado = 'A'" + "      ) " + " and f.menu_idmenu = ? "
                            + " and f.estado = 'A'";

            Query query = em.createNativeQuery(querySql,
                            MenuRol.class);
            query.setParameter(1, usuario.getPersonas().getIdpersonas());
            query.setParameter(2, opcion.getIdmenu());
            return query.getResultList().size() > 0;
    }
    
}
