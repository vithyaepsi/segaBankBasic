/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.epsi.segabankbasic.main;


import fr.epsi.segabankbasic.dao.AgenceDAO;
import fr.epsi.segabankbasic.dao.BankOperationDAO;
import fr.epsi.segabankbasic.dao.GenericAccountDAO;
import fr.epsi.segabankbasic.model.Agence;
import fr.epsi.segabankbasic.model.GenericAccount;
import fr.epsi.segabankbasic.model.LocalOperation;
import fr.epsi.segabankbasic.model.PayingAccount;
import fr.epsi.segabankbasic.model.SavingsAccount;
import fr.epsi.segabankbasic.model.SimpleAccount;
import fr.epsi.segabankbasic.model.BankOperation;
import fr.epsi.segabankbasic.model.TransferOperation;
import fr.epsi.segabankbasic.util.EntityManagerProvider;
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author bouboule
 */
public class main {
    private static Scanner scanner = new Scanner(System.in);
    
    private static GenericAccount connectedAccount;
    
    public static void main( String[] args ) {
        mainMenu();
        
        
        /*AgenceDAO dao = new AgenceDAO();
        //GenericAccountDAO genericAccountDAO = new GenericAccountDAO();
        
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        em.getTransaction().begin();
        
        Agence agence = new Agence("BANKROUTE", "8 avenue du chien qui chie", new ArrayList<>());
        em.persist(agence);
        
        Agence agence2 = new Agence("PROUTBANK", "112 boulevard Jean Jean", new ArrayList<>());
        em.persist(agence2);
        
        dao.create(new Agence("COINBANK", "12 Rue du canard saoul", new ArrayList<>() ));*/
        
        //em.find(Agence.class, "BANKROUTE");
        
        
        
        /*try{
            //Agence agence = dao.findByCode("BANKROUTE");
            
            List<GenericAccount> generic = genericAccountDAO.findAll();
            for(GenericAccount element : generic){
                System.out.println(element.getId());
            }
            
            
            /*PayingAccount paying = new PayingAccount(agence, "001", "001");
            em.persist(paying);

            SavingsAccount savings = new SavingsAccount(agence, "002", "002");
            em.persist(savings);

            SimpleAccount simple = new SimpleAccount(agence, "003", "003", 120);
            em.persist(simple);*/
            
        /*
        }
        catch(NoResultException ee){
            System.out.println("pas de résults...");
        }*/
       
        //System.out.println(agence.toString() + agence.getCode());
        //agence.setCode("BANKROOTE");
        
        //dao.create(agence);
        //dao.delete(agence);
        
        //em.getTransaction().commit();
        
        
        
    }
    
    
    public static void mainMenu(){
        int response;
       
        do {
            System.out.println( "**************************************" );
            System.out.println( "*****************Menu*****************" );
            System.out.println( "* 1 - Créer un compte                *" );
            System.out.println( "* 2 - Se connecter à un compte       *" );
            System.out.println( "* 7 - Quitter                        *" );
            System.out.println( "**************************************" );
            System.out.print( "*Entrez votre choix : " );
            response = scanner.nextInt();
            scanner.nextLine();
        } while ( 0 >= response || response > 8 );
        switch ( response ) {
            case 1:
                addAccount();
                break;
            case 2:
                connectAccount();
                break;
        }
        
    }
    
    public static void connectAccount(){
        String login = askString("login");
        String password = askString("password");
        
        GenericAccountDAO dao = new GenericAccountDAO();
        GenericAccount account = dao.findByLogin(login);
        if(account != null){
            if( password.equals(account.getPassword()) ){
                connectedAccount = account;
                accountMenu();
            }
            else{
                System.out.println("Il semblerait que votre mot de passe ne"
                        + " soit qu'une excrétion intestinale");
                mainMenu();
            }
        }
        else{
            mainMenu();
        }
        
        
    }
    
    public static void accountMenu(){
        int response;
        Boolean hasSavings = connectedAccount.getClass() == SavingsAccount.class;
        do {
            
            System.out.println( "**************************************" );
            System.out.println( "******Opérations du compte************" );
            System.out.println( "* 1 - Réaliser un virement bancaire  *" );
            System.out.println( "* 2 - Réaliser un débit              *" );
            System.out.println( "* 3 - Créditer votre compte          *" );
            System.out.println( "* 4 - Voir l'historique du compte    *" );
            System.out.println( "* 5 - Consulter le solde             *" );
            if( hasSavings == true ){
                System.out.println( "* 6 - Obtenir les intérêts           *" );
            }
            else{
                System.out.println( "* 6 - Rien d'utile                   *" );
            }
            System.out.println( "* 0 - Quitter                        *" );
            System.out.println( "**************************************" );
            System.out.print( "*Entrez votre choix : " );
            response = scanner.nextInt();
            scanner.nextLine();
        } while ( 0 > response || response > 6 );
        switch ( response ) {
            case 1:
                createTransferOperation();
                accountMenu();
                break;
            case 2:
                createLocalOperation(-1);
                accountMenu();
                break;
            case 3:
                createLocalOperation(1);
                accountMenu();
                break;
            case 4:
                accountHistory();
                accountMenu();
                break;
            case 5:
                consultSolde();
                accountMenu();
                break;
            case 6:
                if( hasSavings == true ){
                    showSavings();
                }
                else{
                    System.out.println("Il n'y a rien ici.");
                }
                
                accountMenu();
            case 0:
                mainMenu();
                break;
        }
    }
    
    public static void showSavings(){
        //  On doit spécialiser connectedAccount pour utiliser simulateInterets();
        //  La vérification de la conformité de la classe est faite dans accountMenu()
        SavingsAccount sa = (SavingsAccount)connectedAccount;
        System.out.println("A la prochaine échéance, votre compte sera crédité de : "+sa.simulateInteret()
        +"€"
        );
    }
    
    public static void accountHistory(){
        //  Les BankOperation faisant partie de l'historique sont seulement
        //  les BankOperation de ListBankOperationSender
        //  listBankOperationReceiver contient des montants faussés.
        
        
        List<BankOperation> operationList = connectedAccount.getListBankOperationSender();
        //operationList.addAll(connectedAccount.getListBankOperationReceiver());
        
        for(BankOperation op : operationList){
            System.out.println( op.getOperationSummary() );
        }
    }
    
    public static void consultSolde(){
        System.out.println(connectedAccount.getSoldeSummary());
    }
    
    public static void createTransferOperation(){
        //  il faut obtenir un compte cible
        //  
        GenericAccount targetAccount = searchAccount();
        if(targetAccount == null){
            return;
        }
        
        Double amount = askInt("montant, seulement positif").doubleValue();
        amount = Math.abs(amount);
        
        System.out.println(amount.toString());
        
        connectedAccount.executeTransferOperation(amount, targetAccount);
        
        
        //  INUTILE AVEC CascadeType.PERSIST ???
        GenericAccountDAO dao = new GenericAccountDAO();
        dao.update(connectedAccount);
        dao.update(targetAccount);
        
    }
    
    //  Multiplier est appliqué automatiquement pour désigner un débit ou crédit
    public static void createLocalOperation(double multiplier){
        Double amount = askInt("montant, seulement positif").doubleValue();
        amount = Math.abs(amount);
        
        connectedAccount.executeLocalOperation(amount * multiplier);
        
        //  INUTILE AVEC CascadeType.PERSIST ???
        GenericAccountDAO dao = new GenericAccountDAO();
        dao.update(connectedAccount);
        
    }
    
    public static void addAccount(){
        int response;
       
        do {
            System.out.println( "**************************************" );
            System.out.println( "******Création compte*****************" );
            System.out.println( "* 1 - Compte payant                  *" );
            System.out.println( "* 2 - Compte épargne                 *" );
            System.out.println( "* 3 - Compte simple                  *" );
            System.out.println( "* 4 - Quitter                        *" );
            System.out.println( "**************************************" );
            System.out.print( "*Entrez votre choix : " );
            response = scanner.nextInt();
            scanner.nextLine();
        } while ( 0 >= response || response > 4 );
        switch ( response ) {
            case 1:
                createPayingAccount();
                mainMenu();
                break;
            case 2:
                createSavingsAccount();
                mainMenu();
                break;
            case 3:
                createSimpleAccount();
                mainMenu();
                break;
        }
    }
    
    public static Agence askAgence(){
        int response;
        Agence selectedAgence = null;
        
        AgenceDAO dao = new AgenceDAO();
        List<Agence> listAgence = dao.findAll();
        int i;
        do {
            System.out.println( "**************************************" );
            System.out.println( "******Sélection d'agence**************" );
            i = 0;
            for(Agence agence : listAgence){
                System.out.println("Compte n°"+ ++i + " : " + agence.toString());
            }
            
            System.out.println( "* 0 - Quitter                        *" );
            System.out.println( "**************************************" );
            System.out.print( "*Entrez votre choix : " );
            response = scanner.nextInt();
            scanner.nextLine();
        } while ( 0 > response || response > i );
        if(response > 0){
            selectedAgence = listAgence.get(response-1);
        }
        
        return selectedAgence;
    }
    
    public static String askString(String subject){
        String response = null;

        int i;
        do {
            
            System.out.println( "Entrez votre "+ subject +" : " );
            response = scanner.nextLine();
        } while ( response == null );
        System.out.println(response);
        return response;
        
    }
    
    public static Integer askInt(String subject){
        Integer response;

        int i;
        do {
            
            System.out.println( "Entrez votre "+ subject +" : " );
            response = scanner.nextInt();
            scanner.nextLine();
        } while ( response == null );
        return response;
        
    }
    
    public static GenericAccount searchAccount(){
        GenericAccountDAO dao = new GenericAccountDAO();
        List<GenericAccount> accountList = dao.findAll(connectedAccount.getId());
        GenericAccount searchedAccount = null;
        int i;
        int response;
        do{
            i = 0;
            for(GenericAccount account : accountList){
                System.out.println( "*Compte n°"+ ++i + " : " + account.getAccountBankCode() );
            }
            System.out.println( "* 0 - Quitter                        *" );
            System.out.print( "*Entrez le numéro du compte : " );
            
            response = scanner.nextInt();
            scanner.nextLine();
            
        } while ( 0 > response || response > i );
        if(response > 0){
            searchedAccount = accountList.get(response-1);
        }
        
        return searchedAccount;
    }
    
    
    public static void createPayingAccount(){
        //  things to get : agence, login, password
       Agence agence = askAgence();
       if(agence == null){
           mainMenu();
           return;
       }
       String login = askString("login");
       String password = askString("password");
       
       PayingAccount newAccount = new PayingAccount(agence, login, password);
       
       GenericAccountDAO dao = new GenericAccountDAO();
       dao.create(newAccount);
       
    }
    public static void createSavingsAccount(){
        //  things to get : agence, login, password
        //  tauxInteret is fixed (for now)
       Agence agence = askAgence();
       if(agence == null){
           mainMenu();
           return;
       }
       String login = askString("login");
       String password = askString("password");
       
       SavingsAccount newAccount = new SavingsAccount(agence, login, password);
       
       GenericAccountDAO dao = new GenericAccountDAO();
       dao.create(newAccount);
       
    }
    public static void createSimpleAccount(){
        //  things to get : agence, login, password, decouvert
       Agence agence = askAgence();
       if(agence == null){
           mainMenu();
           return;
       }
       String login = askString("login");
       String password = askString("password");
       Integer decouvert = askInt("découvert");
       
       SimpleAccount newAccount = new SimpleAccount(agence, login, password, Math.abs(decouvert) );
       
       GenericAccountDAO dao = new GenericAccountDAO();
       dao.create(newAccount);
       
    }
}
