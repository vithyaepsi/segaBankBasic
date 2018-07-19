/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.dao;

import fr.epsi.segabankbasic.model.Agence;
import fr.epsi.segabankbasic.model.BankOperation;
import fr.epsi.segabankbasic.util.EntityManagerProvider;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bouboule
 */
public class BankOperationDAO implements IDAO<BankOperation>{

    @Override
    public BankOperation find(String id) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        BankOperation bankOperation = em.find(BankOperation.class, id);
        
        return bankOperation;
    }

    @Override
    public List<BankOperation> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(BankOperation bankOperation) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.persist(bankOperation);
    }

    @Override
    public void update(BankOperation bankOperation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(BankOperation bankOperation) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        em.remove(bankOperation);
    }
    
   

    
}
