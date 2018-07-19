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
public class SavingsAccount extends GenericAccount{

    public SavingsAccount() {
        super();
        tauxInteret = null;
    }
    
    public SavingsAccount(Agence agence, String login, String password) {
        super(agence, login, password);
        //  Le taux d'intérêts est fixe et prédéterminé.
        //  la variable aurait pu être final ?
        tauxInteret = 1.5;
    }
    
    
    public Double simulateInteret(){
        return solde*(tauxInteret/100);
    }
    
    private Double tauxInteret;

    public Double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(Double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
    
    //  Exécuté une fois tous les ans...
    public void processInterests(){
        solde += solde * (tauxInteret / 100);
    }
    
}
