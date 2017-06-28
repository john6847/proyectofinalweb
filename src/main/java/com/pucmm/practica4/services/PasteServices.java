package com.pucmm.practica4.services;

import com.pucmm.practica4.entidades.Paste;
import com.pucmm.practica4.entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by john on 25/06/17.
 */

public class PasteServices extends GestionDb<Paste>  {

    private static PasteServices instancia;
    private PasteServices (){
        super(Paste.class);
    }

    public static PasteServices getInstancia(){
        if(instancia==null){
            instancia = new PasteServices();
        }
        return instancia;
    }


    public List getPaste(String titulo){
        EntityManager entityManager = getEntityManager();
        Query query= entityManager.createNamedQuery("Usuario.findByUsername");
        query.setParameter("username", titulo+"%");
        return query.getResultList();
    }

}
