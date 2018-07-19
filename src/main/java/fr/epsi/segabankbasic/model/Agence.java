/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author bouboule
 */
@Entity
@Table(name="agence")
@NamedQueries({
    @NamedQuery (name = "findByCode", query = "SELECT a FROM Agence a WHERE a.code = :code"),
    @NamedQuery (name = "findAllAgences", query = "SELECT a FROM Agence a")
})
public class Agence implements Serializable{
    @Id
    @Column(name= "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    private String code;
    
    private String adresse;
    
    private Double ezmonies;

    public Double getEzmonies() {
        return ezmonies;
    }

    public void setEzmonies(Double ezmonies) {
        this.ezmonies = ezmonies;
    }
    
    @OneToMany(mappedBy="agence", cascade = CascadeType.REMOVE)
    private List<GenericAccount> accounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<GenericAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<GenericAccount> accounts) {
        this.accounts = accounts;
    }
    
    public Agence(){
        code = null;
        adresse = null;
        accounts = new ArrayList<>();
        ezmonies = 0d;
    }
    
    public Agence(String code, String adresse, List<GenericAccount> accounts){
        this.code = code;
        this.adresse = adresse;
        this.accounts = accounts;
        ezmonies = 0d;
    }
    
    public Agence(String code, String adresse, List<GenericAccount> accounts, Double ezmonies){
        this.code = code;
        this.adresse = adresse;
        this.accounts = accounts;
        this.ezmonies = ezmonies;
    }
    
    @Override
    public String toString(){
        return code;
    }
}
