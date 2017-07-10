package com.pucmm.practica4.services;

import com.pucmm.practica4.entidades.Paste;
import com.pucmm.practica4.entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by john on 25/06/17.
 */


public class PasteServices extends GestionDb<Paste>  {
    @PersistenceContext
    private EntityManager manager;
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

    public void deleteByDate(){
        long fecha = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
        EntityManager entityManager = getEntityManager();
        try {
            Query query= entityManager.createQuery("delete from Paste p where p.fechaExpiracion <:fecha");
            query.setParameter("fecha", fecha);
            System.out.println(fecha);
            query.executeUpdate();
        } catch(Exception e) {
            entityManager.getTransaction().setRollbackOnly();
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    public List<Paste> selectByDate(){
        long fecha = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
        EntityManager entityManager = getEntityManager();

            Query query= entityManager.createQuery("select p  from Paste p where p.fechaExpiracion <:fecha");
            query.setParameter("fecha", fecha);
            System.out.println(fecha);
            return query.getResultList();

    }



    public List<Paste> getPasteByCantAccAndPublic(int startPosition){

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select p from Paste p where p.tipoExposicion =:tipoexposicion ORDER BY p.cantidadVista DESC ");
                query.setParameter("tipoexposicion","public");
                query.setFirstResult(startPosition);
                query.setMaxResults(10);
        return query.getResultList();
    }

    public List<Paste> findLastPaste(int val1){
        EntityManager entityManager = getEntityManager();
        Query query= entityManager.createQuery( "select p from Paste p where p.tipoExposicion =:tipoexposicion ORDER BY p.cantidadVista DESC ");
        query.setParameter("tipoexposicion","public");
        if(val1<0){
            val1 =13+val1;
            query.setFirstResult(0);
            query.setMaxResults(val1);
        }else{
            query.setFirstResult(val1);
            query.setMaxResults(13);
        }

        return query.getResultList();
    }

}
