/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.EAO;

import com.produccion.interfaz.RolFacadeLocal;
import com.produccion.entidades.Rol;
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
public class RolFacade extends AbstractFacade<Rol> implements RolFacadeLocal {

    @PersistenceContext(unitName = "veterinariaPool")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }
    
    @Override
    public List<Rol> findAllRol(){
        Query query = em.createQuery("select t.rol from Rol t");
        List<Rol> listaRoles = query.getResultList();
        return query.getResultList();
    }
    
}
