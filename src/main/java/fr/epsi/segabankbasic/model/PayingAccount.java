/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import fr.epsi.segabankbasic.dao.AgenceDAO;
import fr.epsi.segabankbasic.dao.GenericAccountDAO;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author bouboule
 */
@Entity
@DiscriminatorValue("PayingAccount") // la valeur de la colonne proust
public class PayingAccount extends GenericAccount implements Serializable {

    public PayingAccount(){
        super();
    }
    
    public PayingAccount(Agence agence, String login, String password) {
        super(agence, login, password);
    }
    @Override
    public void executeLocalOperation(Double amount){
        //  substract 5% from operation before executing !
        //  send it to the account's agency moneybags', EZ MONEY
        Double ezmonies = amount * 0.05;
        amount -= ezmonies;
        
        this.sendEzMoneyToAgency(ezmonies);
        
        super.executeLocalOperation(amount);
    }
    
    @Override
    public void executeTransferOperation(Double amount, GenericAccount target){
        //  substract 5% from operation before executing !
        //  send it to the account's agency moneybags', EZ MONEY
        int multiplier = 1;
        if(amount < 0){
            multiplier = -1;
        }
        
        //  On est obligé de réextraire le signe de l'opération 
        //  pour réduire de 5% la valeur absolue du montant de l'opération
        //  Séparer le signe du montant aurait résolu le problème
        Double absAmount = Math.abs(amount);
        
        Double ezmonies = absAmount * 0.05;
        absAmount -= ezmonies;
        absAmount *= multiplier;
        
        //  On envoie l'argent dûment acquis directement dans la poche de la banque
        this.sendEzMoneyToAgency(ezmonies);
        
        super.executeTransferOperation(amount, target, absAmount);
        
    }
    
    public void sendEzMoneyToAgency(Double ezmonies){
        agence.setEzmonies( agence.getEzmonies() + ezmonies );
        
        //  CascadeType.PERSIST ??
        AgenceDAO dao = new AgenceDAO();
        dao.update(agence);
        
        System.out.println("EZ MONIES SENT : "+ ezmonies);
    }
    
}
