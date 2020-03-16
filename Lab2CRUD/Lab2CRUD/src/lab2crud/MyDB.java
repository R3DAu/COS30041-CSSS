/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mitchell Reynolds
 */
public class MyDB {

    /*
    * Methods:
    * - getConnection();
    * - createInvoicesTable();
    * - dropInvoicesTable();
     */

 /*
          Get Connection Method
          This method will return a java.sql.Connection object.
     */
    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost/sun-appserv-samples;create=true";
        String username = "APP";
        String password = "APP";

        return DriverManager.getConnection(url, username, password);
    }

    /*
        Create Invoices Table
        This method will create the basis for creating the Invoice Table
        if it doesn't exist already. 
     */
    public Boolean createInvoicesTable() {
        //set variables to null
        Connection cnnct = null;
        Statement stmnt = null;

        try {
            //create the connection
            cnnct = getConnection();
            //create the statement object
            stmnt = cnnct.createStatement();

            //let's make sure the table doesn't exist already.
            //now execute this and see how many results come from this.
            String sqlStatement = "SELECT count(*) FROM SYS.SYSTABLES WHERE TABLENAME='INVOICES'";

            ResultSet rs = stmnt.executeQuery(sqlStatement);
            while (rs.next()) {
                int count = rs.getInt(1);
                if (count > 1) {
                    //there was a table. We can return false and continue. 
                    return false; //couldn't create because it exists.
                }
            }

            //create the sql string
            sqlStatement = "CREATE TABLE INVOICES ("
                    + " InvoiceID CHAR(10) CONSTRAINT PK_INVOICE PRIMARY KEY, "
                    + " Description CHAR(254), UserId CHAR(6), Amount CHAR(10), "
                    + " Tax CHAR(10))";

            //execute the statment string
            stmnt.execute(sqlStatement);
            //we can now return true.
            return true;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException e) {
                }
            }
        }

        return false;
    }

    /*
        Drop Invoice Table method
        This method drops the Invoices table.
     */
    public Boolean dropInvoicesTable() {
        Connection cnnct = null;
        Statement stmnt = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            //let's do some quick evaluation if the INVOICES table exists first.
            String sqlStatement = "SELECT count(*) FROM SYS.SYSTABLES WHERE TABLENAME='INVOICES'";

            //now execute this and see how many results come from this.
            ResultSet rs = stmnt.executeQuery(sqlStatement);
            while (rs.next()) {
                int count = rs.getInt(1);
                if (count < 1) {
                    //there was no table. We can return false and continue. 
                    return false; //couldn't delete because it doesn't exist.
                }
            }

            //otherwise, if we got to this point the table must exist. 
            stmnt.execute("DROP TABLE INVOICES");
            return true;//we were able to drop the table.
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException e) {
                }
            }
        }

        //make sure all code paths return a value, if we got here something went wrong.
        return false;
    }

    /*
            CRUD Functions
     */
    
    
    /*
        Get Record Function
        This method will grab an invoice object and return it. 
    */
    public Myinvoice getRecord(String myInvoiceId) {
        //set the basic variables to null for the method. 
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        
        //set a counter here.
        int count = 0;
        
        try{
            //do the connection.
            cnnct = getConnection();
          
            //let's first check if an SQL entry exists in this context.
            String PQS = "SELECT COUNT(*) FROM INVOICES WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(PQS);
            pStmnt.setString(1, myInvoiceId);
            
            //execute the query -> like in PDO.
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                //now that we have the count. If it is under 1 let's return null.
               count = rs.getInt(1);
               if (count < 1) {
                   //nothing to return, don't burn processing power for what 
                   //we can return here.
                   return null;
               }
            }
            
            //now that we got this far we can then return everything we need.
            PQS = "SELECT * FROM INVOICES WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(PQS);
            pStmnt.setString(1, myInvoiceId);
            
            //get the results and now return the result.
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                String InvoiceId = rs.getString(1);
                String Description = rs.getString(2);
                String UserId = rs.getString(3);
                String Amount = rs.getString(4);
                String Tax = rs.getString(5);
                
                //now return the new object.
                return new Myinvoice(InvoiceId, Description, UserId, Amount, Tax);
            }

        }catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException exx) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        
        //just making sure all code paths return something. 
        return null;
    }
    
    /*
        create record.
    */
    public Boolean createRecord(Myinvoice myinvoice){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT count(*) FROM INVOICES WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myinvoice.getInvoiceID());

            //execute the query -> like in PDO.
            ResultSet rs = pStmnt.executeQuery();
            //now we can just cycle the entries in here.
            while (rs.next()) {
                int count = rs.getInt(1);
                //now if the count value we get is over 1 then we can add it
                if (count > 0) {
                    //an invoice with the same ID was detected. 
                    return false;
                }
            }

            //otherwise we now have a simple run recreating the existing code.
            preQueryStatement = "INSERT INTO INVOICES VALUES (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, myinvoice.getInvoiceID());
            pStmnt.setString(2, myinvoice.getDescription());
            pStmnt.setString(3, myinvoice.getUserId());
            pStmnt.setString(4, myinvoice.getAmount());
            pStmnt.setString(5, myinvoice.getTax());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("Cannot insert records!");
            }

            return true;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }

        //just making sure all code paths return something. 
        return false;
    }
    
     public boolean updateRecord(Myinvoice myinvoice) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT count(*) FROM INVOICES WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myinvoice.getInvoiceID());

            //execute the query -> like in PDO.
            ResultSet rs = pStmnt.executeQuery();
            //now we can just cycle the entries in here.
            while (rs.next()) {
                int count = rs.getInt(1);
                //now if the count value we get is less than 1, we cannot update it.
                if (count < 1) {
                    //a user with the same ID wasn't detected. 
                    System.out.println("No Invoice Detected with: " + myinvoice.getInvoiceID());
                    return false;
                }
            }

            //otherwise we now have a simple run recreating the existing code.
            preQueryStatement = "UPDATE INVOICES SET DESCRIPTION = ?, USERID = ?, AMOUNT = ?, TAX = ? WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(5, myinvoice.getInvoiceID());
            pStmnt.setString(1, myinvoice.getDescription());
            pStmnt.setString(2, myinvoice.getUserId());
            pStmnt.setString(3, myinvoice.getAmount());
            pStmnt.setString(4, myinvoice.getTax());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("Cannot insert records!");
            }

            return true;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }

        //just making sure all code paths return something. 
        return false;
    }
     
     public boolean deleteRecord(String myinvoiceid) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT count(*) FROM INVOICES WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myinvoiceid);

            //execute the query -> like in PDO.
            ResultSet rs = pStmnt.executeQuery();
            //now we can just cycle the entries in here.
            while (rs.next()) {
                int count = rs.getInt(1);
                //now if the count value we get is less than 1, we cannot update it.
                if (count < 1) {
                    //a user with the same ID wasn't detected. 
                    return false;
                }
            }

            //otherwise we now have a simple run recreating the existing code.
            preQueryStatement = "DELETE FROM INVOICES WHERE INVOICEID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myinvoiceid);

            int rowCount = pStmnt.executeUpdate();
            if (rowCount == 0) {
                throw new SQLException("Cannot delete records!");
            }

            return true;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }

        //just making sure all code paths return something. 
        return false;
    }
     
    public ArrayList<Myinvoice> getAllRecords()
    {
         //set the basic variables to null for the method. 
        Connection cnnct = null;
        Statement Stmnt = null;
        
        //set a counter here.
        int count = 0;
        
        try{
            //do the connection.
            cnnct = getConnection();
            //now that we got this far we can then return everything we need.
            Stmnt = cnnct.createStatement();
            //create a new result set
            ResultSet rs = Stmnt.executeQuery("SELECT * FROM INVOICES");
            //were gonna need something to return...
            ArrayList<Myinvoice> myinvoices = new ArrayList();
            
            //get the results and now return the result.
            while (rs.next()) {
                String InvoiceId = rs.getString(1);
                String Description = rs.getString(2);
                String UserId = rs.getString(3);
                String Amount = rs.getString(4);
                String Tax = rs.getString(5);
                
                //now return the new object.
                 myinvoices.add(new Myinvoice(InvoiceId, Description, UserId, Amount, Tax));
            }
        return myinvoices;
        
        }catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (Stmnt != null) {
                try {
                    Stmnt.close();
                } catch (SQLException exx) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        
        //just making sure all code paths return something. 
        return null;
    }
}
