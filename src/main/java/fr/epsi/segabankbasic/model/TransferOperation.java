/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author bouboule
 */
@Entity
@NamedQueries({
    @NamedQuery (name = "findAllTransferOperations", query = "SELECT a FROM TransferOperation a WHERE a.sender = :sender OR a.receiver = :receiver")
})
public class TransferOperation extends BankOperation  {
    @ManyToOne
    private GenericAccount receiver;
    
    public TransferOperation(){
        super();
        this.receiver = null;
    }
    
    public TransferOperation(GenericAccount sender, Double amount) {
        super(sender, amount);
    }

    public TransferOperation(GenericAccount sender, Double amount, GenericAccount receiver) {
        super(sender, amount);
        this.receiver = receiver;
    }
    

    @Override
    public String getOperationSummary() {
        return(super.getOperationTrace()+"\nVirement de "+sender.getLogin()+ " vers "+ receiver.getLogin());
    }
    
    
}
