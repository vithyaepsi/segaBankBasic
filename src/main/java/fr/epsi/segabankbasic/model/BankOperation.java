/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author bouboule
 */
@Entity
@Table(name="bankoperation")

public abstract class BankOperation implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected int id;
    
    @ManyToOne
    protected GenericAccount sender;
    
    protected Double amount;
    
    public abstract String getOperationSummary();
    
    public String getOperationTrace(){
        String buffer = "";
        if(amount < 0){
            buffer += "-";
        }
        else{
            buffer += "+";
        }
        buffer += amount + "€";
        
        return buffer;
    }
    
    
    public BankOperation(){
        this.sender = null;
        this.amount = 0d;
        
    }

    public BankOperation(GenericAccount sender, Double amount) {
        this.sender = sender;
        this.amount = amount;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GenericAccount getSender() {
        return sender;
    }

    public void setSender(GenericAccount sender) {
        this.sender = sender;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    
    
}
