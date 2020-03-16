/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2crud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static java.util.Objects.isNull;

/**
 *
 * @author reyno
 */
public class Lab2CRUD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //let's do the standard import.
        MyDB mydb = new MyDB();

        //let's delete the current table if it exists.
        mydb.dropInvoicesTable();
        //now create the table.
        mydb.createInvoicesTable();

        //let's create an invoice to send
        Boolean tryCreate = mydb.createRecord(new Myinvoice("100000001", "New Test Invoice", "000001", "$1000.00", "$100.00"));
        System.out.println(String.format("Created Record: %s", tryCreate));

        //and let's try retrieving this record.
        Myinvoice myinvoice = mydb.getRecord("100000001");
        System.out.println(String.format("Invoice ID: %s, Description: %s", myinvoice.getInvoiceID().trim(), myinvoice.getDescription().trim()));

        //let's update an invoice to send
        Boolean tryUpdate = mydb.updateRecord(new Myinvoice("100000001", "Updated Invoice", "000002", "$9999.00", "$999.00"));
        System.out.println(String.format("Updated Record: %s", tryUpdate));

        //and let's try retrieving this record.
        myinvoice = mydb.getRecord("100000001");
        System.out.println(String.format("Updated Invoice: \nInvoice ID: %s, Description: %s", myinvoice.getInvoiceID().trim(), myinvoice.getDescription().trim()));

        //let's see if we can delete a record.
        Boolean tryDelete = mydb.deleteRecord("100000001");
        System.out.println(String.format("Deleed Record: %s", tryDelete));

        //let's display the main menu.
        MainMenu();
    }

    public static void GetAllInvoices() {
        //let's start by getting all the invoices.
        MyDB mydb = new MyDB();
        ArrayList<Myinvoice> myinvoices = mydb.getAllRecords();

        //let's cycle them out.
        System.out.println("\n\n These are all the records we have:");

        for (Myinvoice myinvoice : myinvoices) {
            String str = String.format("Invoice ID: %s | Description: %s | UserID: %s | Amount: %s | Tax: %s", myinvoice.getInvoiceID().trim(), myinvoice.getDescription().trim(), myinvoice.getUserId().trim(), myinvoice.getAmount().trim(), myinvoice.getTax().trim());
            System.out.println(str);
        }

        System.out.println("\nComplete. \n");
    }

    public static void NewInvoice() {
        //let's start by getting all the invoices.
        MyDB mydb = new MyDB();
        Boolean inserted = false;

        //let's ask the basics. But we will do it in a loop.
        while (!inserted) {
            String invoiceid = "";
            String description = "";
            String userid = "";
            String amount = "";
            String tax = "";
            
            try {
                //wait for a response.
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                //Ask for the option to select
                if(!invoiceid.isEmpty()){
                    System.out.print(String.format("\nInsert Invoice ID [%s]: ", invoiceid));
                }else{
                    System.out.print("\nInsert Invoice ID: ");
                }
                
                //wait for the readline
                String tInvoiceid = in.readLine();
                if(tInvoiceid.isEmpty() && invoiceid.isEmpty()){
                    while(!tInvoiceid.isEmpty()){
                        System.out.println("Please enter an invoice ID.");
                        tInvoiceid = in.readLine();
                    }
                    invoiceid = tInvoiceid;
                }else if(!tInvoiceid.isEmpty()){
                    invoiceid = tInvoiceid;
                }
                
                //to get around the basic length requirements for 
                //SQL.
                if (invoiceid.length() > 10) {
                    invoiceid = invoiceid.substring(0, 10);
                }

                //Ask for the option to select
                if(!description.isEmpty()){
                    System.out.print(String.format("\nInsert Description [%s]: ", description));
                }else{
                    System.out.print("\nInsert description: ");
                }
                
                //wait for the readline
                String tDescription = in.readLine();
                if(tDescription.isEmpty() && tDescription.isEmpty()){
                    while(!tDescription.isEmpty()){
                        System.out.println("Please enter an Description.");
                        tDescription = in.readLine();
                    }
                    description = tDescription;
                }else if(!tDescription.isEmpty()){
                    description = tDescription;
                }
                
                if (description.length() > 254) {
                    description = description.substring(0, 254);
                }
                
                //Ask for the option to select
                if(!userid.isEmpty()){
                    System.out.print(String.format("\nInsert User ID [%s]: ", userid));
                }else{
                    System.out.print("\nInsert User ID: ");
                }
                
                //wait for the readline
                String tUserid = in.readLine();
                if(tUserid.isEmpty() && tUserid.isEmpty()){
                    while(!tUserid.isEmpty()){
                        System.out.println("Please enter an User ID.");
                        tUserid = in.readLine();
                    }
                    userid = tUserid;
                }else if(!tUserid.isEmpty()){
                    userid = tUserid;
                }
                
                if (userid.length() > 6) {
                    userid = userid.substring(0, 6);
                }
                
                //Ask for the option to select
                if(!amount.isEmpty()){
                    System.out.print(String.format("\nInsert Amount [%s]: ", amount));
                }else{
                    System.out.print("\nInsert Amount: ");
                }
                
                //wait for the readline
                String tAmount = in.readLine();
                if(tAmount.isEmpty() && tAmount.isEmpty()){
                    while(!tAmount.isEmpty()){
                        System.out.println("Please enter an Amount.");
                        tAmount = in.readLine();
                    }
                    amount = tAmount;
                }else if(!tAmount.isEmpty()){
                    amount = tAmount;
                }
                
                if (amount.length() > 10) {
                    amount = amount.substring(0, 10);
                }
                
                //Ask for the option to select
                if(!tax.isEmpty()){
                    System.out.print(String.format("\nInsert Tax [%s]: ", tax));
                }else{
                    System.out.print("\nInsert Tax: ");
                }
                
                //wait for the readline
                String tTax = in.readLine();
                if(tTax.isEmpty() && tTax.isEmpty()){
                    while(!tTax.isEmpty()){
                        System.out.println("Please enter tax.");
                        tTax = in.readLine();
                    }
                    tax = tTax;
                }else if(!tTax.isEmpty()){
                    tax = tTax;
                }
                
                if (tax.length() > 10) {
                    tax = tax.substring(0, 10);
                }
                
                //now we attempt the insert
                inserted = mydb.createRecord(new Myinvoice(invoiceid, description, userid, amount, tax));
                
            } catch (IOException e) {
                System.out.println("That option is invalid, Please try again.");
                //reset the option value.
                inserted = false;
            }
        }
    }
    
     public static void UpdateInvoice() {
        //let's start by getting all the invoices.
        MyDB mydb = new MyDB();
        Boolean updated = false;

        //let's ask the basics. But we will do it in a loop.
        while (!updated) {
            String invoiceid = "";
            String description = "";
            String userid = "";
            String amount = "";
            String tax = "";
            
            try {
                //wait for a response.
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                //Ask for the option to select
                if(!invoiceid.isEmpty()){
                    System.out.print(String.format("\nInsert Invoice ID [%s]: ", invoiceid));
                }else{
                    System.out.print("\nInsert Invoice ID: ");
                }
                
                //wait for the readline
                String tInvoiceid = in.readLine();
                if(tInvoiceid.isEmpty() && invoiceid.isEmpty()){
                    while(!tInvoiceid.isEmpty()){
                        System.out.println("Please enter an invoice ID.");
                        tInvoiceid = in.readLine();
                    }
                    invoiceid = tInvoiceid;
                }else if(!tInvoiceid.isEmpty()){
                    invoiceid = tInvoiceid;
                }
                
                //to get around the basic length requirements for 
                //SQL.
                if (invoiceid.length() > 10) {
                    invoiceid = invoiceid.substring(0, 10);
                }

                //Ask for the option to select
                if(!description.isEmpty()){
                    System.out.print(String.format("\nInsert Description [%s]: ", description));
                }else{
                    System.out.print("\nInsert description: ");
                }
                
                //wait for the readline
                String tDescription = in.readLine();
                if(tDescription.isEmpty() && tDescription.isEmpty()){
                    while(!tDescription.isEmpty()){
                        System.out.println("Please enter an Description.");
                        tDescription = in.readLine();
                    }
                    description = tDescription;
                }else if(!tDescription.isEmpty()){
                    description = tDescription;
                }
                
                if (description.length() > 254) {
                    description = description.substring(0, 254);
                }
                
                //Ask for the option to select
                if(!userid.isEmpty()){
                    System.out.print(String.format("\nInsert User ID [%s]: ", userid));
                }else{
                    System.out.print("\nInsert User ID: ");
                }
                
                //wait for the readline
                String tUserid = in.readLine();
                if(tUserid.isEmpty() && tUserid.isEmpty()){
                    while(!tUserid.isEmpty()){
                        System.out.println("Please enter an User ID.");
                        tUserid = in.readLine();
                    }
                    userid = tUserid;
                }else if(!tUserid.isEmpty()){
                    userid = tUserid;
                }
                
                if (userid.length() > 6) {
                    userid = userid.substring(0, 6);
                }
                
                //Ask for the option to select
                if(!amount.isEmpty()){
                    System.out.print(String.format("\nInsert Amount [%s]: ", amount));
                }else{
                    System.out.print("\nInsert Amount: ");
                }
                
                //wait for the readline
                String tAmount = in.readLine();
                if(tAmount.isEmpty() && tAmount.isEmpty()){
                    while(!tAmount.isEmpty()){
                        System.out.println("Please enter an Amount.");
                        tAmount = in.readLine();
                    }
                    amount = tAmount;
                }else if(!tAmount.isEmpty()){
                    amount = tAmount;
                }
                
                if (amount.length() > 10) {
                    amount = amount.substring(0, 10);
                }
                
                //Ask for the option to select
                if(!tax.isEmpty()){
                    System.out.print(String.format("\nInsert Tax [%s]: ", tax));
                }else{
                    System.out.print("\nInsert Tax: ");
                }
                
                //wait for the readline
                String tTax = in.readLine();
                if(tTax.isEmpty() && tTax.isEmpty()){
                    while(!tTax.isEmpty()){
                        System.out.println("Please enter tax.");
                        tTax = in.readLine();
                    }
                    tax = tTax;
                }else if(!tTax.isEmpty()){
                    tax = tTax;
                }
                
                if (tax.length() > 10) {
                    tax = tax.substring(0, 10);
                }
                
                //now we attempt the insert
                updated = mydb.updateRecord(new Myinvoice(invoiceid, description, userid, amount, tax));
                
            } catch (IOException e) {
                System.out.println("That option is invalid, Please try again.");
                //reset the option value.
                updated = false;
            }
        }
    }
    
    public static void GetSpecificInvoice(){
        String invoiceid = "";
        Boolean retrievedRecord = false;
        MyDB mydb = new MyDB();
        
        while(!retrievedRecord){
            try {
                //wait for a response.
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                //Ask for the option to select
                if(!invoiceid.isEmpty()){
                    System.out.print(String.format("\nInsert Invoice ID [%s]: ", invoiceid));
                }else{
                    System.out.print("\nInsert Invoice ID: ");
                }
                
                //wait for the readline
                String tInvoiceid = in.readLine();
                if(tInvoiceid.isEmpty() && invoiceid.isEmpty()){
                    while(!tInvoiceid.isEmpty()){
                        System.out.println("Please enter an invoice ID.");
                        tInvoiceid = in.readLine();
                    }
                    invoiceid = tInvoiceid;
                }else if(!tInvoiceid.isEmpty()){
                    invoiceid = tInvoiceid;
                }
                
                //to get around the basic length requirements for 
                //SQL.
                if (invoiceid.length() > 10) {
                    invoiceid = invoiceid.substring(0, 10);
                }

               Myinvoice myinvoice = mydb.getRecord(invoiceid);
               if(!isNull( myinvoice )){
                   //print the invoice details here.
                   String str = String.format("Invoice ID: %s | Description: %s | UserID: %s | Amount: %s | Tax: %s", myinvoice.getInvoiceID().trim(), myinvoice.getDescription().trim(), myinvoice.getUserId().trim(), myinvoice.getAmount().trim(), myinvoice.getTax().trim());
                   System.out.println(str);
                   retrievedRecord = true;
               }else{
                   retrievedRecord = false;
               }
            } catch (IOException e) {
                System.out.println("That option is invalid, Please try again.");
                //reset the option value.
                retrievedRecord = false;
            }
        }
    }
    
    public static void DeleteInvoice(){
        String invoiceid = "";
        Boolean deletedRecord = false;
        MyDB mydb = new MyDB();
        
        while(!deletedRecord){
            try {
                //wait for a response.
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                //Ask for the option to select
                if(!invoiceid.isEmpty()){
                    System.out.print(String.format("\nInsert Invoice ID [%s]: ", invoiceid));
                }else{
                    System.out.print("\nInsert Invoice ID: ");
                }
                
                //wait for the readline
                String tInvoiceid = in.readLine();
                if(tInvoiceid.isEmpty() && invoiceid.isEmpty()){
                    while(!tInvoiceid.isEmpty()){
                        System.out.println("Please enter an invoice ID.");
                        tInvoiceid = in.readLine();
                    }
                    invoiceid = tInvoiceid;
                }else if(!tInvoiceid.isEmpty()){
                    invoiceid = tInvoiceid;
                }
                
                //to get around the basic length requirements for 
                //SQL.
                if (invoiceid.length() > 10) {
                    invoiceid = invoiceid.substring(0, 10);
                }

               deletedRecord = mydb.deleteRecord(invoiceid);
            } catch (IOException e) {
                System.out.println("That option is invalid, Please try again.");
                //reset the option value.
                deletedRecord = false;
            }
        }
    }
    
    public static void MainMenu() {
        //set the option
        int option = -1;

        //let's do a choice.
        ArrayList<String> MenuItems = new ArrayList();

        //add menu items.
        MenuItems.add("0. Quit");
        MenuItems.add("1. New Invoice");
        MenuItems.add("2. Get All Invoices");
        MenuItems.add("3. Get Specific Invoice");
        MenuItems.add("4. Update an Invoice");
        MenuItems.add("5. Delete an Invoice");

        //cycle through the options, then ask for a selection.
        while (option != 0) {
            System.out.println("\nPlease make a selection from the below options:");
            for (String menuitem : MenuItems) {
                System.out.println(menuitem);
            }

            try {
                //wait for a response.
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                //Ask for the option to select
                System.out.print("\nOption: ");
                //wait for the readline
                option = Integer.parseInt(in.readLine());
            } catch (IOException e) {
                System.out.println("That option is invalid, Please try again.");
                //reset the option value.
                option = -1;
                continue;
            }

            if ((option < -1) || (option > (MenuItems.size() - 1))) {
                System.out.println("That option is invalid, Please try again.");
                //reset the option value.
                option = -1;
                continue;
            }

            switch (option) {
                case 1:
                    NewInvoice();
                    break;
                case 2:
                    GetAllInvoices();
                    break;
                case 3:
                    GetSpecificInvoice();
                    break;
                case 4:
                    UpdateInvoice();
                    break;
                case 5:
                    DeleteInvoice();
                    break;
                default:
                    System.out.println("That option is not valid!");
            }
        }

    }
}
