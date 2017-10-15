/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.produccion.EAO;

import com.produccion.configuraciones.UtilCryptography;
import com.produccion.interfaz.UsuariosFacadeLocal;
import com.produccion.entidades.Usuarios;
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
}
