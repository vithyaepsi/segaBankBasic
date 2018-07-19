/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.dao;

import fr.epsi.segabankbasic.model.*;
import fr.epsi.segabankbasic.util.EntityManagerProvider;
import java.util.List;
import javassist.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bouboule
 */
public class GenericAccountDAO implements IDAO<GenericAccount> {

    @Override
    public GenericAccount find(String id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        GenericAccount account = em.find(GenericAccount.class, id);
        return account;
    }

    @Override
    public List<GenericAccount> findAll() {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        Query q = em.createNamedQuery("findAllAccounts", GenericAccount.class);
        List<GenericAccount> generic = (List<GenericAccount>)q.getResultList();
        
        return generic;
    }
    
    //  findAll avec un ID permet d'ignorer un ID
    //  (pour afficher la liste des comptes, sauf son propre compte)
    public List<GenericAccount> findAll(int id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        Query q = em.createNamedQuery("findAllAccounts", GenericAccount.class);
        q.setParameter("id", id);
        List<GenericAccount> generic = (List<GenericAccount>)q.getResultList();
        
        return generic;
    }
    
    public GenericAccount findByLogin(String login) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        Query q = em.createNamedQuery("findByLogin", GenericAccount.class);
        q.setParameter("login", login);
        GenericAccount account;
        try{
            account = (GenericAccount)q.getSingleResult();
        }
        catch(NoResultException ee){
            account = null;
        }
        
        return account;
    }

    @Override
    public void create(GenericAccount account) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
        //System.out.println("Persisted : " + account.toString());
    }

    @Override
    public void update(GenericAccount account) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    @Override
    public void delete(GenericAccount account) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.remove(account);
    }
    
    
}
