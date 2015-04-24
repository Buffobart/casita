/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadCuenta;
import Entidad.ClsTransferenciaHib;
import daos.CuentaDao;
import daos.OperacionDao;
import daos.impl.CuentaDaoImpl;
import daos.impl.OperacionDaoImpl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class FrmTransferenciaCuentas extends javax.swing.JInternalFrame {

    private List<ClsEntidadCuenta> originAccounts;
    private List<ClsEntidadCuenta> destAccounts;
    
    private CuentaDao cuentaDao = new CuentaDaoImpl();
    private OperacionDao operacionDao = new OperacionDaoImpl();
    
    /**
     * Creates new form FrmTransferenciaCuentas
     */
    public FrmTransferenciaCuentas() {
        initComponents();
        
    }
    
    public void setOriginAccounts(List<ClsEntidadCuenta> origin){
        this.originAccounts = origin;
        updateCboOrigen();
    }
    
    public void setDestinationAccounts(List<ClsEntidadCuenta> dest){
        this.destAccounts = dest;
        updateCboDestino();
    }
    
    public void setAccounts(List<ClsEntidadCuenta> origin, List<ClsEntidadCuenta> dest){
        
        this.setOriginAccounts(origin);
        this.setDestinationAccounts(dest);
    }
    
    public void updateCboOrigen(){
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
        
        //String etiquetas[] = new String[cuentas.size()];
        for(ClsEntidadCuenta cuenta: this.originAccounts){
            comboModel.addElement(cuenta.getDescripcion());
        }
        
        this.cboOrigen.setModel(comboModel);
    }
    
    public void updateCboDestino(){
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
        
        //String etiquetas[] = new String[cuentas.size()];
        for(ClsEntidadCuenta cuenta: this.destAccounts){
            comboModel.addElement(cuenta.getDescripcion());
        }
        
        this.cboDestino.setModel(comboModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboDestino = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboOrigen = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();

        setTitle("Transferencia");

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuenta destino"));

        jLabel3.setText("Cuenta:");

        cboDestino.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton2.setText("Realizar Transferencia");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuenta origen"));

        jLabel1.setText("Cuenta:");

        cboOrigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Cantidad:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cboOrigen, 0, 142, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtCantidad)))
                .addGap(230, 230, 230))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        try{
            ClsEntidadCuenta cuentaOrigen = this.originAccounts.get( this.cboOrigen.getSelectedIndex() );
            ClsEntidadCuenta cuentaDestino = this.destAccounts.get( this.cboDestino.getSelectedIndex() );
            double cantidad = Double.parseDouble( this.txtCantidad.getText() );
            
            if( cuentaOrigen.getBalance().doubleValue() < cantidad ){
                //There is not enough money, warn the user 
                int response = JOptionPane.showConfirmDialog(this, "La cuenta origen no tiene fondos suficientes, deseas continuar? la cuenta quedara con numeros negativos.", "Fondos insufucientes", JOptionPane.YES_NO_OPTION);
                if( response != JOptionPane.YES_OPTION){
                    return;
                }
                
            }
            
            BigDecimal cantidadBigDecimal = new BigDecimal(cantidad);
            
            cuentaDestino.setBalance(cuentaDestino.getBalance().add(cantidadBigDecimal));
            cuentaOrigen.setBalance(cuentaOrigen.getBalance().subtract(cantidadBigDecimal));
            
            //ArrayList cuentasToUpdate = new ArrayList();
            
            //ClsEntidadOperacionHib operacionOrigen = new ClsEntidadOperacionHib();
            //ClsEntidadOperacionHib operacionDestino = new ClsEntidadOperacionHib();
            
            ClsTransferenciaHib tranfer =  new ClsTransferenciaHib();
            tranfer.setFecha(new Date());
            tranfer.setUsuario(FrmPrincipal.getInstance().getEmpleado());
            tranfer.setCuentaOrigen(cuentaOrigen);
            tranfer.setCuentaDestino(cuentaDestino);
            tranfer.setCantidad(cantidadBigDecimal);
            
            Session session = null;
            try{
            session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            
            session.save(tranfer);

            session.getTransaction().commit();
            }finally{
                if(session != null){
                    session.close();
                }
            }
            
            operacionDao.addOperacion(tranfer);
            
            /*
            operacionOrigen.setTipo("");
            operacionOrigen.setCuenta(cuentaOrigen);
            operacion.setCantidad(BigDecimal.valueOf(total));
            operacion.setUsuario(new ClsEntidadEmpleadoHib(Integer.valueOf(FrmPrincipal.getInstance().strIdEmpleado)));
            operacion.setHora(new Date());
                    */

            //operacionDao.addOperacion(operacion);
            
            //cuentasToUpdate.add(cuentaDestino);
            //cuentasToUpdate.add(cuentaOrigen);
            
            //cuentaDao.saveCuentas( cuentasToUpdate );
            
        }catch( java.lang.NumberFormatException e ){
            
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "La cantidad introducida es invalida o tienen un formato invalido", "Cantidad invalida", JOptionPane.ERROR_MESSAGE);
            
            
        }finally{
            this.dispose();
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboDestino;
    private javax.swing.JComboBox cboOrigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}