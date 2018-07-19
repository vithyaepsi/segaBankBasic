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
 *  TransferOperation matérialise les opérations de virements entre deux comptes
 *  Il était à l'origine prévu que les opérations soient bidirectionnellement égales
 *  Ce n'est pas le cas à cause des SavingsAccount
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
    
    //  La provenance du virement est faussée par le doublement de l'enregistrement
    // des TransferOperation
    //  EZ FIX : switcher les provenances lorsque amount est négatif.
    @Override
    public String getOperationSummary() {
        if(amount > 0){
            return(super.getOperationTrace()+"\nVirement de "+receiver.getLogin()+ " vers "+ sender.getLogin());
        }
        else{
            return(super.getOperationTrace()+"\nVirement de "+sender.getLogin()+ " vers "+ receiver.getLogin());
        }
        
    }
    
    
}
