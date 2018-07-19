/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.dao;

import fr.epsi.segabankbasic.model.Agence;
import fr.epsi.segabankbasic.util.EntityManagerProvider;

import java.util.List;
import javax.persistence.EntityManager;

import javax.persistence.Query;


/**
 *
 * @author bouboule
 */
public class AgenceDAO implements IDAO<Agence>{
    /*private static final String FIND_AGENCE = "SELECT * FROM agence WHERE code = :code";
    private static final String INSERT_QUERY = "INSERT INTO agence (code, adresse, accounts) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE book SET name = ? WHERE id = ?";*/

    @Override
    public Agence find(String id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        Agence agence = em.find(Agence.class, id);
        
        return agence;
    }
    
    
    public Agence findByCode(String code) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        Query q = em.createNamedQuery("findByCode", Agence.class);
        q.setParameter("code", code);
        Agence agence = (Agence)q.getSingleResult();
        
        return agence;
    }


    @Override
    public void create(Agence agence) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(agence);
        em.getTransaction().commit();
    }

    @Override
    public void update(Agence agence) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(agence);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Agence agence) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.remove(agence);
    }

    @Override
    public List<Agence> findAll() {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        Query q = em.createNamedQuery("findAllAgences", Agence.class);
        List<Agence> listeAgence = (List<Agence>)q.getResultList();
        
        return listeAgence;
        
    }
}
