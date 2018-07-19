/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import fr.epsi.segabankbasic.dao.BankOperationDAO;
import fr.epsi.segabankbasic.util.EntityManagerProvider;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author bouboule
 */
@Entity
@Table(name="genericaccount")
@NamedQueries({
    @NamedQuery (name = "findAllAccounts", query = "SELECT a FROM GenericAccount a WHERE a.id != :id"),
    @NamedQuery (name = "findByLogin", query = "SELECT a FROM GenericAccount a WHERE a.login = :login")
    
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "proust")


public abstract class GenericAccount implements Serializable {
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Integer id;
    
    protected Double solde;
    
    //@Column (updatable = false, insertable = false)
    //protected String proust;
    
    @Column(unique=true)
    protected String login;
    protected String password;
    
    @ManyToOne
    protected Agence agence;
    
    //  CascadeType.PERSIST : Persist automatiquement les sender et receiver 
    //  lorsque l'on persist l'opération les possédant !
    //  
    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST)
    protected List<BankOperation> listBankOperationSender;
    
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.PERSIST)
    protected List<TransferOperation> listBankOperationReceiver;

    public List<BankOperation> getListBankOperationSender() {
        return listBankOperationSender;
    }

    public void setListBankOperationSender(List<BankOperation> listBankOperationSender) {
        this.listBankOperationSender = listBankOperationSender;
    }

    public List<TransferOperation> getListBankOperationReceiver() {
        return listBankOperationReceiver;
    }

    public void setListBankOperationReceiver(List<TransferOperation> listBankOperationReceiver) {
        this.listBankOperationReceiver = listBankOperationReceiver;
    }
    
    
    
    
    @Override
    public String toString(){
        return "id : "+ id + " login : "+login + " password :"+password;
    }
    
    
    public GenericAccount(){
        this.solde = 0d;
        this.login = null;
        this.password = null;
        this.agence = null;
        listBankOperationSender = new ArrayList<>();
        listBankOperationReceiver = new ArrayList<>();
    }
    

    public GenericAccount(Agence agence, String login, String password) {
        this();
        this.solde = 0d;
        this.agence = agence;
        this.login = login;
        this.password = password;
    }
    
    public GenericAccount(Agence agence, String login, String password, 
            List<BankOperation> listBankOperationSender,
            List<TransferOperation> listBankOperationReceiver) {
        this(agence, login, password);
        this.listBankOperationSender = listBankOperationSender;
        this.listBankOperationReceiver = listBankOperationReceiver;
    }
    
    //  Le signe de l'opération est contenu dans le montant
    public void executeLocalOperation(Double amount){
        this.solde += amount;
        //  il semble y avoir un dysfonctionnement lorsque appelé par PayingAccount
        //  Il manque un persist ? les opérations effectuées ne sont pas immédiatement 
        //  visibles dans l'historique.
        System.out.println("debug : "+amount);
        
        
        LocalOperation operation = new LocalOperation(this, amount);
        BankOperationDAO dao = new BankOperationDAO();
        
        System.out.println("debug : "+operation.getOperationSummary());
        dao.create(operation);
    }
    
    //  On envoie un montant à quelqu'un, on retire donc le montant du solde
    //  Amount représente le montant à affecter au compte emetteur
    //  newAmount représente le montant après taxes, à affecter au compte cible
    public void executeTransferOperation(Double amount, GenericAccount target, Double newAmount){
        this.solde += -amount;
        target.solde += newAmount;
        
        System.out.println(amount.toString());
        
        TransferOperation operation = new TransferOperation(this, -amount, target);
        
        //  On inverse l'emetteur et le récepteur, car le montant véritablement affecté
        //  n'est pas toujours le même à gauche et à droite.
        TransferOperation operation2 = new TransferOperation(target, newAmount, this);
        BankOperationDAO dao = new BankOperationDAO();
        dao.create(operation);
        dao.create(operation2);
    }
    
    //  Ici le montant doit être le même pour l'emetteur et le récepteur
    public void executeTransferOperation(Double amount, GenericAccount target){
        this.solde += -amount;
        target.solde += amount;
        
        System.out.println(amount.toString());
        
        TransferOperation operation = new TransferOperation(this, amount, target);
        TransferOperation operation2 = new TransferOperation(target, amount, this);
        BankOperationDAO dao = new BankOperationDAO();
        dao.create(operation);
        dao.create(operation2);
    }
    
    
    //  Génère un code de compte 
    //  format : CODE_BANQUE + ID du compte
    public String getAccountBankCode(){
        return agence.getCode() + this.id;
    }
    
    public String getSoldeSummary(){
        String buffer;
        buffer = "---\nVotre solde est de : "+ String.format("%.2f", solde) + "€\n---";
        
        return buffer;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Agence getAgence() {
        return agence;
    }

    public void setAgence(Agence agence) {
        this.agence = agence;
    }
    
    
}
