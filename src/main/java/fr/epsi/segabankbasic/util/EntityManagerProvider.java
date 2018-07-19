/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author bouboule
 */
public class EntityManagerProvider {
    private static EntityManager em = null;
    
    public static EntityManager getEntityManager(){
        if(em == null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("fr.epsi_segaBankBasic_jar_0.1PU");
            em = emf.createEntityManager();
        }
        return em;
        
    }
    
}
