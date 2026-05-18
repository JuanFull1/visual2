/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author Lenovo
 */
public class Conexion {
    
     public Connection conectar(){
        Connection conectar = null ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conectar = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cuarto",
                "root",
                ""
            );
            System.out.println((conectar == null)? "No valio" : "si valio");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return conectar;
    }
}
