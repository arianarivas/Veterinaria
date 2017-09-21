package com.produccion.controller;

import com.produccion.eao.PersonalFacadeLocal;
import com.produccion.entidades.Personal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 
 * Proyecto - Multicentro de Mascotas
 * @author mpluas - arivas
 * @version 1.0
 * 
 */
@Named
@ViewScoped
public class personalController implements Serializable{
    
    @EJB
    private PersonalFacadeLocal personalEJB;
    
    private List<Personal> personalList;
    private Personal personal;
    
    @PostConstruct
    public void init(){
        personal = new Personal();
        this.personalList = personalEJB.findAll();
    }

    public List<Personal> getPersonalList() {
        return personalList;
    }

    public void setPersonalList(List<Personal> personalList) {
        this.personalList = personalList;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
    
}
