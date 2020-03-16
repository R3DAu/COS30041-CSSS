/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.jdbc;

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
 * @author reyno
 */
public class MyDB {

    public static Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost/sun-appserv-samples;create=true";
        String username = "APP";
        String password = "APP";

        return DriverManager.getConnection(url, username, password);
    }

    public void createMyuserTable() {
        Connection cnnct = null;
        Statement stmnt = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("CREATE TABLE MYUSER( "
                    + " UserId CHAR(6) CONSTRAINT PK_CUSTOMER PRIMARY KEY, "
                    + " Name CHAR(30), Password CHAR(6), Email CHAR(30), "
                    + " Phone CHAR(10), Address CHAR(60), "
                    + " secQn CHAR(60), SecAns CHAR(60))");
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
    }

    public void dropMyuserTable() {
        Connection cnnct = null;
        Statement stmnt = null;

        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            stmnt.execute("DROP TABLE MYUSER");
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
    }

    public Myuser getRecord(String myuser) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            int count = 0;

            String preQueryStatement = "SELECT count(*) FROM MYUSER WHERE USERID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myuser);

            //execute the query -> like in PDO.
            ResultSet rs = pStmnt.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
                if (count < 1) {
                    return null;
                }
            }

            preQueryStatement = "SELECT * FROM MYUSER WHERE USERID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myuser);

            //execute the query -> like in PDO.
            rs = pStmnt.executeQuery();
            //now we can just cycle the entries in here.
            while (rs.next()) {
                //now if the count value we get is over 1 then we can add it
                if (count < 1) {
                    //a user with the same ID was detected. 
                    return null;
                } else {
                    String userid = rs.getString(1);
                    String username = rs.getString(2);
                    String password = rs.getString(3);
                    String email = rs.getString(4);
                    String phone = rs.getString(5);
                    String address = rs.getString(6);
                    String secQn = rs.getString(7);
                    String secAns = rs.getString(8);

                    return new Myuser(userid, username, password, email, phone, address, secQn, secAns);
                }
            }

            return null;
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
        return null;
    }

    public boolean updateRecord(Myuser myuser) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT count(*) FROM MYUSER WHERE userid = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myuser.getUserid());

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
            preQueryStatement = "UPDATE MYUSER SET NAME = ?, PASSWORD = ?, EMAIL = ?,PHONE = ?, ADDRESS = ?, SECQN = ?, SECANS = ? WHERE USERID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, myuser.getName());
            pStmnt.setString(2, myuser.getPassword());
            pStmnt.setString(3, myuser.getEmail());
            pStmnt.setString(4, myuser.getPhone());
            pStmnt.setString(5, myuser.getAddress());
            pStmnt.setString(6, myuser.getSecQn());
            pStmnt.setString(7, myuser.getSecAns());
            pStmnt.setString(8, myuser.getUserid());

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

    public boolean deleteRecord(String myuser) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT count(*) FROM MYUSER WHERE userid = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myuser);

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
            preQueryStatement = "DELETE FROM MYUSER WHERE USERID = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myuser);

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

    public boolean createRecord(Myuser myuser) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        //let's check to see if there is a record first.
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT count(*) FROM MYUSER WHERE userid = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, myuser.getUserid());

            //execute the query -> like in PDO.
            ResultSet rs = pStmnt.executeQuery();
            //now we can just cycle the entries in here.
            while (rs.next()) {
                int count = rs.getInt(1);
                //now if the count value we get is over 1 then we can add it
                if (count > 0) {
                    System.out.println("\n\n User Detected");
                    //a user with the same ID was detected. 
                    return false;
                }
            }

            //otherwise we now have a simple run recreating the existing code.
            preQueryStatement = "INSERT INTO MYUSER VALUES (?,?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            pStmnt.setString(1, myuser.getUserid());
            pStmnt.setString(2, myuser.getName());
            pStmnt.setString(3, myuser.getPassword());
            pStmnt.setString(4, myuser.getEmail());
            pStmnt.setString(5, myuser.getPhone());
            pStmnt.setString(6, myuser.getAddress());
            pStmnt.setString(7, myuser.getSecQn());
            pStmnt.setString(8, myuser.getSecAns());

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

    public void addRecords(ArrayList<Myuser> myUsers) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO MYUSER VALUES (?,?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            //loop through the users array list
            for (Myuser myuser : myUsers) {
                pStmnt.setString(1, myuser.getUserid());
                pStmnt.setString(2, myuser.getName());
                pStmnt.setString(3, myuser.getPassword());
                pStmnt.setString(4, myuser.getEmail());
                pStmnt.setString(5, myuser.getPhone());
                pStmnt.setString(6, myuser.getAddress());
                pStmnt.setString(7, myuser.getSecQn());
                pStmnt.setString(8, myuser.getSecAns());

                int rowCount = pStmnt.executeUpdate();
                if (rowCount == 0) {
                    throw new SQLException("Cannot insert records!");
                }
            }
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
    }
}
