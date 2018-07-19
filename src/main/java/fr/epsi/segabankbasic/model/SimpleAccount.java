/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import javax.persistence.Entity;

/**
 *
 * @author bouboule
 */
@Entity
public class SimpleAccount extends GenericAccount {
    public SimpleAccount(){
        super();
    }

    public SimpleAccount(Agence agence, String login, String password, int decouvert) {
        super(agence, login, password);
        this.decouvert = decouvert;
    }
    
    private int decouvert;

    public int getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(int decouvert) {
        this.decouvert = decouvert;
    }
    
    @Override
    public void executeLocalOperation(Double amount){
        if(solde - amount >= -decouvert){
            super.executeLocalOperation(amount);
        }
        
    }
    
    
}
