/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.packages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class DatabaseConnection {
    Connection Conn =null;
    String url="jdbc:postgresql://localhost:5432/DODB";
    String user="postgres";
    String password="1234";
    public Connection databaseConn(){
        
        try {
        Class.forName("org.postgresql.Driver");
        } 
        catch (ClassNotFoundException ex) {
            System.out.println("Driver Errorr!!!!!1");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Conn = DriverManager.getConnection(url,user,password);
            JOptionPane.showMessageDialog(null,"Connected to Database");
        }
        catch (SQLException ex) {
            System.out.println("Did not Connected to Database");
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Conn;
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        DatabaseConnection connDatabase = new DatabaseConnection();
        connDatabase.databaseConn();
    }
}
