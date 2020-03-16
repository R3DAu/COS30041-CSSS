/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jdbc;

import java.util.ArrayList;

/**
 *
 * @author reyno
 */
public class SetUpMyUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyDB mydb = new MyDB();

        /*
       *  drop table first for a clean start
       *  may cause an error if the table does not exist.
         */
        mydb.dropMyuserTable();
        mydb.createMyuserTable();

        ArrayList<Myuser> aList = prepareMyuserData();
        mydb.addRecords(aList);
        
        /*
            Testing the CRUD functionality
        */
        
        boolean testCreate = mydb.createRecord(new Myuser("000006", "Test Person", "123456", "tperson@swin.edu.au", "9876543210", "Swinburne EN599x", "What is my name", "Test"));
        System.out.println(String.format("User Created: %s", testCreate));
        
        Myuser testGet = mydb.getRecord("000006");
        System.out.println(String.format("\nUser Selected:\nName: %s, ID: %s", testGet.getName().trim(), testGet.getUserid().trim()));
        
        boolean testUpdate = mydb.updateRecord(new Myuser("000006", "Testing Person", "123457", "tperson@swin.edu.au", "9876543210", "Swinburne EN599x", "What is my name", "Test"));
        System.out.println(String.format("User Updated: %s", testUpdate));
        
        boolean testDelete = mydb.deleteRecord("000006");
        System.out.println(String.format("User Delete: %s", testDelete));
    }

    public static ArrayList<Myuser> prepareMyuserData() {
        ArrayList<Myuser> myList = new ArrayList<Myuser>();

        Myuser myuser1 = new Myuser("000001", "Peter Smith", "123456", "psmith@swin.edu.au", "9876543210", "Swinburne EN510f", "What is my name", "Peter");
        Myuser myuser2 = new Myuser("000002", "James T. Kirk", "234567", "jkirk@swin.edu.au", "8765432109", "Swinburne EN511a", "What is my name", "James");
        Myuser myuser3 = new Myuser("000003", "Sheldon Cooper", "345678", "scooper@swin.edu.au", "7654321098", "Swinburne EN512a", "What is my last name", "Cooper");
        Myuser myuser4 = new Myuser("000004", "Clark Kent", "456789", "ckent@swin.edu.au", "6543210987", "Swinburne EN513a", "What is my last name", "Kent");
        Myuser myuser5 = new Myuser("000005", "Harry Potter", "567890", "hpotter@swin.edu.au", "6543210987", "Swinburne EN514a", "What is my last name", "Potter");

        myList.add(myuser1);
        myList.add(myuser2);
        myList.add(myuser3);
        myList.add(myuser4);
        myList.add(myuser5);

        return myList;
    }

}
