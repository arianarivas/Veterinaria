/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.EAO;

import com.produccion.configuraciones.UtilCryptography;
import com.produccion.interfaz.UsuariosFacadeLocal;
import com.produccion.entidades.Usuarios;
import com.produccion.entidades.UsuariosRol;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mplua
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "veterinariaPool")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }
    
    @Override
    public Usuarios autenticar(String username, String password){
        Usuarios usuarios = null;
        String consulta;
        try {
            consulta = "FROM Usuarios u WHERE u.username = ?1 AND u.password = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, username);
            query.setParameter(2, UtilCryptography.encriptar(password));
            List<Usuarios> lista = query.getResultList();
            if (!lista.isEmpty()) {
                usuarios = lista.get(0);
            }
        } catch (Exception e) {
            try {
                throw e;
            } catch (Exception ex) {
                Logger.getLogger(UsuariosFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return usuarios;
    }
    
    @Override
    public void cabiarClaveUsuario(Usuarios usuario) 
    {
            String lsQuery = "update usuarios a " + "set a.password = ?, "
                            + "where a.personas_idpersonas = ? ";
            Query query = em.createNativeQuery(lsQuery);
            query.setParameter(1, usuario.getPassword());
            query.setParameter(2, usuario.getPersonas().getIdpersonas());
            query.executeUpdate();
    }
    
    @Override
    public void registrarSession(Usuarios usuario){
        Date date = new Date();
        DateFormat hourdateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("fecha " +hourdateFormat.format(date));
        String lsQuery = "update usuarios a set a.ultima_sesion = '"+hourdateFormat.format(date)+"' where a.personas_idpersonas = ? ";
        Query query = em.createNativeQuery(lsQuery);
        query.setParameter(1, usuario.getId());
        query.executeUpdate();
    }
    
    @Override
    public List<Usuarios> findAllRolUsuario(Integer usuario){
        /*Query query = em.createQuery("select t from UsuariosRol t where t.usuarios = ?1 ");
        query.setParameter(1, idUsuario);*/
        /*String lsQuery = " from usuarios_rol u " +
                         " where u.usuarios_idpersonas = ? and u.estado = 'A' ";
        Query query =  em.createNativeQuery(lsQuery);
        query.setParameter(1, idUsuario);	
        List<UsuariosRol> listaUsuariosRol = query.getResultList();
        return query.getResultList();*/
        System.out.println("el rol es " + usuario);
        Query query = em.createNativeQuery(
                        "select usu.* from usuarios_rol u "+
                         " inner join usuarios usu on u.usuarios_idpersonas = usu.personas_idpersonas  "+
                         " where u.rol_idrol = ? "+
                         " and u.estado = 'A' ", Usuarios.class);
        query.setParameter(1, usuario);

        return query.getResultList();
    }
//    public List<Usuarios> listaOpcionesAsignada(String rol) {
//            Query query = getEntityManager()
//                            .createNativeQuery(
//                                            " select op.* from cls_opciones_roles opr, cls_opciones op, cls_rol r "
//                                                            + " where opr.id_opcion = op.id and opr.id_rol = r.id "
//                                                            + " and op.Modulo_Padre is not null and upper(r.rol) = upper(?) and opr.estado='A' ",
//                                                            Usuarios.class);
//            query.setParameter(1, rol);
//            return query.getResultList();
//    }
}
