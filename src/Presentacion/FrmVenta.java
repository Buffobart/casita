/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Conexion.ClsConexion;
import Conexion.HibernateUtil;
import Entidad.*;
import Negocio.*;
import daos.CuentaDao;
import daos.OperacionDao;
import daos.VentaDao;
import daos.impl.CuentaDaoImpl;
import daos.impl.OperacionDaoImpl;
import daos.impl.VentaDaoImpl;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.hibernate.Session;


public class FrmVenta extends javax.swing.JInternalFrame {
private Connection connection=new ClsConexion().getConection();
    String Total;
    String strCodigo;
    String accion;
    String numVenta,tipoDocumento;
    
    int registros;
    String id[]=new String[50];
    //String Datos[]=new String[50];

    static int intContador;
    public Integer IdEmpleado;
    public String NombreEmpleado;
    int idventa,nidventa;
     String idventa_print;
    //-----------------------------------------------
    public String codigo;
    static Connection conn=null;
    
    static ResultSet rs=null;
    DefaultTableModel dtm=new DefaultTableModel();
    DefaultTableModel dtmDetalle = new DefaultTableModel();

    String criterio,busqueda;
    
    List<ClsEntidadCuenta> cuentas;
    ClsEntidadVentaHib venta = null;
    
    OperacionDao operacionDao = new OperacionDaoImpl();
    CuentaDao cuentasDao = new CuentaDaoImpl();
    VentaDao ventaDao = new VentaDaoImpl();
    
    //String precioProducto1, precioProducto2;
    /*
    public void setPrecioProducto(String pr1, String pr2){
        this.precioProducto1 = pr1;
        this.precioProducto2 = pr2;
    }*/
    ClsEntidadProductoHib selectedProduct;
    ArrayList<ClsEntidadDetalleventaHib> detalles = new ArrayList<>();
    List<ClsEntidadTipodocumentoHib> tiposDeDocumento;
    
    public FrmVenta() {
        initComponents();
        //---------------------FECHA ACTUAL-------------------------------
        Date date=new Date();
        String format=new String("dd/MM/yyyy");
        SimpleDateFormat formato=new SimpleDateFormat(format);
        txtFecha.setDate(date);
        //---------------------GENERA NUMERO DE VENTA---------------------
        numVenta=generaNumVenta();
        txtNumero.setText(numVenta);
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        //this.setSize(955, 608);//[955, 608]
        cargarComboTipoDocumento();
   
        lblIdProducto.setVisible(false);
        lblIdCliente.setVisible(false);
        txtDescripcionProducto.setVisible(false);
        txtCostoProducto.setVisible(false);
        mirar();
        //--------------------JTABLE - DETALLEPRODUCTO--------------------
        
        String titulos[] = {"ID","CÓDIGO", "PRODUCTO","DESCRIPCIÓN", "CANT.", "COSTO", "PRECIO", "TOTAL"};
        dtmDetalle.setColumnIdentifiers(titulos);
        tblDetalleProducto.setModel(dtmDetalle);
        CrearTablaDetalleProducto();
        
        cargarCuentasBancarias();
        
        //this.IdEmpleado = FrmPrincipal.getInstance().getEmpleado().getIdEmpleado().toString();
    }
    
    public FrmVenta(ClsEntidadVentaHib venta) {
        this();
        modificar();
        this.venta = venta;
        
        fillFieldsWithData(venta);
        
    }
//-----------------------------------------------------------------------------------------------
//--------------------------------------METODOS--------------------------------------------------
//-----------------------------------------------------------------------------------------------
    
    public void fillFieldsWithData(ClsEntidadVentaHib venta){
        
        this.txtNombreCliente.setText( venta.getIdCliente().getNombre() );
        this.cboTipoDocumento.setSelectedItem( venta.getIdTipoDocumento().getDescripcion() );
        //fecha
        this.txtSerie.setText( venta.getSerie() );
        this.txtNumero.setText( venta.getNumero() );
        
        this.txtTotalPagar.setText( venta.getTotalPagar().toString() );
        this.txtSubTotal.setText( venta.getSubTotal().toString() );
        this.txtTotalVenta.setText( venta.getTotalVenta().toString() );
        this.txtIGV.setText( venta.getIgv().toString() );
        //this.CalcularIGV();
        
        //this.detalles = (ArrayList<ClsEntidadDetalleventaHib>) venta.getClsEntidadDetalleventaHibCollection();
        
        for(ClsEntidadDetalleventaHib detalle : venta.getClsEntidadDetalleventaHibCollection()){
            
            this.detalles.add(detalle);
                    
            ClsEntidadProductoHib p = detalle.getIdProducto();

            agregardatos(
                    p.getIdProducto(),
                    p.getCodigo(),
                    p.getNombre(),
                    p.getDescripcion(),
                    detalle.getCantidad().doubleValue(),
                    p.getPrecioCosto().floatValue(),
                    p.getPrecioVenta().floatValue(),
                    detalle.getTotal().doubleValue());
        }
        
        this.lblIdCliente.setText( venta.getIdCliente().getId().toString() );
        
    }
    
    private void cargarCuentasBancarias(){
        this.cuentas = cuentasDao.getAllCuentas();
        
        if(this.cuentas.size()<= 0){
            return;
        }
        
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
        
        //String etiquetas[] = new String[cuentas.size()];
        for(ClsEntidadCuenta cuenta: this.cuentas){
            comboModel.addElement(cuenta.getDescripcion());
        }
        
        this.cboCuentas.setModel(comboModel);
        
        ClsEntidadCuenta cuentaUsuario = FrmPrincipal.getInstance().getEmpleado().getCuenta();
        
        if(cuentaUsuario != null){
            this.cboCuentas.setSelectedItem(cuentaUsuario.getDescripcion());
        }
    }
    
  public String generaNumVenta(){

        ClsVenta oVenta=new ClsVenta(); 
        try {

            rs= oVenta.obtenerUltimoIdVenta();
            while (rs.next()) {
            if (rs.getString(1) != null) {
                Scanner s = new Scanner(rs.getString(1));
                int c = s.useDelimiter("C").nextInt() + 1;

                if (c < 10) {
                    return "C0000" + c;
                }
                if (c < 100) {
                    return "C000" + c;
                }
                if (c < 1000) {
                    return "C00" + c;
                }
                if (c < 10000) {
                    return "C0" + c;
                } else {
                    return "C" + c;
                }
            }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return "C00001";
              
  }
  
   void CrearTablaDetalleProducto(){
   //--------------------PRESENTACION DE JTABLE----------------------
      
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==1 || column==4 || column==5 || column==6 || column==7){
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
        for (int i=0;i<tblDetalleProducto.getColumnCount();i++){
            tblDetalleProducto.getColumnModel().getColumn(i).setCellRenderer(render);
        }
      
        //Activar ScrollBar
        tblDetalleProducto.setAutoResizeMode(tblDetalleProducto.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50,120,190,260,70,70,70,70};
        for(int i = 0; i < tblDetalleProducto.getColumnCount(); i++) {
            tblDetalleProducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Ocultar columa
        setOcultarColumnasJTable(tblDetalleProducto,new int[]{0,5});

   }
   void limpiarCampos(){

       txtTotalVenta.setText("0.0");
       txtDescuento.setText("0.0");
       txtSubTotal.setText("0.0");
       txtIGV.setText("0.0");
       txtTotalPagar.setText("0.0");
       
       lblIdProducto.setText("");
       txtCodigoProducto.setText("");
       txtNombreProducto.setText("");
       txtStockProducto.setText("");
       txtPrecioProducto.setText("");
       txtCantidadProducto.setText("1");
       txtTotalProducto.setText("");
       txtCodigoProducto.requestFocus();
       
       this.selectedProduct = null;
   }
       
   void mirar(){
       btnNuevo.setEnabled(true);
       btnGuardar.setEnabled(false);
       btnCancelar.setEnabled(false);
       btnCotizacion.setEnabled(false);
       btnSalir.setEnabled(true);
        

       cboTipoDocumento.setEnabled(false);
       //this.cboPrecio.setEnabled(false);
       txtCodigoProducto.setEnabled(false);
       txtSerie.setEnabled(false);
       txtCantidadProducto.setEnabled(false);
       txtFecha.setEnabled(false);
       txtNumero.setEnabled(false);
       
       btnBuscarCliente.setEnabled(false);
       btnBuscarProducto.setEnabled(false);
       btnAgregarProducto.setEnabled(false);
       btnEliminarProducto.setEnabled(false);
       btnLimpiarTabla.setEnabled(false);
       chkCambiarNumero.setEnabled(false);
       chkCambiarNumero.setSelected(false);
       
       txtTotalVenta.setText("0.0");
       txtDescuento.setText("0.0");
       txtSubTotal.setText("0.0");
       txtIGV.setText("0.0");
       txtTotalPagar.setText("0.0");
       lblIdProducto.setText("");
       txtCodigoProducto.setText("");
       txtNombreProducto.setText("");
       txtStockProducto.setText("");
       txtPrecioProducto.setText("");
       txtCantidadProducto.setText("1");
       txtTotalProducto.setText("");
       txtCodigoProducto.requestFocus();
       
       //this.setPrecioProducto("0", "0");
       

   }
   
   void modificar(){

       btnNuevo.setEnabled(false);

       btnGuardar.setEnabled(true);
       btnCancelar.setEnabled(true);
       btnCotizacion.setEnabled(true);
       btnSalir.setEnabled(false);
        
       cboTipoDocumento.setEnabled(true);
       //this.cboPrecio.setEnabled(true);
       txtCodigoProducto.setEnabled(true);
       txtSerie.setEnabled(true);
       txtCantidadProducto.setEnabled(true);
       txtFecha.setEnabled(true);
       
       btnBuscarCliente.setEnabled(true);
       btnBuscarProducto.setEnabled(true);
       btnAgregarProducto.setEnabled(true);
       btnEliminarProducto.setEnabled(true);
       btnLimpiarTabla.setEnabled(true);
       chkCambiarNumero.setEnabled(true);
       
       txtCodigoProducto.requestFocus();
   }
   
   
   
   void cargarComboTipoDocumento(){
       
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        this.tiposDeDocumento = session.createQuery("from ClsEntidadTipodocumentoHib").list();
        
        if(this.tiposDeDocumento.size() <= 0){
            return;
        }
        
        int i=0;
        String etiquetas[] = new String[tiposDeDocumento.size()];
        for(ClsEntidadTipodocumentoHib tipo: this.tiposDeDocumento){
            etiquetas[i] = tipo.getDescripcion();
            i++;
        }
        
        this.cboTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel(etiquetas));
        //this.cboCuentas.setSelectedIndex(0);
        session.close();
       
       /*
       ClsTipoDocumento tipodocumento=new ClsTipoDocumento();
       ArrayList<ClsEntidadTipoDocumento> tipodocumentos=tipodocumento.listarTipoDocumento();
       Iterator iterator=tipodocumentos.iterator();
       DefaultComboBoxModel DefaultComboBoxModel=new DefaultComboBoxModel();
       DefaultComboBoxModel.removeAllElements();
       
       cboTipoDocumento.removeAll();
       String fila[]=new String[2];
       intContador=0;
       
       while(iterator.hasNext()){
           ClsEntidadTipoDocumento TipoDocumento = new ClsEntidadTipoDocumento();
           TipoDocumento=(ClsEntidadTipoDocumento) iterator.next();
           id[intContador]=TipoDocumento.getStrIdTipoDocumento();
           fila[0]=TipoDocumento.getStrIdTipoDocumento();
           fila[1]=TipoDocumento.getStrDescripcionTipoDocumento();
           DefaultComboBoxModel.addElement(TipoDocumento.getStrDescripcionTipoDocumento());
           intContador++;              
       }
       cboTipoDocumento.setModel(DefaultComboBoxModel);
        */
    }
   
   

    void BuscarProductoPorCodigo(){
        String busqueda=null;
        busqueda=txtCodigoProducto.getText(); 
            try{
                ClsProducto oProducto=new ClsProducto();
                
                rs= oProducto.listarProductoActivoPorParametro("codigo",busqueda);
                while (rs.next()) {
                    if(rs.getString(2).equals(busqueda)) {
        
                            lblIdProducto.setText(rs.getString(1));
                            txtNombreProducto.setText(rs.getString(3));
                            txtDescripcionProducto.setText(rs.getString(4));
                            //DescripcionProducto=rs.getString(4);
                            txtStockProducto.setText(rs.getString(5));
                            txtCostoProducto.setText(rs.getString(7));
                            txtPrecioProducto.setText(rs.getString(8));             
                    }
                   break;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
                System.out.println(ex.getMessage());
            }
    
    }
    void BuscarClientePorDefecto(){
        try{
            ClsCliente oCliente=new ClsCliente();
            rs= oCliente.listarClientePorParametro("id","1");
            while (rs.next()) {
                lblIdCliente.setText(rs.getString(1));
                txtNombreCliente.setText(rs.getString(2));             
            break;
            }

         }catch(Exception ex){
            JOptionPane.showMessageDialog(this,ex.getMessage());
            System.out.println(ex.getMessage());
         }
    
    }
    private void setOcultarColumnasJTable(JTable tbl, int columna[]){
        for(int i=0;i<columna.length;i++)
        {
             tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
             tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
        }
    }
    
    
//-----------------------------------------------------------------------------------------------
//--------------------------------------METODOS--------------------------------------------------
//-----------------------------------------------------------------------------------------------
    void obtenerUltimoIdVenta_print(){
        try{
        ClsVenta oVenta=new ClsVenta(); 
        rs= oVenta.obtenerUltimoIdVenta();
            while (rs.next()) {
                idventa_print=String.valueOf(rs.getInt(1));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tblDetalleProducto = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnImporte = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnCotizacion = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtCantidadProducto = new javax.swing.JTextField();
        btnAgregarProducto = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        txtTotalProducto = new javax.swing.JTextField();
        btnEliminarProducto = new javax.swing.JButton();
        btnLimpiarTabla = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        btnBuscarProducto = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDescripcionProducto = new javax.swing.JLabel();
        txtStockProducto = new javax.swing.JTextField();
        txtPrecioProducto = new javax.swing.JTextField();
        lblIdProducto = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDescuentoProducto = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaxDescuentoProducto = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscarCliente = new javax.swing.JButton();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboTipoDocumento = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new com.toedter.calendar.JDateChooser();
        lblIdCliente = new javax.swing.JLabel();
        chkCambiarNumero = new javax.swing.JCheckBox();
        txtCostoProducto = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        txtIGV = new javax.swing.JTextField();
        txtTotalPagar = new javax.swing.JTextField();
        txtTotalVenta = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtImporte = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCambio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cboCuentas = new javax.swing.JComboBox();
        chkOrdenDeCompra = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro de Ventas");
        setPreferredSize(new java.awt.Dimension(955, 608));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(null);

        tblDetalleProducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblDetalleProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalleProducto.setRowHeight(22);
        jScrollPane3.setViewportView(tblDetalleProducto);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(10, 340, 790, 137);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setIconTextGap(0);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 70));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/venta.png"))); // NOI18N
        btnGuardar.setText("Generar Venta");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIconTextGap(0);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 110, 70));

        btnImporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/importe.png"))); // NOI18N
        btnImporte.setText("Importe");
        btnImporte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImporte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImporteActionPerformed(evt);
            }
        });
        jPanel4.add(btnImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 110, 70));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer.png"))); // NOI18N
        btnImprimir.setText("Recibo");
        btnImprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel4.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 110, 70));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setIconTextGap(0);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 110, 70));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/principal.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setIconTextGap(0);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 110, 70));

        btnCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/asistencia_m.png"))); // NOI18N
        btnCotizacion.setText("<html>Generar<br>Cotizacion</html>");
        btnCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizacionActionPerformed(evt);
            }
        });
        jPanel4.add(btnCotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 110, 70));

        getContentPane().add(jPanel4);
        jPanel4.setBounds(803, 10, 130, 560);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setText("CANTIDAD:");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 9, 70, 30));

        txtCantidadProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCantidadProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantidadProducto.setText("1");
        txtCantidadProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadProductoActionPerformed(evt);
            }
        });
        txtCantidadProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProductoKeyTyped(evt);
            }
        });
        jPanel3.add(txtCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 9, 60, 30));

        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Agregar_p1.png"))); // NOI18N
        btnAgregarProducto.setToolTipText("Agregar Producto");
        btnAgregarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarProductoMouseClicked(evt);
            }
        });
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        btnAgregarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnAgregarProductoKeyReleased(evt);
            }
        });
        jPanel3.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 5, 50, 40));

        jLabel24.setText("TOTAL:");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 9, 50, 30));

        txtTotalProducto.setBackground(new java.awt.Color(204, 255, 204));
        txtTotalProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalProducto.setForeground(new java.awt.Color(0, 102, 204));
        txtTotalProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalProducto.setDisabledTextColor(new java.awt.Color(0, 102, 204));
        txtTotalProducto.setEnabled(false);
        jPanel3.add(txtTotalProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 9, 80, 30));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Remove.png"))); // NOI18N
        btnEliminarProducto.setToolTipText("Eliminar Prodcuto");
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 5, 50, 40));

        btnLimpiarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nuevo1.png"))); // NOI18N
        btnLimpiarTabla.setToolTipText("Limpiar Tabla");
        btnLimpiarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTablaActionPerformed(evt);
            }
        });
        jPanel3.add(btnLimpiarTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 5, 50, 40));

        jPanel5.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 530, 50));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("SERIE");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, 60, 20));

        txtSerie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSerie.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSerie.setText("001");
        jPanel5.add(txtSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, 59, -1));

        txtNumero.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel5.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 110, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Nº DE VENTA");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 230, 110, 20));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Producto"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setText("CÓDIGO:");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 50, 20));

        txtCodigoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProductoKeyTyped(evt);
            }
        });
        jPanel2.add(txtCodigoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 190, -1));

        btnBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Buscar_p.png"))); // NOI18N
        btnBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProductoActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 27, 40, 35));

        jLabel17.setText("NOMBRE:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 55, 50, 20));

        txtNombreProducto.setEnabled(false);
        jPanel2.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 75, 240, -1));

        jLabel19.setText("STOCK:");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));
        jPanel2.add(txtDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 30, 20));

        txtStockProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtStockProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtStockProducto.setEnabled(false);
        jPanel2.add(txtStockProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 90, -1));

        txtPrecioProducto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPrecioProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioProducto.setEnabled(false);
        txtPrecioProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioProductoActionPerformed(evt);
            }
        });
        jPanel2.add(txtPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 90, -1));
        jPanel2.add(lblIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 20, 20));

        jLabel23.setText("PRECIO:");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 50, -1));

        jLabel14.setText("DESCUENTO:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, -1));

        txtDescuentoProducto.setText("0.0");
        txtDescuentoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoProductoKeyReleased(evt);
            }
        });
        jPanel2.add(txtDescuentoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 60, -1));

        jLabel16.setText("%");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, -1, -1));

        jLabel4.setText("Maximo descuento aplicable:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txtMaxDescuentoProducto.setEditable(false);
        jPanel2.add(txtMaxDescuentoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 70, -1));

        jLabel18.setText("<html>* Nota: Para descuentos mayores a los registrados en el sistema<br>se debe de pedir autorizacion.</html>");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jPanel5.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 530, 180));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Venta"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("CLIENTE:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 50, 20));

        btnBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Buscar_p.png"))); // NOI18N
        btnBuscarCliente.setAlignmentY(1.0F);
        btnBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarClienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 28, 40, 35));

        txtNombreCliente.setEnabled(false);
        jPanel1.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 35, 190, -1));

        jLabel13.setText("DOCUMENTO:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 15, 100, -1));

        cboTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoDocumentoActionPerformed(evt);
            }
        });
        jPanel1.add(cboTipoDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 35, 130, 20));

        jLabel2.setText("FECHA:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 15, 80, 20));
        jPanel1.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 35, 100, -1));
        jPanel1.add(lblIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 20, 20));

        jPanel5.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 530, 70));

        chkCambiarNumero.setText("Cambiar Número");
        chkCambiarNumero.setOpaque(false);
        chkCambiarNumero.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkCambiarNumeroStateChanged(evt);
            }
        });
        jPanel5.add(chkCambiarNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, 110, -1));
        jPanel5.add(txtCostoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 90, 20));

        getContentPane().add(jPanel5);
        jPanel5.setBounds(10, 10, 790, 320);

        jPanel6.setBackground(new java.awt.Color(255, 246, 227));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SUB TOTAL");
        jPanel6.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 3, 100, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("I.V.A.");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 3, 100, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("TOTAL A PAGAR");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(448, 3, 130, 20));

        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSubTotal.setEnabled(false);
        jPanel6.add(txtSubTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 25, 100, 30));

        txtIGV.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtIGV.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtIGV.setEnabled(false);
        jPanel6.add(txtIGV, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 25, 100, 30));

        txtTotalPagar.setBackground(new java.awt.Color(0, 0, 0));
        txtTotalPagar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTotalPagar.setForeground(new java.awt.Color(0, 255, 102));
        txtTotalPagar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalPagar.setDisabledTextColor(new java.awt.Color(0, 255, 102));
        txtTotalPagar.setEnabled(false);
        jPanel6.add(txtTotalPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(444, 25, 135, 30));

        txtTotalVenta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotalVenta.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotalVenta.setEnabled(false);
        jPanel6.add(txtTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 25, 100, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VALOR VENTA");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 3, 100, 20));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("DESCUENTO");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 3, 90, 20));

        txtDescuento.setEditable(false);
        txtDescuento.setBackground(new java.awt.Color(255, 255, 204));
        txtDescuento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
        });
        jPanel6.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 25, 90, 30));

        getContentPane().add(jPanel6);
        jPanel6.setBounds(210, 480, 590, 65);

        jPanel7.setBackground(new java.awt.Color(247, 254, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("IMPORTE");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 3, 80, 20));

        txtImporte.setBackground(new java.awt.Color(0, 0, 0));
        txtImporte.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtImporte.setForeground(new java.awt.Color(255, 255, 255));
        txtImporte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImporte.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtImporte.setEnabled(false);
        jPanel7.add(txtImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, 80, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("CAMBIO");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 3, 80, 20));

        txtCambio.setBackground(new java.awt.Color(0, 0, 0));
        txtCambio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCambio.setForeground(new java.awt.Color(255, 255, 0));
        txtCambio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCambio.setDisabledTextColor(new java.awt.Color(255, 255, 0));
        txtCambio.setEnabled(false);
        jPanel7.add(txtCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 25, 80, 30));

        getContentPane().add(jPanel7);
        jPanel7.setBounds(10, 480, 190, 65);

        jLabel10.setText("Cuenta:");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(10, 550, 39, 14);

        cboCuentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Por favor, agrega una cuenta en el menu \"Cuentas > Cuentas Bancarias y Cajas\"" }));
        cboCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCuentasActionPerformed(evt);
            }
        });
        getContentPane().add(cboCuentas);
        cboCuentas.setBounds(60, 550, 170, 20);

        chkOrdenDeCompra.setText("Generar Orden de Compra");
        chkOrdenDeCompra.setToolTipText("Genera una orden de compra con un provedor por los articulos de esta venta");
        chkOrdenDeCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOrdenDeCompraActionPerformed(evt);
            }
        });
        getContentPane().add(chkOrdenDeCompra);
        chkOrdenDeCompra.setBounds(250, 550, 170, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
//        txtIdEmpleado.setText(IdEmpleado);
        BuscarClientePorDefecto();
        cargarComboTipoDocumento();

        
    }//GEN-LAST:event_formComponentShown

    private void btnBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProductoActionPerformed
    Consultas.FrmBuscarProducto_Venta producto= new Consultas.FrmBuscarProducto_Venta(this);
    Presentacion.FrmPrincipal.Escritorio.add(producto);
    producto.toFront();
    producto.setVisible(true);
    }//GEN-LAST:event_btnBuscarProductoActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
    Consultas.FrmBuscarCliente_Venta cliente= new Consultas.FrmBuscarCliente_Venta();
    Presentacion.FrmPrincipal.Escritorio.add(cliente);
    cliente.toFront();
    cliente.setVisible(true);
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    

    void CalcularTotal(){
        
        if(null == this.selectedProduct){
            this.txtTotalProducto.setText("-");
            return;
        }
        
        try{
            
            DecimalFormat formater = FrmPrincipal.getInstance().getDecimalFormater();
            
            ClsEntidadMonedaHib moneda = this.selectedProduct.getMoneda();
            
            float precioVenta = this.selectedProduct.getPrecioVenta().multiply(moneda.getTipoCambio()).floatValue();
            
            //float precioVenta = this.selectedProduct.getPrecioVenta().floatValue();
            float descuento = Float.valueOf( this.txtDescuentoProducto.getText() );
            float precioConDescuento = (100f - descuento) * precioVenta / 100f;
            precioConDescuento *= Float.valueOf( this.txtCantidadProducto.getText() );
            
            this.txtTotalProducto.setText( formater.format(precioConDescuento) );
            
        }catch(NumberFormatException e){
            this.txtTotalProducto.setText("-");
            System.err.println(e.toString());
        }
        
        /*
        precio_prod=Double.parseDouble(txtPrecioProducto.getText());
        cant_prod=Double.parseDouble(txtCantidadProducto.getText());
        total_prod=precio_prod*cant_prod;
        txtTotalProducto.setText(String.valueOf(formateador.format(total_prod)));
                */
    }




    

    public int recorrer(int id){
        int fila = 0,valor=-1;
        
        fila = tblDetalleProducto.getRowCount();
        
        for (int f = 0; f < fila;f++) {
            if(Integer.parseInt(String.valueOf(dtmDetalle.getValueAt(f, 0)))==id){

                valor=f;
                //JOptionPane.showMessageDialog(null, "te encontre!");
                break;
                
                
            }else{
                //JOptionPane.showMessageDialog(null, "no estas!");
                valor= -1;
            }          
              
        }
        return valor;
    } 

    void agregardatos(int item, String cod, String nom,String descrip,double cant,double cost,double pre,double tot){
        
        int p=recorrer(item);
        double n_cant,n_total;
        if (p>-1){
                               
            n_cant=Double.parseDouble(String.valueOf(tblDetalleProducto.getModel().getValueAt(p,4)))+cant;
            tblDetalleProducto.setValueAt(n_cant,p,4);
                       
            //n_total=Double.parseDouble(String.valueOf(tblDetalleProducto.getModel().getValueAt(p,4)))*Double.parseDouble(String.valueOf(tblDetalleProducto.getModel().getValueAt(p,5)));
            n_total = Double.parseDouble( (String)tblDetalleProducto.getModel().getValueAt(p,7)) + tot;
            tblDetalleProducto.setValueAt(String.valueOf(n_total),p,7);
            

            //JOptionPane.showMessageDialog(null, "IGUALL!");
                            
        }else{
            String Datos[] = {String.valueOf(item),cod,nom,descrip,String.valueOf(cant),String.valueOf(cost),String.valueOf(pre),String.valueOf(tot)};
            dtmDetalle.addRow(Datos);
        }
        tblDetalleProducto.setModel(dtmDetalle);
    }
    void CalcularValor_Venta(){
        int fila = 0;
        double valorVenta = 0;
        fila = dtmDetalle.getRowCount();
        for (int f=0; f<fila; f++){
            valorVenta += Double.parseDouble(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 7)));            
        }
        txtTotalVenta.setText(String.valueOf(valorVenta));
    }
    void CalcularSubTotal(){
        double subtotal=0;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.##",simbolos);        
        subtotal=Double.parseDouble(txtTotalPagar.getText())/1.16;
        txtSubTotal.setText(String.valueOf(formateador.format(subtotal)));
    }
    void CalcularIGV(){
        double igv=0;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.##",simbolos);       
        igv=Double.parseDouble(txtSubTotal.getText())*0.16;
        txtIGV.setText(String.valueOf(formateador.format(igv)));
    }
    void CalcularTotal_Pagar(){
        double totalpagar=0;
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.##",simbolos);
        totalpagar=Double.parseDouble(txtTotalVenta.getText())-Double.parseDouble(txtDescuento.getText());
        txtTotalPagar.setText(String.valueOf(formateador.format(totalpagar)));
    }
    void limpiarTabla(){
        try{      
	int filas=tblDetalleProducto.getRowCount();
            for (int i = 0;filas>i; i++) {
                dtmDetalle.removeRow(0);
            }
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    private void actualizaCuentaBancaria(ClsEntidadCuenta cuenta){
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        session.update(cuenta);
        session.getTransaction().commit();
        session.close();
    }

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        double stock,cant;

        if(this.txtTotalProducto.getText().equals("-")){
            JOptionPane.showMessageDialog(null, "<html>Parece haber un error en la cantidad o en el porcentaje de descuento, "
                    + "<br>por favor, verifica estos campos<html>");
            return;
        }
        
        if(this.selectedProduct == null){
            JOptionPane.showMessageDialog(null, "<html>no has seleccionado ningún producto.<html>");
            return;
        }
        
        float descuento = Float.valueOf( this.txtDescuentoProducto.getText() );
        
        if( descuento > this.selectedProduct.getDescuentoVenta() ){
            //TODO: 
            
            /*
            JOptionPane.showMessageDialog(null, "<html>No es posible proporcionar un descuento mayor "
                    + "<br>al pre-establecido en el sistema.<html>");
            */
            
            if( FrmPrincipal.getInstance().getPermisoDescuento().equals("0") ){
                /** este ususario no tiene permiso para ofrecer 
                 * descuentos mayores a los pre-establecidos
                 */
                
                boolean autorizado = new FrmAutorizaDescuento( FrmPrincipal.getInstance(), true).pedirAutorizacion();
                System.out.println("Ejecutado, la autorizacion fue: " + autorizado);
                
                if(!autorizado){
                    return;
                }
            }
        }
        
        ClsEntidadProductoHib p = this.selectedProduct;
        //List
        
        ClsEntidadDetalleventaHib detalle = new ClsEntidadDetalleventaHib();
        detalle.setIdProducto(p);
        detalle.setCantidad(new BigDecimal( txtCantidadProducto.getText() ));
        detalle.setTotal(new BigDecimal( txtTotalProducto.getText() ));
        detalle.setCosto(p.getPrecioCosto());
        detalle.setPrecio(p.getPrecioVenta());
        detalle.setIdVenta(this.venta);

        int d1      = p.getIdProducto();
        String d2   = p.getCodigo();
        String d3   = p.getNombre();//txtNombreProducto.getText();
        String d4   = p.getDescripcion();
        double d5   = Double.parseDouble(txtCantidadProducto.getText());
        double d6   = p.getPrecioCosto().floatValue();
        double d7   = p.getPrecioVenta().floatValue();
        double d8   = Double.parseDouble(txtTotalProducto.getText());
        agregardatos(d1,d2,d3,d4,d5,d6,d7,d8);
        //HashMap<Integer, ClsEntidadDetalleventaHib> detalles = new HashMap<>();

        //esta este producto ya en la tabla?
        boolean existente = false;
        for( ClsEntidadDetalleventaHib det : this.detalles ){
            if(det.getIdProducto().equals(detalle.getIdProducto())){
                det.setCantidad( det.getCantidad().add( detalle.getCantidad() ) );
                det.setTotal( det.getTotal().add( detalle.getTotal() ) );
                existente = true;
            }
        }
        
        if(!existente){
            this.detalles.add(detalle);
        }
        
        //actualizarTabla();
        //this.venta.setClsEntidadDetalleventaHibCollection(detalles);

        CalcularValor_Venta();
        CalcularTotal_Pagar();
        CalcularSubTotal();
        CalcularIGV();

        txtCantidadProducto.setText("1");
        txtTotalProducto.setText("-");

        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtStockProducto.setText("");
        txtPrecioProducto.setText("");
        txtCodigoProducto.requestFocus();
        
        this.txtDescuentoProducto.setText("0.0");
        
        this.selectedProduct = null;

    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void txtCantidadProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProductoKeyReleased
        CalcularTotal();
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) btnAgregarProducto.requestFocus(); 
        
    }//GEN-LAST:event_txtCantidadProductoKeyReleased

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
    int fila = tblDetalleProducto.getSelectedRow();
        if (fila > 0) {
            dtmDetalle.removeRow(fila);
            CalcularValor_Venta();
            CalcularSubTotal();
            CalcularIGV();
        } else if (fila == 0) {
            dtmDetalle.removeRow(fila);

            txtTotalVenta.setText("0.0");
            txtDescuento.setText("0.0");
            txtSubTotal.setText("0.0");
            txtIGV.setText("0.0");
            txtTotalPagar.setText("0.0");
            CalcularValor_Venta();
            CalcularTotal_Pagar();
            CalcularSubTotal();
            CalcularIGV();
        }
        
        this.detalles.remove(fila);
        
        CalcularValor_Venta();
        CalcularTotal_Pagar();
        CalcularSubTotal();
        CalcularIGV();
        txtCodigoProducto.requestFocus();
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void txtCodigoProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProductoKeyReleased
        BuscarProductoPorCodigo(); 
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtCantidadProducto.requestFocus();                                                
      
    }//GEN-LAST:event_txtCodigoProductoKeyReleased

    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
        CalcularTotal_Pagar();
        CalcularSubTotal();
        CalcularIGV();
    }//GEN-LAST:event_txtDescuentoKeyReleased

    private void btnLimpiarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTablaActionPerformed
        limpiarTabla();
        txtTotalVenta.setText("0.0");
        txtDescuento.setText("0.0");
        txtSubTotal.setText("0.0");
        txtIGV.setText("0.0");
        txtTotalPagar.setText("0.0");
        txtCodigoProducto.requestFocus();
        
        this.detalles.clear();
    }//GEN-LAST:event_btnLimpiarTablaActionPerformed

    private void chkCambiarNumeroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkCambiarNumeroStateChanged
        if (chkCambiarNumero.isSelected()){
            txtNumero.setText("");
            txtNumero.setEnabled(true);
        }else{
            txtNumero.setEnabled(false);
            numVenta=generaNumVenta();
            txtNumero.setText(numVenta);
        }
    }//GEN-LAST:event_chkCambiarNumeroStateChanged

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        limpiarTabla();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        Presentacion.FrmVentaRecibo VentaRecibo= new Presentacion.FrmVentaRecibo();
        Presentacion.FrmPrincipal.Escritorio.add(VentaRecibo);
        Presentacion.FrmVentaRecibo.txtDocumentoVenta.setText(tipoDocumento);
        VentaRecibo.toFront();
        VentaRecibo.setVisible(true);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnImporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImporteActionPerformed
        // TODO add your handling code here:
        String ingreso = JOptionPane.showInputDialog(null, "Ingrese Importe a Cancelar", "0.0");
        Double importe,cambio;
        if (ingreso.compareTo("") != 0) {
            importe = Double.parseDouble(ingreso);
            txtImporte.setText(String.valueOf(importe));
            cambio = Double.parseDouble(txtImporte.getText()) - Double.parseDouble(txtTotalPagar.getText());
            txtCambio.setText(String.valueOf(cambio));
        } else {
            txtImporte.setText("0.0");
        }
    }//GEN-LAST:event_btnImporteActionPerformed

    private ClsEntidadVentaHib inputToVO(){
        //ClsEntidadVentaHib venta = new ClsEntidadVentaHib();
        
        venta.setIdTipoDocumento(this.tiposDeDocumento.get( this.cboTipoDocumento.getSelectedIndex()) );
        venta.setIdCliente(new ClsEntidadClienteHib(new Integer(lblIdCliente.getText())));
        venta.setIdEmpleado(new ClsEntidadEmpleadoHib(FrmPrincipal.getInstance().getEmpleado().getIdEmpleado()));
        venta.setSerie(txtSerie.getText());
        venta.setNumero(txtNumero.getText());
        venta.setFecha(txtFecha.getDate());
        venta.setTotalVenta(new BigDecimal(txtTotalVenta.getText()));
        venta.setDescuento(new BigDecimal(txtDescuento.getText()));
        venta.setSubTotal(new BigDecimal(txtSubTotal.getText()));
        venta.setIgv(new BigDecimal(txtIGV.getText()));
        venta.setEstado(ClsEntidadVentaHib.PRO_TIPO_VENTA);
        venta.setTotalPagar(new BigDecimal(txtTotalPagar.getText()));
        venta.setCuenta(this.cuentas.get(this.cboCuentas.getSelectedIndex()));

        venta.setClsEntidadDetalleventaHibCollection(this.detalles);
        
        return venta;
    }
    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        inputToVO();
        //venta.setClsEntidadDetalleventaHibCollection(this.detalles);
        this.ventaDao.saveOrUpdateVenta(venta);
        
        /*
        double total = Double.parseDouble(txtTotalVenta.getText());
        ClsEntidadCuenta cuenta = this.cuentas.get(this.cboCuentas.getSelectedIndex());

        ClsEntidadOperacionHib operacion = new ClsEntidadOperacionHib();
        operacion.setTipo("VENTA");
        operacion.setCuenta(cuenta);
        operacion.setCantidad(BigDecimal.valueOf(total));
        operacion.setUsuario(new ClsEntidadEmpleadoHib(Integer.valueOf(FrmPrincipal.getInstance().strIdEmpleado)));
        operacion.setHora(new Date());
        */

        /** Save the log(operation) */
        operacionDao.addOperacion(venta);

        if(this.chkOrdenDeCompra.isSelected()){
            generarOrdenDeCompra(venta);
        }

        mirar();
        tipoDocumento = cboTipoDocumento.getSelectedItem().toString();
        limpiarTabla();
        numVenta = generaNumVenta();
        txtNumero.setText(numVenta);
        BuscarClientePorDefecto();

        /*
         //------------ Imprimir Venta --------------            
         if(cboTipoDocumento.getSelectedItem().equals("TICKET")){
         obtenerUltimoIdVenta_print();
         Map p=new HashMap();
         p.put("busqueda", idventa_print);
            
         JasperReport report;
         JasperPrint print;
         try{
         report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptVentaTicket.jrxml");
         print=JasperFillManager.fillReport(report, p,connection);
         JasperViewer view=new JasperViewer(print,false);
         //                view.setTitle("Reporte de Venta");
         //                view.setVisible(true);


         JasperPrintManager.printReport(print, false);
         //JOptionPane.showMessageDialog(null, "Id ultima venta" + idventa_print);

         }catch(JRException e){
         e.printStackTrace();
         }
         }
 
         */
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion="Nuevo";
        this.venta = new ClsEntidadVentaHib();
        this.detalles.clear();
        modificar();
        limpiarCampos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtCantidadProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProductoKeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtCantidadProductoKeyTyped

    private void txtCodigoProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProductoKeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtCodigoProductoKeyTyped

    private void btnAgregarProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAgregarProductoKeyReleased

    }//GEN-LAST:event_btnAgregarProductoKeyReleased

    private void btnAgregarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarProductoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarProductoMouseClicked

    private void txtPrecioProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioProductoActionPerformed

    private void cboTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoDocumentoActionPerformed

    private void cboCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCuentasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCuentasActionPerformed

    private void txtDescuentoProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoProductoKeyReleased
        // TODO add your handling code here:
        CalcularTotal();
    }//GEN-LAST:event_txtDescuentoProductoKeyReleased

    private void txtCantidadProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadProductoActionPerformed

    private void chkOrdenDeCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOrdenDeCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkOrdenDeCompraActionPerformed

    private void btnCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotizacionActionPerformed
        
        inputToVO();
        venta.setEstado(ClsEntidadVentaHib.PRO_TIPO_COTIZACION);
        //venta.setClsEntidadDetalleventaHibCollection(this.detalles);
        this.ventaDao.saveOrUpdateVenta(venta);
        
        this.generarOrdenDeCompra(venta);
        
        
    }//GEN-LAST:event_btnCotizacionActionPerformed
    void obtenerUltimoIdVenta(){
        try{
        ClsVenta oVenta=new ClsVenta(); 
        rs= oVenta.obtenerUltimoIdVenta();
            while (rs.next()) {
                idventa = rs.getInt(1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    void guardarDetalle(){
        
        obtenerUltimoIdVenta();
        ClsDetalleVenta detalleventas=new ClsDetalleVenta();
        ClsEntidadDetalleVenta detalleventa=new ClsEntidadDetalleVenta();
        ClsProducto productos=new ClsProducto();
        String codigo,strId;
        ClsEntidadProducto producto=new ClsEntidadProducto();
        int fila=0;
        
        double cant = 0,ncant=0,stock=0;   
        fila =tblDetalleProducto.getRowCount();
        for (int f=0; f<fila; f++){
            detalleventa.setStrIdVenta(String.valueOf(idventa));
            detalleventa.setStrIdProducto(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 0)));
            detalleventa.setStrCantidadDet(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 4)));
            detalleventa.setStrCostoDet(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 5)));
            detalleventa.setStrPrecioDet(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 6)));
            detalleventa.setStrTotalDet(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 7)));  
            detalleventas.agregarDetalleVenta(detalleventa);
            


            try{
                ClsProducto oProducto=new ClsProducto();
                
                rs= oProducto.listarProductoActivoPorParametro("id",((String) tblDetalleProducto.getValueAt(f, 0)));
                while (rs.next()) {
                            cant=Double.parseDouble(rs.getString(5));
                }
                

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
                System.out.println(ex.getMessage());
            }        

            
            
        strId =  ((String) tblDetalleProducto.getValueAt(f, 0));

        ncant=Double.parseDouble(String.valueOf(tblDetalleProducto.getModel().getValueAt(f, 4)));

        stock=cant-ncant;
        producto.setStrStockProducto(String.valueOf(stock));
        productos.actualizarProductoStock(strId, producto);



    }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarProducto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCotizacion;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImporte;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiarTabla;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cboCuentas;
    private javax.swing.JComboBox cboTipoDocumento;
    private javax.swing.JCheckBox chkCambiarNumero;
    private javax.swing.JCheckBox chkOrdenDeCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JLabel lblIdCliente;
    public static javax.swing.JLabel lblIdProducto;
    private javax.swing.JTable tblDetalleProducto;
    private javax.swing.JTextField txtCambio;
    private javax.swing.JTextField txtCantidadProducto;
    public static javax.swing.JTextField txtCodigoProducto;
    public static javax.swing.JLabel txtCostoProducto;
    public static javax.swing.JLabel txtDescripcionProducto;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDescuentoProducto;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtIGV;
    private javax.swing.JTextField txtImporte;
    public static javax.swing.JTextField txtMaxDescuentoProducto;
    public static javax.swing.JTextField txtNombreCliente;
    public static javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNumero;
    public static javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtSerie;
    public static javax.swing.JTextField txtStockProducto;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotalPagar;
    private javax.swing.JTextField txtTotalProducto;
    private javax.swing.JTextField txtTotalVenta;
    // End of variables declaration//GEN-END:variables

    public void setSelectedProduct(ClsEntidadProductoHib product) {
        this.selectedProduct = product;
    }

    public void updateProductDetails() {
        
        DecimalFormat formater = FrmPrincipal.getInstance().getDecimalFormater();
        
        ClsEntidadMonedaHib moneda = this.selectedProduct.getMoneda();
        String costo = formater.format( this.selectedProduct.getPrecioCosto().multiply(moneda.getTipoCambio()) );
        String precio = formater.format( this.selectedProduct.getPrecioVenta().multiply(moneda.getTipoCambio()) );
        
        
        
        this.lblIdProducto.setText( this.selectedProduct.getIdProducto().toString() );
        this.txtCodigoProducto.setText( this.selectedProduct.getCodigo() );
        this.txtNombreProducto.setText( this.selectedProduct.getNombre() );
        this.txtDescripcionProducto.setText( this.selectedProduct.getDescripcion() );
        this.txtStockProducto.setText( this.selectedProduct.getStock().toString() );
        this.txtCostoProducto.setText( costo );          
        this.txtPrecioProducto.setText( precio );
        this.txtMaxDescuentoProducto.setText( String.valueOf( this.selectedProduct.getDescuentoVenta() ) );
        
        this.txtCantidadProducto.setText("1");
        this.CalcularTotal();
    }

    private void actualizarTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void generarOrdenDeCompra(ClsEntidadVentaHib venta) {
            this.ventaDao.generarOrdenDeCompra(venta.getIdVenta());
    }
}
