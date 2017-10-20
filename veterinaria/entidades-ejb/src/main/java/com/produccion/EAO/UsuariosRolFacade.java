/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.EAO;

import com.produccion.entidades.Rol;
import com.produccion.entidades.Usuarios;
import com.produccion.interfaz.UsuariosRolFacadeLocal;
import com.produccion.entidades.UsuariosRol;
import java.util.Date;
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
    public List<UsuariosRol> findAllRolUsuarios(Usuarios usuario){
        /*Query query = em.createQuery("select t from UsuariosRol t where t.usuarios = ?1 ");
        query.setParameter(1, idUsuario);*/
        /*String lsQuery = " from usuarios_rol u " +
                         " where u.usuarios_idpersonas = ? and u.estado = 'A' ";
        Query query =  em.createNativeQuery(lsQuery);
        query.setParameter(1, idUsuario);	
        List<UsuariosRol> listaUsuariosRol = query.getResultList();
        return query.getResultList();*/
        Query query = em.createNativeQuery(
                        "select * from "+
                                        " usuarios_rol u "+
                                        " where u.usuarios_idpersonas = ? "+
                                        " and u.estado = 'A' ", UsuariosRol.class);
        query.setParameter(1, usuario.getId());

        return query.getResultList();
    }
    
    @Override
    public UsuariosRol obtenerUsuarioRol(Integer idUsuario, Integer idRol){
            Query query = getEntityManager().createNativeQuery("select op.* from usuarios_rol op "+
                            " where op.usuarios_idpersonas = ? "+
                            " and op.rol_idrol = ? ", UsuariosRol.class);
            query.setParameter(1, idUsuario);
            query.setParameter(2, idRol);		
            List<UsuariosRol> resultado = query.getResultList();
            if (!resultado.isEmpty()) {
                    return resultado.get(0);
            } else {
                    return null;
            }
    }
    
    @Override
    public void actualizarUsuarioRol(String estado, Integer idOpRol){
            String lsQuery = " update usuarios_rol b "+
                            " set b.estado = ?, b.fecha_actualizacion = ? "+
                            " where b.id_usuariosRol = ? ";
            Query query =  getEntityManager().createNativeQuery(lsQuery);
            query.setParameter(1, estado);
            query.setParameter(2, new Date());
            query.setParameter(3, idOpRol);
            query.executeUpdate();
    }
}
