/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author bouboule
 */
@Entity
@NamedQueries({
    @NamedQuery (name = "findAllLocalOperations", query = "SELECT a FROM LocalOperation a WHERE a.sender = :sender")
})
public class LocalOperation extends BankOperation{

    @Override
    public String getOperationSummary() {
        String buffer = "Opération de ";
        if(amount < 0){
            buffer += "débit";
        }
        else{
            buffer += "crédit";
        }
        
        return(super.getOperationTrace()+"\n"+buffer);
    }

    public LocalOperation() {
    }

    public LocalOperation(GenericAccount sender, Double amount) {
        super(sender, amount);
    }
    
    
    
}
