/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Interfaz extends javax.swing.JFrame {

    DefaultTableModel model ;
    
    public Interfaz() {
        initComponents();
       // selectEstudent();
       consumirSelectEstudentDao();
        SelectRowEstudent();
        
    }

    public void selectEstudent() {
        try {
            String titulos[] = {"CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
            String registros[]= new String[5];
            model = new DefaultTableModel(null , titulos);
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "select * from estudiantes";
            Statement psd = cc.createStatement();
            ResultSet rs=psd.executeQuery(sql);
            
             while (rs.next()) {
                registros[0]=rs.getString("estCedula");
                registros[1]=rs.getString("estNombre");
                registros[2]=rs.getString("estApellido");
                registros[3]=rs.getString("estDireccion");
                registros[4]=rs.getString("estTelefono");
                model.addRow(registros);
            }  

            jtblEstudiante.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se inserto comuniquese con el administrador ");
        }
   
    }
     
    public void inserEstudent(){
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "insert into estudiantes values(?,?,?,?,?)";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setString(1,jtxtCedula.getText());
            psd.setString(2,jtxtNombre.getText());
            psd.setString(3,jtxtApellido.getText());
            psd.setString(4,jtxtDireccion.getText());
            psd.setString(5, jtxtTelefono.getText());
            
            int res=psd.executeUpdate();
            if (res>0) {
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                selectEstudent();
            }      
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    public void deleteEstudent() {
        if (JOptionPane.showConfirmDialog(this, "Estas seguro de borrar", 
                "Borrar estudiante",
                JOptionPane.YES_NO_OPTION )==JOptionPane.YES_OPTION)
            
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "delete from estudiantes where estcedula='" + jtxtCedula.getText() + "'";
            PreparedStatement psd = cc.prepareStatement(sql);
            
            int res=psd.executeUpdate();
            if (res>0) {
                JOptionPane.showMessageDialog(null, "Se borro correctamente");
                selectEstudent();
            }  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    public void updateEstudent() {
    try {
        Conexion cn = new Conexion();
        Connection cc = cn.conectar();
       
        String sql = "UPDATE estudiantes SET "
                    + "estNombre = '" + jtxtNombre.getText() + "', "
                    + "estApellido = '" + jtxtApellido.getText() + "', "
                    + "estDireccion = '" + jtxtDireccion.getText() + "', "
                    + "estTelefono = '" + jtxtTelefono.getText() + "' "  
                    + "WHERE estCedula = '" + jtxtCedula.getText() + "'"; 
        
        PreparedStatement psd = cc.prepareStatement(sql);
        int res = psd.executeUpdate();
        
        if (res > 0) {
            JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
            selectEstudent();  
        }  
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, ex);
    }
}
    
    public void SelectRowEstudent ()
    {
        jtblEstudiante.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(jtblEstudiante.getSelectedRow()!=-1){
                    int fila = jtblEstudiante.getSelectedRow();
                jtxtCedula.setText(jtblEstudiante.getValueAt(fila, 0).toString());
                jtxtNombre.setText(jtblEstudiante.getValueAt(fila, 1).toString());
                jtxtApellido.setText(jtblEstudiante.getValueAt(fila,2).toString());
                jtxtDireccion.setText(jtblEstudiante.getValueAt(fila,3).toString());
                jtxtTelefono.setText(jtblEstudiante.getValueAt(fila,4).toString());
                }
            }
        });
    }
    

    
   public void consumidorDaoInsertar() {

    Estudiantes estudiante = new Estudiantes(
            jtxtCedula.getText(),
            jtxtNombre.getText(),
            jtxtApellido.getText(),
            jtxtDireccion.getText(),
            jtxtTelefono.getText()
    );

    DAO dao = new DAO();
    dao.insertEstudentDAO(estudiante);
    consumirSelectEstudentDao();
}
    
   
    public void consumirSelectEstudentDao() {
        try {
            DAO dao = new DAO ();
            List <Estudiantes> lista=dao.selectStudentDao();
            String titulos[] = {"CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
            model = new DefaultTableModel(null , titulos);
            for (Estudiantes est:lista){
                Object[] registro = {est.getEstCedula(),
                                    est.getEstNombre(),
                                    est.getEstApellido(),
                                    est.getEstDireccion(),
                                    est.getEstTelefono()

                };
                model.addRow(registro);
            }
            jtblEstudiante.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se inserto comuniquese con el administrador ");
        }
     
    }
    
    
    
    public void consumidorDaoDelete() {
        if (JOptionPane.showConfirmDialog(
                this,
                "Estas seguro de borrar este estudiante",
                "Borrar estudiante",
                JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION) {

            DAO dao = new DAO();
            dao.deleteEstudentDAO(jtxtCedula.getText());
            consumirSelectEstudentDao();
        }
    }
    
    public void consumidorDaoUpdate() {

    Estudiantes estudiante = new Estudiantes(
            jtxtCedula.getText(),
            jtxtNombre.getText(),
            jtxtApellido.getText(),
            jtxtDireccion.getText(),
            jtxtTelefono.getText()
    );

    DAO dao = new DAO();
    dao.updateEstudentDAO(estudiante);
    consumirSelectEstudentDao();
}
    


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jtxtCedula = new javax.swing.JTextField();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jtxtDireccion = new javax.swing.JTextField();
        jtxtTelefono = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnBorrar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiante = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtxtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCedulaActionPerformed(evt);
            }
        });

        jLabel1.setText("Ingrese Cedula");

        jLabel2.setText("Ingrese Nombre");

        jLabel3.setText("Ingrese Direccion");

        jLabel4.setText("Ingrese Telefono");

        jLabel5.setText("Ingrese Apellido");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtxtNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtxtTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jbtnEditar.setText("Editar");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jbtnBorrar.setText("Borrar");
        jbtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBorrarActionPerformed(evt);
            }
        });

        jbtnCancelar.setText("Cancelar");

        jButton1.setText("REPORTES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnCancelar)
                            .addComponent(jbtnBorrar)
                            .addComponent(jbtnGuardar)
                            .addComponent(jbtnNuevo)
                            .addComponent(jbtnEditar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addComponent(jbtnNuevo)
                .addGap(18, 18, 18)
                .addComponent(jbtnGuardar)
                .addGap(29, 29, 29)
                .addComponent(jbtnEditar)
                .addGap(30, 30, 30)
                .addComponent(jbtnBorrar)
                .addGap(18, 18, 18)
                .addComponent(jbtnCancelar)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jtblEstudiante.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblEstudiante);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCedulaActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
      //inserEstudent();
      consumidorDaoInsertar();
       
      
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBorrarActionPerformed
        //deleteEstudent();
        consumidorDaoDelete();
        
    }//GEN-LAST:event_jbtnBorrarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
       // updateEstudent();
       consumidorDaoUpdate();
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            Map parametros = new HashMap();
            parametros.put("cedula", jtxtCedula.getText());
            JasperReport reporteEstudiantes = JasperCompileManager.compileReport("C:\\Users\\User\\Documents\\NetBeansProjects\\CuartoA\\src\\Reportes\\ReporteEstudiantes.jrxml");
            JasperPrint imprimir = JasperFillManager.fillReport(reporteEstudiantes, parametros, cc);
            JasperViewer.viewReport(imprimir,false);
            JasperExportManager.exportReportToPdfFile(imprimir,"C:\\Users\\User\\Documents\\3ER SEMESTRE\\COMPUTACION VISUAL\\SEGUNDO PARCIAL\\reporte.pdf");
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnBorrar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JTable jtblEstudiante;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
