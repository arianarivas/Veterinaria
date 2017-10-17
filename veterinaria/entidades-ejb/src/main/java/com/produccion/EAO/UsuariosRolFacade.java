/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.EAO;

import com.produccion.entidades.Rol;
import com.produccion.interfaz.UsuariosRolFacadeLocal;
import com.produccion.entidades.UsuariosRol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mpluas
 */
@Stateless
public class UsuariosRolFacade extends AbstractFacade<UsuariosRol> implements UsuariosRolFacadeLocal {

    @PersistenceContext(unitName = "veterinariaPool")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosRolFacade() {
        super(UsuariosRol.class);
    }
    
    @Override
    public List<UsuariosRol> findAllRolUsuario(Integer idUsuario){
        Query query = em.createQuery("select t from UsuariosRol t where t.usuarios = ?1 ");
        query.setParameter(1, idUsuario);
        List<UsuariosRol> listaUsuariosRol = query.getResultList();
        return query.getResultList();
    }
}
