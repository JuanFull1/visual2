

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class DAO {
    
        
 
        public void insertEstudentDAO(Estudiantes estudiante) {
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "INSERT INTO estudiantes VALUES(?,?,?,?,?)";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setString(1, estudiante.getEstCedula());
            psd.setString(2, estudiante.getEstNombre());
            psd.setString(3, estudiante.getEstApellido());
            psd.setString(4, estudiante.getEstDireccion());
            psd.setString(5, estudiante.getEstTelefono());

            int respuesta = psd.executeUpdate();
            if (respuesta > 0) {
                JOptionPane.showMessageDialog(null, "Se inserto un Estudiante");
                //selectEstudent();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public List<Estudiantes> selectStudentDao() {

    List<Estudiantes> lista = new ArrayList<>();

    try {
        Conexion cn = new Conexion();
        Connection cc = cn.conectar();
        String sql = "SELECT * FROM estudiantes";
        Statement psd = cc.createStatement();
        ResultSet rs = psd.executeQuery(sql);

        while (rs.next()) {
            Estudiantes est = new Estudiantes(
                    rs.getString("estCedula"),
                    rs.getString("estNombre"),
                    rs.getString("estApellido"),
                    rs.getString("estDireccion"),
                    rs.getString("estTelefono")
            );

            lista.add(est);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "No se pudo consultar los estudiantes");
    }

    return lista;
}
    
    public void deleteEstudentDAO(String cedula) {
    try {
        Conexion cn = new Conexion();
        Connection cc = cn.conectar();

        String sql = "DELETE FROM estudiantes WHERE estCedula = ?";
        PreparedStatement psd = cc.prepareStatement(sql);
        psd.setString(1, cedula);

        int respuesta = psd.executeUpdate();

        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Se borró el estudiante");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el estudiante");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
}
    
    public void updateEstudentDAO(Estudiantes estudiante) {
    try {
        Conexion cn = new Conexion();
        Connection cc = cn.conectar();

        String sql = "UPDATE estudiantes SET estNombre=?, estApellido=?, estDireccion=?, estTelefono=? WHERE estCedula=?";
        PreparedStatement psd = cc.prepareStatement(sql);

        psd.setString(1, estudiante.getEstNombre());
        psd.setString(2, estudiante.getEstApellido());
        psd.setString(3, estudiante.getEstDireccion());
        psd.setString(4, estudiante.getEstTelefono());
        psd.setString(5, estudiante.getEstCedula());

        int respuesta = psd.executeUpdate();

        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Se actualizó el estudiante");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el estudiante");
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
}
}
