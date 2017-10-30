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
import java.util.List;
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

    @Override
    public List<Menu> findAllActivos() {
        Query query = em.createNativeQuery(
                "select * from menu "
                + "where estado = 'A' ", Menu.class);
        return query.getResultList();
    }

    @Override
    public List<Menu> findAllMenuRol(Integer menu) {
        System.out.println("el rol es " + menu);
        Query query = em.createNativeQuery(
                "select men.* from menu_rol mr "
                + " inner join menu men on mr.menu_idmenu = men.idmenu  "
                + " where mr.rol_idrol = ? "
                + " and mr.estado = 'A' and men.estado = 'A'", Menu.class);
        query.setParameter(1, menu);

        return query.getResultList();
    }

    @Override
    public List<Menu> findAllMenuPadre() {
        Query query = em.createNativeQuery(
                "select * from menu "
                + " where tipo = 'P' "
                + " and estado = 'A'"
                + "and menu_padre is null ", Menu.class);

        return query.getResultList();
    }

    @Override
    public Menu buscaMenuCodigo(Integer codmenu) {
        Query query = getEntityManager().createNativeQuery("select b.* from menu b where b.idmenu = ? ", Menu.class);
        query.setParameter(1, codmenu);
        Menu men = (Menu) query.getSingleResult();
        return (Menu) men;
    }

}
