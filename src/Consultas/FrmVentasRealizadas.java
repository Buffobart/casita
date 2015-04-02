/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import Conexion.ClsConexion;
import Entidad.*;
import Negocio.*;
import Presentacion.FrmPrincipal;
import Presentacion.FrmVenta;
import daos.VentaDao;
import daos.impl.VentaDaoImpl;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class FrmVentasRealizadas extends javax.swing.JInternalFrame {
    private Connection connection=new ClsConexion().getConection();
    static ResultSet rs=null;
    DefaultTableModel dtm=new DefaultTableModel();
    DefaultTableModel dtm1=new DefaultTableModel();
    String id[]=new String[50];
    static int intContador;
    Date fecha_ini,fecha_fin;
    String documento,criterio,busqueda,Total;
    boolean valor=true;
    int n=0;
    
    VentaDao ventasDao = new VentaDaoImpl();
    List<ClsEntidadVentaHib> ventas;
    
    public FrmVentasRealizadas() {
        initComponents();
        lblIdVenta.setVisible(false);

        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(769, 338);

        //---------------------FECHA ACTUAL-------------------------------
        //Date date=new Date();
        //String format=new String("dd/MM/yyyy");
        //SimpleDateFormat formato=new SimpleDateFormat(format);
        
        Calendar cal = Calendar.getInstance(); 
        this.dcFechafin.setDate(cal.getTime());
        cal.add(Calendar.MONTH, -1);
        this.dcFechaini.setDate(cal.getTime());
        
        BuscarVenta();
        CrearTabla(); 
        CantidadTotal();
    }

//-----------------------------------------------------------------------------------------------
//--------------------------------------METODOS--------------------------------------------------
//-----------------------------------------------------------------------------------------------

    void CrearTabla(){
   //--------------------PRESENTACION DE JTABLE----------------------
      /*
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==2 || column==4 || column==5 || column==6 || column==7 || column==8){
                        l.setHorizontalAlignment(SwingConstants.CENTER); 
                    }else{
                        l.setHorizontalAlignment(SwingConstants.LEFT);
                    }

                //Colores en Jtable        
                if (isSelected) {
                    l.setBackground(new Color(51, 152, 255));
                    //l.setBackground(new Color(168, 198, 238));
                    l.setForeground(Color.WHITE); 
                }else{
                    l.setForeground(Color.BLACK);
                    if (row % 2 == 0) {
                        l.setBackground(Color.WHITE);
                    } else {
                        //l.setBackground(new Color(232, 232, 232));
                        l.setBackground(new Color(229, 246, 245));
                    }
                }
                return l; 
            } 
        }; 
        
        //Agregar Render
        for (int i=0;i<tblVenta.getColumnCount();i++){
            tblVenta.getColumnModel().getColumn(i).setCellRenderer(render);
        }*/
      
        //Activar ScrollBar
        //tblVenta.setAutoResizeMode(tblVenta.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50};
        for(int i = 0; i < anchos.length; i++) {
            tblVenta.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

    }
  void BuscarVenta(){
        String titulos[]={"ID","Cliente","Fecha","Empleado","Documento","Serie","Número","Estado","Valor Venta","Descuento","Total"};
        DefaultTableModel tableModel = new DefaultTableModel(null, titulos);
        
        if(this.radCotizaciones.isSelected()){
            if( !this.txtIDVenta.getText().equals("") ){
                this.ventas = new ArrayList<ClsEntidadVentaHib>();
                this.ventas.add( ventasDao.getCotizacionById(new Integer( this.txtIDVenta.getText() )) );
            }else{
                this.ventas = ventasDao.searchCotizaciones(this.dcFechaini.getDate(), this.dcFechafin.getDate());
            }
        }else{
            if( !this.txtIDVenta.getText().equals("") ){
                this.ventas = new ArrayList<ClsEntidadVentaHib>();
                this.ventas.add( ventasDao.getVentaById(new Integer( this.txtIDVenta.getText() )) );
            }else{
                this.ventas = ventasDao.searchVentas(this.dcFechaini.getDate(), this.dcFechafin.getDate());
            }
        }
        
        
        for( ClsEntidadVentaHib venta : this.ventas){
            Vector data = new Vector();
            
            data.add(venta.getIdVenta());
            data.add(venta.getIdCliente().getNombre());
            data.add(venta.getFecha());
            data.add(venta.getIdEmpleado().getNombre());
            data.add(venta.getIdTipoDocumento().getDescripcion());
            data.add(venta.getSerie());
            data.add(venta.getNumero());
            data.add(venta.getEstado());
            data.add(venta.getTotalVenta());
            data.add(venta.getDescuento());
            data.add(venta.getTotalPagar());
            
            tableModel.addRow(data);
            
        }
        
        this.tblVenta.setModel(tableModel);
    }
      
    void CrearTablaDetalle(){
   //--------------------PRESENTACION DE JTABLE DETALLE VENTA----------------------
      
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==1 || column==2 || column==5 || column==6){
                        l.setHorizontalAlignment(SwingConstants.CENTER); 
                    }else{
                        l.setHorizontalAlignment(SwingConstants.LEFT);
                    }

                //Colores en Jtable        
                if (isSelected) {
                    l.setBackground(new Color(51, 152, 255));
                    //l.setBackground(new Color(168, 198, 238));
                    l.setForeground(Color.WHITE); 
                }else{
                    l.setForeground(Color.BLACK);
                    if (row % 2 == 0) {
                        l.setBackground(Color.WHITE);
                    } else {
                        //l.setBackground(new Color(232, 232, 232));
                        l.setBackground(new Color(229, 246, 245));
                    }
                }
                return l; 
            } 
        }; 
        
        //Agregar Render
        for (int i=0;i<tblDetalleVenta.getColumnCount();i++){
            tblDetalleVenta.getColumnModel().getColumn(i).setCellRenderer(render);
        }
      
        //Activar ScrollBar
        //tblDetalleVenta.setAutoResizeMode(tblVenta.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {30};
        for(int i = 0; i < anchos.length; i++) {
            tblDetalleVenta.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Ocultar Columnas
        ocultarColumnas(tblDetalleVenta,new int[]{1});
    }
  void BuscarVentaDetalle(){
        String titulos[]={"ID","ID Prod.","Cód Producto","Nombre","Descripción","Cantidad","Precio","Total"};
        dtm1.setColumnIdentifiers(titulos);
        ClsDetalleVenta detalleventa=new ClsDetalleVenta ();
        busqueda=lblIdVenta.getText();

        try{
            rs=detalleventa.listarDetalleVentaPorParametro("id",busqueda);
            boolean encuentra=false;
            String Datos[]=new String[8];
            int f,i;
            f=dtm1.getRowCount();
            if(f>0){
                for(i=0;i<f;i++){
                    dtm1.removeRow(0);
                }
            }
            while(rs.next()){
                Datos[0]=(String) rs.getString(1);
                Datos[1]=(String) rs.getString(2);
                Datos[2]=(String) rs.getString(3);
                Datos[3]=(String) rs.getString(4);
                Datos[4]=(String) rs.getString(5);
                Datos[5]=(String) rs.getString(6);
                Datos[6]=(String) rs.getString(7);
                Datos[7]=(String) rs.getString(8);
                dtm1.addRow(Datos);
                encuentra=true;

            }
            if(encuentra=false){
                JOptionPane.showMessageDialog(null, "¡No se encuentra!");
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        tblDetalleVenta.setModel(dtm1);
    }
    void CantidadTotal(){
        Total= String.valueOf(tblVenta.getRowCount());   
        lblEstado.setText("Se cargaron " + Total + " registros");      
    }
    void restablecerCantidades(){
        String strId;
        String idventa=lblIdVenta.getText();
        ClsProducto productos=new ClsProducto();
        ClsEntidadProducto producto=new ClsEntidadProducto();
        int fila=0;
        double cant = 0,ncant,stock;   
        fila =tblDetalleVenta.getRowCount();
        for (int f=0; f<fila; f++){          
            try{
                ClsProducto oProducto=new ClsProducto();
                
                rs= oProducto.listarProductoActivoPorParametro("id",((String) tblDetalleVenta.getValueAt(f, 1)));
                while (rs.next()) {
                            cant=Double.parseDouble(rs.getString(5));
                }               

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
                System.out.println(ex.getMessage());
            }        

            
            
        strId = ((String) tblDetalleVenta.getValueAt(f, 1));
        ncant=Double.parseDouble(String.valueOf(tblDetalleVenta.getModel().getValueAt(f, 5)));
        stock=cant+ncant;
        producto.setStrStockProducto(String.valueOf(stock));
        productos.actualizarProductoStock(strId, producto);

    }
    }
    private void ocultarColumnas(JTable tbl, int columna[]){
        for(int i=0;i<columna.length;i++)
        {
             tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
             tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgVentaCotizacion = new javax.swing.ButtonGroup();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblVenta = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        dcFechaini = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        dcFechafin = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblIdVenta = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtIDVenta = new javax.swing.JTextField();
        radVenta = new javax.swing.JRadioButton();
        radCotizaciones = new javax.swing.JRadioButton();
        btnVerDetalle = new javax.swing.JButton();
        lblEstado = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDetalleVenta = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        btnGenerarVenta = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Ventas y Cotizaciones");
        setToolTipText("");
        getContentPane().setLayout(null);

        tblVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        tblVenta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblVenta.setRowHeight(22);
        tblVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVentaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblVenta);

        getContentPane().add(jScrollPane5);
        jScrollPane5.setBounds(10, 110, 730, 140);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar"));
        jPanel1.setLayout(null);

        dcFechaini.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(dcFechaini);
        dcFechaini.setBounds(120, 30, 100, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("Fecha inicial:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(120, 17, 80, 13);

        dcFechafin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel1.add(dcFechafin);
        dcFechafin.setBounds(230, 30, 100, 20);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("Fecha final:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(230, 17, 80, 13);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Buscar_32.png"))); // NOI18N
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(340, 20, 110, 50);
        jPanel1.add(lblIdVenta);
        lblIdVenta.setBounds(320, 20, 40, 20);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer.png"))); // NOI18N
        jButton2.setText("Imprimir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(460, 20, 110, 50);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel2.setText("ID Venta / Cotizacion:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 17, 110, 10);
        jPanel1.add(txtIDVenta);
        txtIDVenta.setBounds(10, 30, 100, 20);

        rbgVentaCotizacion.add(radVenta);
        radVenta.setSelected(true);
        radVenta.setText("Ventas");
        radVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radVentaActionPerformed(evt);
            }
        });
        jPanel1.add(radVenta);
        radVenta.setBounds(10, 60, 59, 23);

        rbgVentaCotizacion.add(radCotizaciones);
        radCotizaciones.setText("Cotizaciones");
        radCotizaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radCotizacionesActionPerformed(evt);
            }
        });
        jPanel1.add(radCotizaciones);
        radCotizaciones.setBounds(80, 60, 85, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 730, 90);

        btnVerDetalle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/busqueda_detallada.png"))); // NOI18N
        btnVerDetalle.setText("Ver Detalle");
        btnVerDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetalleActionPerformed(evt);
            }
        });
        getContentPane().add(btnVerDetalle);
        btnVerDetalle.setBounds(600, 260, 140, 40);
        getContentPane().add(lblEstado);
        lblEstado.setBounds(10, 250, 230, 20);

        tblDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalleVenta.setRowHeight(22);
        tblDetalleVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleVentaMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblDetalleVenta);

        getContentPane().add(jScrollPane6);
        jScrollPane6.setBounds(10, 310, 730, 140);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Buscadetalle_p.png"))); // NOI18N
        jButton3.setText("Generar Orden de Compra");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(390, 260, 190, 40);

        btnGenerarVenta.setText("Generar Venta");
        btnGenerarVenta.setEnabled(false);
        btnGenerarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(btnGenerarVenta);
        btnGenerarVenta.setBounds(260, 260, 110, 40);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarTablaDetalle(ClsEntidadVentaHib venta){
        String titulos[]={"ID","ID Prod.","Cód Producto","Nombre","Descripción","Cantidad","Precio","Total"};
        
        DefaultTableModel model = new DefaultTableModel(null, titulos);
        
        for(ClsEntidadDetalleventaHib detalle : venta.getClsEntidadDetalleventaHibCollection() ){
            Vector data = new Vector();
            
            data.add(detalle.getIdDetalleVenta());
            data.add(detalle.getIdProducto().getIdProducto());
            data.add(detalle.getCantidad());
            data.add(detalle.getIdProducto().getNombre());
            data.add(detalle.getIdProducto().getDescripcion());
            data.add(detalle.getCantidad());
            data.add(detalle.getPrecio());
            data.add(detalle.getTotal());
            
            model.addRow(data);
            
        }
        
        this.tblDetalleVenta.setModel(model);
        
    }
    
    private void tblVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVentaMouseClicked
        int fila = tblVenta.getSelectedRow();

        if (fila >= 0){
            ClsEntidadVentaHib venta = this.ventas.get(fila);
            actualizarTablaDetalle(venta);
            
            if(venta.getEstado().equals(ClsEntidadVentaHib.PRO_TIPO_COTIZACION)){
                this.btnGenerarVenta.setEnabled(true);
            }else{
                this.btnGenerarVenta.setEnabled(false);
            }
        }
    }//GEN-LAST:event_tblVentaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BuscarVenta();
        CrearTabla();
        CantidadTotal();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnVerDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetalleActionPerformed
        if(tblVenta.getSelectedRows().length > 0 ) {

            if(n==0){
                this.setSize(769, 507);
                n=1;
                btnVerDetalle.setText("Ocultar Detalle");
            }else if(n==1){
                this.setSize(769, 338);
                n=0;
                btnVerDetalle.setText("Ver Detalle");
            }
            //BuscarVentaDetalle();
            //CrearTablaDetalle();
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro de venta!");
        }

    }//GEN-LAST:event_btnVerDetalleActionPerformed

    private void tblDetalleVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleVentaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblDetalleVentaMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Map p=new HashMap();

        Date fecha_inicial=(dcFechaini.getDate());
        Date fecha_final=(dcFechafin.getDate());

        p.put("fecha_ini" ,fecha_inicial);
        p.put("fecha_fin" ,fecha_final);

        JasperReport report;
        JasperPrint print;
        try{

            report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptVentasRealizadas.jrxml");
            print=JasperFillManager.fillReport(report, p,connection);
            JasperViewer view=new JasperViewer(print,false);
            view.setTitle("Reporte General de Ventas Realizadas");
            view.setVisible(true);
        }catch(JRException e){
            e.printStackTrace();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int fila = tblVenta.getSelectedRow();
        
        if(fila >= 0 ) {
            int id = this.ventas.get(fila).getIdVenta();
            new VentaDaoImpl().generarOrdenDeCompra( id );
            
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro de venta!");
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void radCotizacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radCotizacionesActionPerformed
        this.BuscarVenta();
    }//GEN-LAST:event_radCotizacionesActionPerformed

    private void radVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radVentaActionPerformed
        this.BuscarVenta();
    }//GEN-LAST:event_radVentaActionPerformed

    private void btnGenerarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVentaActionPerformed
        
        int fila = tblVenta.getSelectedRow();
        
        if(fila >= 0 ) {
            ClsEntidadVentaHib venta = this.ventas.get(fila);
            
            FrmVenta frmVenta = new FrmVenta(venta);
            FrmPrincipal.getInstance().getEscritorio().add(frmVenta);
            frmVenta.setVisible(true);
            
            this.dispose();
            
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro de venta!");
        }
        
    }//GEN-LAST:event_btnGenerarVentaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarVenta;
    private javax.swing.JButton btnVerDetalle;
    private com.toedter.calendar.JDateChooser dcFechafin;
    private com.toedter.calendar.JDateChooser dcFechaini;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblIdVenta;
    private javax.swing.JRadioButton radCotizaciones;
    private javax.swing.JRadioButton radVenta;
    private javax.swing.ButtonGroup rbgVentaCotizacion;
    private javax.swing.JTable tblDetalleVenta;
    private javax.swing.JTable tblVenta;
    private javax.swing.JTextField txtIDVenta;
    // End of variables declaration//GEN-END:variables
}
