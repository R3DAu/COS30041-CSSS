/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jpa;

/**
 *
 * @author reyno
 */
public class MyuserApp {

    private MyuserDB mydb;

    public MyuserApp() {
        mydb = new MyuserDB();
    }

    public static void main(String[] args) {
        MyuserApp client = new MyuserApp();
        MyuserDTO myuserDTO = new MyuserDTO("000099", "Smith of Peter", "123456", "speter@swin.edu.au", "9876543210", "Swinburne EN510f", "What is my name", "Smith");

        boolean result = client.createRecord(myuserDTO);
        client.showCreateResult(result, myuserDTO);

        MyuserDTO myuserDTO2 = new MyuserDTO("000090", "Smith of David", "123456", "sdavid@swin.edu.au", "9876543210", "Swinburne EN511f", "What is my name", "Smith");

        result = client.createRecord(myuserDTO2);
        client.showCreateResult(result, myuserDTO2);
        
        myuserDTO = client.getRecord("000090");
        System.out.println("User's name is: " + myuserDTO.getName());
        
        MyuserDTO myuserDTO3 = new MyuserDTO("000090", "Smith of Paul", "123456", "sdavid@swin.edu.au", "9876543210", "Swinburne EN511f", "What is my name", "Smith");
        
        result = client.updateRecord(myuserDTO3);
        client.showUpdateResult(result, myuserDTO3);
        
        result = client.deleteRecord("000090");
        if (result) {
            System.out.println("Deleted user");
        } else {
            System.out.println("Couldn't delete user");
        }
    }

    public void showCreateResult(boolean result, MyuserDTO myuserDTO) {
        if (result) {
            System.out.println("Record with primary key " + myuserDTO.getUserid() + " has been created in the table.");
        } else {
            System.out.println("Record with primary key " + myuserDTO.getUserid() + " could not be created in the table.");
        }
    }
    
    public void showUpdateResult(boolean result, MyuserDTO myuserDTO) {
        if (result) {
            System.out.println("Record with primary key " + myuserDTO.getUserid() + " has been updated in the table.");
        } else {
            System.out.println("Record with primary key " + myuserDTO.getUserid() + " could not be updated in the table.");
        }
    }
    
     public Boolean deleteRecord(String userid){
        return mydb.deleteRecord(userid);
    }
    
    public Boolean updateRecord(MyuserDTO myDTO){
        return mydb.updateRecord(myDTO);
    }
    
    public MyuserDTO getRecord(String userid){
        return mydb.getRecord(userid);
    }

    public boolean createRecord(MyuserDTO myuserDTO) {
        return mydb.createRecord(myuserDTO);
    }

}
