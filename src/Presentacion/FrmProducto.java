/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Conexion.ClsConexion;
import Conexion.HibernateUtil;
import Entidad.*;
import Negocio.*;
import daos.ProveedorDao;
import daos.impl.ProductoDaoImpl;
import daos.impl.ProveedorDaoImpl;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import net.sourceforge.barbecue.BarcodeFactory;   
import net.sourceforge.barbecue.Barcode;   
import net.sourceforge.barbecue.BarcodeException;   
import net.sourceforge.barbecue.BarcodeImageHandler;   
import java.awt.event.*;   
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sourceforge.barbecue.output.OutputException;
import org.hibernate.Session;

public class FrmProducto extends javax.swing.JInternalFrame {
    private Connection connection=new ClsConexion().getConection();
    String Total;
    String strCodigo;
    String accion;
    int registros;
    String id[]=new String[50];
    static int intContador;
    
    //-----------------------------------------------
    public String codigo;
    static Connection conn=null;
    static ResultSet rs=null;
    DefaultTableModel dtm=new DefaultTableModel();
    String criterio,busqueda;
    
    List<ClsEntidadMonedaHib> monedas;
    List<ClsEntidadProductoHib> productos;
    ProveedorDao proveedoresDao = new ProveedorDaoImpl();
    List<ClsEntidadProveedorHib> proveedores;
    
    ProductoDaoImpl productoDao = new ProductoDaoImpl();
    
    public FrmProducto() {
        initComponents();
        tabProducto.setIconAt(tabProducto.indexOfComponent(pBuscar), new ImageIcon("src/iconos/busca_p1.png"));
        tabProducto.setIconAt(tabProducto.indexOfComponent(pNuevo), new ImageIcon("src/iconos/nuevo1.png"));
        cargarComboCategoria();
        cargarComboMoneda();
        cargarComboProveedor();
        /*
        buttonGroup1.add(rbtnCodigo);
        buttonGroup1.add(rbtnNombre);
        buttonGroup1.add(rbtnDescripcion);
        buttonGroup1.add(rbtnCategoria);
        */
        buttonGroup2.add(rbtnActivo);
        buttonGroup2.add(rbtnInactivo);
        
        mirar();
        actualizarTabla();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(805, 519);
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
        
                    if(column==0 || column==1 || column==4 || column==5 || column==6 || column==7 || column==8 || column==9 || column==10){
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
        for (int i=0;i<tblProducto.getColumnCount();i++){
            tblProducto.getColumnModel().getColumn(i).setCellRenderer(render);
        }
        */
      
        //Activar ScrollBar
        tblProducto.setAutoResizeMode(tblProducto.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {40,100,150,200,60,60,60,60,60,80,100,60,60};
        for(int i = 0; i < tblProducto.getColumnCount(); i++) {
            tblProducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
   }
   void CantidadTotal(){
        Total= String.valueOf(tblProducto.getRowCount());   
        lblEstado.setText("Se cargaron " + Total + " registros");      
   }
   void limpiarCampos(){
       txtCodigo.setText("");
       txtCodigoBar.setText("");
       txtNombre.setText("");
       txtDescripcion.setText("");
       txtStock.setText("");
       txtStockMin.setText("");
       txtPrecioCosto.setText("");
       txtPrecioVenta.setText("");
       txtUtilidad.setText("");
       txtCodigoBar.requestFocus();
       rbtnActivo.setSelected(true);
       rbtnInactivo.setSelected(false);
//       txtStock.setText("0");
//       txtStockMin.setText("0");
       txtPrecioCosto.setText("0.0");
       txtPrecioVenta.setText("0.0");
       txtUtilidad.setText("0.0");
       /*
       rbtnCodigo.setSelected(false);
       rbtnNombre.setSelected(false);
       rbtnDescripcion.setSelected(false);
       rbtnCategoria.setSelected(false);
       */
       txtBusqueda.setText("");
       limpiarList();
       
       this.txtDescuentoProveedor.setText("0.0");
       this.txtDescuentoAplicable.setText("0.0");
       this.txtCostoConDescuento.setText("0.0");
   }
       
   void mirar(){
       tblProducto.setEnabled(true);
       btnNuevo.setEnabled(true);
       btnModificar.setEnabled(true);
       btnGuardar.setEnabled(false);
       btnCancelar.setEnabled(false);
       btnSalir.setEnabled(true);
        
       txtCodigoBar.setEnabled(false);
       txtNombre.setEnabled(false);
       txtDescripcion.setEnabled(false);
       txtStock.setEnabled(false);
       txtStockMin.setEnabled(false);
       txtPrecioCosto.setEnabled(false);
       txtPrecioVenta.setEnabled(false);
       //txtPrecioVenta2.setEnabled(false);
       cboCategoria.setEnabled(false);
       rbtnActivo.setEnabled(false);
       rbtnInactivo.setEnabled(false);
       
       btnActualizar.setEnabled(false);
       lstCodigos.setEnabled(false);
       cboTipoCodificacion.setEnabled(false);
       btnGenerar.setEnabled(false);
       
       this.txtDescuentoProveedor.setEnabled(false);
       this.txtDescuentoAplicable.setEnabled(false);
   
   }
   
   void modificar(){
       tblProducto.setEnabled(false);
       btnNuevo.setEnabled(false);
       btnModificar.setEnabled(false);
       btnGuardar.setEnabled(true);
       btnCancelar.setEnabled(true);
       btnSalir.setEnabled(false);
        
       txtCodigoBar.setEnabled(true);
       txtNombre.setEnabled(true);
       txtDescripcion.setEnabled(true);
       txtStock.setEnabled(true);
       txtStockMin.setEnabled(true);
       txtPrecioCosto.setEnabled(true);
       txtPrecioVenta.setEnabled(true);
       //txtPrecioVenta2.setEnabled(true);
       cboCategoria.setEnabled(true);
       rbtnActivo.setEnabled(true);
       rbtnInactivo.setEnabled(true);
       
       this.txtDescuentoProveedor.setEnabled(true);
       this.txtDescuentoAplicable.setEnabled(true);
       
       btnActualizar.setEnabled(true);
       lstCodigos.setEnabled(true);
       cboTipoCodificacion.setEnabled(true);
       btnGenerar.setEnabled(true);
       txtCodigoBar.requestFocus();
   }
  void cargarComboCategoria(){
  ClsCategoria tipodocumento=new ClsCategoria();
       ArrayList<ClsEntidadCategoria> categorias=tipodocumento.listarCategoria();
       Iterator iterator=categorias.iterator();
       DefaultComboBoxModel DefaultComboBoxModel=new DefaultComboBoxModel();
       DefaultComboBoxModel.removeAllElements();
       
       cboCategoria.removeAll();
       String fila[]=new String[2];
       intContador=0;
       
       while(iterator.hasNext()){
           ClsEntidadCategoria Categoria = new ClsEntidadCategoria();
           Categoria=(ClsEntidadCategoria) iterator.next();
           id[intContador]=Categoria.getStrIdCategoria();
           fila[0]=Categoria.getStrIdCategoria();
           fila[1]=Categoria.getStrDescripcionCategoria();
           DefaultComboBoxModel.addElement(Categoria.getStrDescripcionCategoria());
           intContador++;              
       }
       cboCategoria.setModel(DefaultComboBoxModel);
    }
    void actualizarTabla(){
        this.productos = this.productoDao.getAllProductos(this.chkInactivos.isSelected());
        this.SetDataToTable();
   }
    
    private void SetDataToTable(){
        String titulos[]={"ID","Cód. de Barras","Nombre","Descripción",
           "Stock","Stock Min.","Costo","Precio","Utilidad","Estado","Categoría",
           "Descuento Proveedor", "Descuento Venta"};
       
       DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
       
       String fila[]=new String[titulos.length];
       
        for(ClsEntidadProductoHib producto : productos){
            fila[0]=producto.getIdProducto().toString();
            fila[1]=producto.getCodigo();
            fila[2]=producto.getNombre();
            fila[3]=producto.getDescripcion();
            fila[4]=producto.getStock().toString();
            fila[5]=producto.getStockMin().toString();
            fila[6]=producto.getPrecioCosto().toString();
            fila[7]=producto.getPrecioVenta().toString();
            fila[8]=producto.getUtilidad().toString();
            fila[9]=producto.getEstado();
            fila[10]=producto.getDescripcion();
            fila[11]=String.valueOf( producto.getDescuentoProveedor() );
            fila[12]=String.valueOf( producto.getDescuentoVenta() );
            defaultTableModel.addRow(fila);
        }
        
        this.tblProducto.setModel(defaultTableModel);
    }
    
   void BuscarProducto(){
        busqueda=txtBusqueda.getText();
        
        this.productos = this.productoDao.buscar(busqueda, this.chkInactivos.isSelected());

        this.SetDataToTable();
        
    }

    void listardatos(){
        String estado;
        DefaultTableModel defaultTableModel;//=new DefaultTableModel();
        if(registros==-1){
            JOptionPane.showMessageDialog(null,"Se debe seleccionar un registro");
        }else{
            defaultTableModel=(DefaultTableModel) tblProducto.getModel();
            strCodigo=((String) defaultTableModel.getValueAt(registros,0));
            txtCodigo.setText((String)defaultTableModel.getValueAt(registros,0));
            txtCodigoBar.setText((String)defaultTableModel.getValueAt(registros,1));
            txtNombre.setText((String)defaultTableModel.getValueAt(registros,2));
            txtDescripcion.setText((String)defaultTableModel.getValueAt(registros,3));
            txtStock.setText((String)defaultTableModel.getValueAt(registros,4));
            txtStockMin.setText((String)defaultTableModel.getValueAt(registros,5));
            txtPrecioCosto.setText((String)defaultTableModel.getValueAt(registros,6));
            txtPrecioVenta.setText((String)defaultTableModel.getValueAt(registros,7));
            //txtPrecioVenta2.setText((String)defaultTableModel.getValueAt(registros,8));
            txtUtilidad.setText((String)defaultTableModel.getValueAt(registros,8));
            if ("ACTIVO".equals((String) defaultTableModel.getValueAt(registros,9))){
               rbtnActivo.setSelected(true);
            }else if ("INACTIVO".equals((String) defaultTableModel.getValueAt(registros,9))){
               rbtnInactivo.setSelected(true);
            }
            cboCategoria.setSelectedItem((String)defaultTableModel.getValueAt(registros,10));
            
            this.txtDescuentoProveedor.setText( (String)defaultTableModel.getValueAt(registros,11) );
            this.txtDescuentoAplicable.setText( (String)defaultTableModel.getValueAt(registros,12) );
            
            tblProducto.setRowSelectionInterval(registros,registros);
        }
    
    }
    
    /*
    void CalcularUtilidad(){
        double pre_costo=0,pre_venta=0,utilidad=0,t_utilidad;
        pre_costo=Double.parseDouble(txtPrecioCosto.getText());
        pre_venta=Double.parseDouble(txtPrecioVenta.getText());
        utilidad=pre_venta-pre_costo;
        t_utilidad=Math.rint(utilidad*100)/100;
        txtUtilidad.setText(String.valueOf(t_utilidad));
    }*/
    
    private void calcularValoresAdicionales(){
        
        try{
            
            float costo = Float.parseFloat( this.txtPrecioCosto.getText() );
            float descuentoProveedor = Float.parseFloat( this.txtDescuentoProveedor.getText() );
            
            float costoConDescuento = ((100 - descuentoProveedor) * costo) / 100;
            this.txtCostoConDescuento.setText( String.valueOf(costoConDescuento) );
            
            float precioVenta = Float.parseFloat( this.txtPrecioVenta.getText() );
            float utilidad = precioVenta - costoConDescuento;
            
            this.txtUtilidad.setText( String.valueOf(utilidad) );
            
        }catch(NumberFormatException e){
            this.txtCostoConDescuento.setText("0.0");
            this.txtUtilidad.setText("0.0");
            
            e.printStackTrace();
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tabProducto = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtBusqueda = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        chkInactivos = new javax.swing.JCheckBox();
        pNuevo = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigoBar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtStockMin = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPrecioCosto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        rbtnActivo = new javax.swing.JRadioButton();
        rbtnInactivo = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtUtilidad = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox();
        btnGenerar = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        lblprueba = new javax.swing.JLabel();
        cboTipoCodificacion = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstCodigos = new javax.swing.JList();
        btnActualizar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtDescuentoProveedor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDescuentoAplicable = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCostoConDescuento = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cboMoneda = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        cboProveedor = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro del Producto");
        getContentPane().setLayout(null);

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setIconTextGap(0);
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar);
        btnModificar.setBounds(690, 200, 81, 70);

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
        getContentPane().add(btnCancelar);
        btnCancelar.setBounds(690, 280, 81, 70);

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
        getContentPane().add(btnNuevo);
        btnNuevo.setBounds(690, 40, 81, 70);

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
        getContentPane().add(btnSalir);
        btnSalir.setBounds(690, 360, 81, 70);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setIconTextGap(0);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(690, 120, 81, 70);

        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mantenimiento"));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(680, 20, 100, 460);

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pBuscar.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 200, 20));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pBuscar.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 70, 40));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/report.png"))); // NOI18N
        jButton3.setText("Reporte");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pBuscar.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 30, 110, 40));
        pBuscar.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 360, 20));

        jLabel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jLabel13.setOpaque(true);
        pBuscar.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 640, 80));

        tblProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProducto.setRowHeight(22);
        tblProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProducto);

        pBuscar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 640, 290));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cancelar_p.png"))); // NOI18N
        jButton1.setText("Desactivar");
        jButton1.setPreferredSize(new java.awt.Dimension(81, 71));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pBuscar.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 160, 30));

        chkInactivos.setText("Mostrar inactivos");
        chkInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkInactivosMouseClicked(evt);
            }
        });
        pBuscar.add(chkInactivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        tabProducto.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigo.setEnabled(false);
        pNuevo.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 70, 20));

        jLabel3.setText("ID Producto:");
        pNuevo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 70, 20));

        jLabel2.setText("Código de Barras:");
        pNuevo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 100, 20));

        txtCodigoBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoBarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoBarKeyTyped(evt);
            }
        });
        pNuevo.add(txtCodigoBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 130, -1));

        jLabel5.setText("Nombre:");
        pNuevo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 50, 20));

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        pNuevo.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 310, -1));

        txtStockMin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStockMin.setToolTipText("Mínima cantidad de ítems que se pueden tener en el inventario");
        txtStockMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockMinKeyReleased(evt);
            }
        });
        pNuevo.add(txtStockMin, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, 80, 30));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Existencia Mínima:");
        pNuevo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 220, 90, 30));

        txtPrecioVenta.setBackground(new java.awt.Color(254, 254, 241));
        txtPrecioVenta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecioVenta.setToolTipText("Precio al que se venderá el producto al público");
        txtPrecioVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioVentaKeyTyped(evt);
            }
        });
        pNuevo.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 80, 30));

        jLabel8.setText("Descripción:");
        pNuevo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 70, 20));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Costo (Proveedor):");
        pNuevo.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 110, 20));

        txtPrecioCosto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPrecioCosto.setToolTipText("Precio al que se compra el producto al proveedor");
        txtPrecioCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioCostoKeyReleased(evt);
            }
        });
        pNuevo.add(txtPrecioCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 80, 30));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Precio Venta:");
        pNuevo.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 80, 30));

        rbtnActivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnActivo.setText("ACTIVO");
        rbtnActivo.setOpaque(false);
        pNuevo.add(rbtnActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 178, 80, -1));

        rbtnInactivo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rbtnInactivo.setText("INACTIVO");
        rbtnInactivo.setOpaque(false);
        pNuevo.add(rbtnInactivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 178, 90, -1));

        jLabel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado"));
        pNuevo.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 190, 50));

        jLabel15.setText("Utilidad:");
        pNuevo.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 50, 30));

        txtUtilidad.setEditable(false);
        txtUtilidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUtilidad.setToolTipText("Ganancia que se espera obtener al vender el producto");
        txtUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUtilidadActionPerformed(evt);
            }
        });
        pNuevo.add(txtUtilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, 80, 30));

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        pNuevo.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 310, 50));

        jLabel4.setText("Categoría:");
        pNuevo.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 60, 30));

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pNuevo.add(cboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 120, 30));

        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Generar.png"))); // NOI18N
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });
        pNuevo.add(btnGenerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 320, 110, 40));

        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel.add(lblprueba, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 160, 90));

        pNuevo.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 180, 90));

        cboTipoCodificacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Code 128" }));
        pNuevo.add(cboTipoCodificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 160, 30));

        jLabel9.setText("Tipo de Codificación:");
        pNuevo.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, -1, -1));

        jLabel16.setText("Código Alternativos:");
        pNuevo.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 110, 30));

        lstCodigos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstCodigos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstCodigosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstCodigos);

        pNuevo.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 200, 70));

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Refresh.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        pNuevo.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 40, 30));

        jLabel19.setForeground(new java.awt.Color(0, 51, 153));
        jLabel19.setText("Los campos marcado con un asterísco (*) son obligatorios");
        pNuevo.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 280, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 51, 153));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("*");
        pNuevo.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 20, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 51, 153));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("*");
        pNuevo.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 70, 20, 20));

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/printer.png"))); // NOI18N
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        pNuevo.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, 60, 40));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Descuento Proveedor:");
        pNuevo.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, 20));

        txtDescuentoProveedor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescuentoProveedor.setText("0.0");
        txtDescuentoProveedor.setToolTipText("Descuento que el proveedor da a la empresa a la hora de comprar el producto");
        txtDescuentoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoProveedorActionPerformed(evt);
            }
        });
        txtDescuentoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoProveedorKeyReleased(evt);
            }
        });
        pNuevo.add(txtDescuentoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 50, 30));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Existencia:");
        pNuevo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        txtStock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStock.setToolTipText("Cantidad de ítems que se encuentran actualmente en el inventario");
        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStockKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockKeyTyped(evt);
            }
        });
        pNuevo.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 80, 30));

        jLabel17.setText("Descuento aplicable venta:");
        pNuevo.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        txtDescuentoAplicable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtDescuentoAplicable.setText("0.0");
        txtDescuentoAplicable.setToolTipText("<html>Máximo descuento que se puede ofrecer al cliente. </br>Definir un valor diferente a cero, no quiere decir siempre se aplicará este descuento, eso se especifica en la ventana de “venta” antes de realizar una venta</html>");
        pNuevo.add(txtDescuentoAplicable, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 50, 30));

        jLabel18.setText("Costo con descuento:");
        pNuevo.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 310, -1, -1));

        txtCostoConDescuento.setEditable(false);
        txtCostoConDescuento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCostoConDescuento.setToolTipText("Precio final que se pagó al proveedor ya con el descuento incluido");
        pNuevo.add(txtCostoConDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 80, 30));

        jLabel22.setText("%");
        pNuevo.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, -1, -1));

        jLabel23.setText("%");
        pNuevo.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, -1, -1));

        jLabel24.setText("Moneda:");
        pNuevo.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, -1, -1));

        cboMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pNuevo.add(cboMoneda, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 120, -1));

        jLabel25.setText("Proveedor:");
        pNuevo.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, -1, -1));

        cboProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pNuevo.add(cboProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 400, 180, -1));

        tabProducto.addTab("Nuevo / Modificar", pNuevo);

        getContentPane().add(tabProducto);
        tabProducto.setBounds(10, 10, 670, 470);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if(tblProducto.getSelectedRows().length > 0 ) {
            accion="Modificar";
            modificar();
            tabProducto.setSelectedIndex(tabProducto.indexOfComponent(pNuevo));
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        tabProducto.setSelectedIndex(tabProducto.indexOfComponent(pBuscar));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion="Nuevo";
        modificar();
        limpiarCampos();
        tblProducto.setEnabled(false);
        tabProducto.setSelectedIndex(tabProducto.indexOfComponent(pNuevo));
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed
//----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos(){
        if(txtCodigoBar.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Especifique un código de barras para el Producto");
            txtCodigoBar.requestFocus();
            txtCodigoBar.setBackground(Color.YELLOW);
            return false;
        }else if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese un nombre para el Producto");
            txtNombre.requestFocus();
            txtNombre.setBackground(Color.YELLOW);
            return false;
        }else{
            return true;
        }

    }
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    if(validardatos()==true){   
        ClsEntidadProductoHib producto = new ClsEntidadProductoHib();
        producto.setCodigo(txtCodigoBar.getText());
        producto.setNombre(txtNombre.getText());
        producto.setDescripcion(txtDescripcion.getText());
        producto.setStock( new BigDecimal(txtStock.getText()) );
        producto.setStockMin( new BigDecimal(txtStockMin.getText()) );
        producto.setPrecioCosto( new BigDecimal(txtPrecioCosto.getText()) );
        producto.setPrecioVenta( new BigDecimal(txtPrecioVenta.getText()) );
        producto.setUtilidad( new BigDecimal(txtUtilidad.getText()) );
        if (rbtnActivo.isSelected()){
            producto.setEstado("ACTIVO");
        }else if (rbtnInactivo.isSelected()){
            producto.setEstado("INACTIVO");
        }
        ClsEntidadCategoriaHib categoria = new ClsEntidadCategoriaHib();
        categoria.setIdCategoria(new Integer( id[cboCategoria.getSelectedIndex()] ) );
        producto.setIdCategoria(categoria);
        producto.setDescuentoProveedor( new Float(this.txtDescuentoProveedor.getText()) );
        producto.setDescuentoVenta( new Float(this.txtDescuentoAplicable.getText()) );
        producto.setMoneda(this.monedas.get(this.cboMoneda.getSelectedIndex()));
        producto.setProveedor( this.proveedores.get( this.cboProveedor.getSelectedIndex() ));
        
        if(accion.equals("Nuevo")){
          
            Session session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            session.save(producto);
            session.getTransaction().commit();
            //JOptionPane.showMessageDialog(null, "Los datos se han guardado con exito en la base de datos.");
            session.close();
            
            actualizarTabla();
            CantidadTotal();
        }
        if(accion.equals("Modificar")){
            producto.setIdProducto( new Integer(this.txtCodigo.getText()) );
            
            Session session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            session.update(producto);
            session.getTransaction().commit();
            //JOptionPane.showMessageDialog(null, "Los datos se han guardado con exito en la base de datos.");
            session.close();
            
            actualizarTabla();
            modificar();
            limpiarCampos();
            CantidadTotal();
        }
        CrearTabla();
        mirar();
        tabProducto.setSelectedIndex(tabProducto.indexOfComponent(pBuscar));
    
    }  
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductoMouseClicked
        int fila;
        DefaultTableModel defaultTableModel;// = new DefaultTableModel();
        fila = tblProducto.getSelectedRow();

        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            /*
            defaultTableModel = (DefaultTableModel)tblProducto.getModel();
            strCodigo =  ((String) defaultTableModel.getValueAt(fila, 0));
            txtCodigo.setText((String)defaultTableModel.getValueAt(fila,0));
            txtCodigoBar.setText((String)defaultTableModel.getValueAt(fila,1));
            txtNombre.setText((String)defaultTableModel.getValueAt(fila,2));
            txtDescripcion.setText((String)defaultTableModel.getValueAt(fila,3));
            txtStock.setText((String)defaultTableModel.getValueAt(fila,4));
            txtStockMin.setText((String)defaultTableModel.getValueAt(fila,5));
            txtPrecioCosto.setText((String)defaultTableModel.getValueAt(fila,6));
            txtPrecioVenta.setText((String)defaultTableModel.getValueAt(fila,7));
            //txtPrecioVenta2.setText((String)defaultTableModel.getValueAt(fila,8));
            txtUtilidad.setText((String)defaultTableModel.getValueAt(fila,8));
            if ("ACTIVO".equals((String) defaultTableModel.getValueAt(fila,9))){
               rbtnActivo.setSelected(true);
            }else if ("INACTIVO".equals((String) defaultTableModel.getValueAt(fila,9))){
               rbtnInactivo.setSelected(true);
            }
            cboCategoria.setSelectedItem((String)defaultTableModel.getValueAt(fila,10));
            
            this.txtDescuentoProveedor.setText( (String)defaultTableModel.getValueAt(fila,11) );
            this.txtDescuentoAplicable.setText( (String)defaultTableModel.getValueAt(fila,12) );
            */
            
            ClsEntidadProductoHib producto = this.productos.get(fila);
            
            strCodigo =  producto.getIdProducto().toString();
            txtCodigo.setText(producto.getIdProducto().toString());
            txtCodigoBar.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtStock.setText(producto.getStock().toString());
            txtStockMin.setText(producto.getStockMin().toString());
            txtPrecioCosto.setText(producto.getPrecioCosto().toString());
            txtPrecioVenta.setText(producto.getPrecioVenta().toString());
            txtUtilidad.setText(producto.getUtilidad().toString());
            if ( "ACTIVO".equals( producto.getEstado() ) ){
               rbtnActivo.setSelected(true);
            }else if ("INACTIVO".equals( producto.getEstado() )){
               rbtnInactivo.setSelected(true);
            }
            cboCategoria.setSelectedItem( producto.getIdCategoria().getDescripcion() );
            
            this.txtDescuentoProveedor.setText( String.valueOf(producto.getDescuentoProveedor()) );
            this.txtDescuentoAplicable.setText( String.valueOf(producto.getDescuentoVenta()) );
            
            this.cboMoneda.setSelectedItem( producto.getMoneda().getNombre() );
            
            if(producto.getProveedor() != null){
                this.cboProveedor.setSelectedItem( producto.getProveedor().getNombre() );
            }
            

        }

        mirar();
        calcularValoresAdicionales();
    }//GEN-LAST:event_tblProductoMouseClicked

    private void txtCodigoBarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarKeyTyped
        txtCodigoBar.setBackground(Color.WHITE);
        //        char car = evt.getKeyChar();
        //        if((car<'a' || car>'z') && (car<'A' || car>'Z')) evt.consume();
    }//GEN-LAST:event_txtCodigoBarKeyTyped

    private void txtPrecioCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCostoKeyReleased
        calcularValoresAdicionales();
        //CalcularUtilidad();
        
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtPrecioVenta.requestFocus();  
    }//GEN-LAST:event_txtPrecioCostoKeyReleased

    private void txtPrecioVentaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyReleased
        calcularValoresAdicionales();
        //CalcularUtilidad();
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) btnGuardar.requestFocus();  
    }//GEN-LAST:event_txtPrecioVentaKeyReleased
       void verificarCodigoBar(){
        String busqueda=null;
        int sen = 2;
        busqueda=txtCodigoBar.getText(); 
        
            try{
                ClsProducto oProducto=new ClsProducto();
                
                rs= oProducto.verificarCodigoBar(busqueda);
                while (rs.next()) {
                    if(!rs.getString(2).equals("")) {
                               
                       sen=1;
                    }else{

                       sen=2;
                    }
                   break;
                }
 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
                System.out.println(ex.getMessage());
            }
            
            if(sen==1){
                JOptionPane.showMessageDialog(null, "Codigo No Disponible");
            }else if (sen==2){
                JOptionPane.showMessageDialog(null, "Codigo Disponible");
            }else if(rs==null){
                JOptionPane.showMessageDialog(null, "no hay");
            }
    
    }
    void GeneraAleatorio(){

        String codbar;
        int sen = 2;
        DefaultListModel modelo = new DefaultListModel();
      
        for(int i = 1; i<=4; i++){
            codbar=String.valueOf((int)(Math.random()*(500000-100000+1)+100000));
            //codbar=String.valueOf((int)(Math.random()*(9-1+1)+1));    
            try{
                ClsProducto oProducto=new ClsProducto();
                
                rs= oProducto.verificarCodigoBar(codbar);
                while (rs.next()) {
                    if(!rs.getString(2).equals("")) {
                               
                       sen=1;
                    }else{

                       sen=2;
                    }
                   break;
                }
 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage());
                System.out.println(ex.getMessage());
            }
            
            if(sen==1){
                //JOptionPane.showMessageDialog(null, "Codigo No Disponible");
                i=i-1;
                sen=2;
            }else if (sen==2){
                //JOptionPane.showMessageDialog(null, "Codigo Disponible");
                modelo.addElement(codbar);
                //sen=2;
                
            }
        
            

        }
      
        lstCodigos.setModel(modelo);
    }
    void limpiarList(){
        DefaultListModel model = new DefaultListModel();
        lstCodigos.setModel(model);
    }

    
    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
    String codigobar;
    ImageIcon imagen=null;
    codigobar=txtCodigoBar.getText();
        File file=new File("C:\\001.jpg");
        
        Barcode barcode = null;   
        try {   
            if(cboTipoCodificacion.getSelectedItem().equals("Code 128")){
                barcode = BarcodeFactory.createCode128(codigobar);  
            }else if(cboTipoCodificacion.getSelectedItem().equals("Code 128A")){
                barcode = BarcodeFactory.createCode128A(codigobar);
            }else if(cboTipoCodificacion.getSelectedItem().equals("Code 128B")){
                barcode = BarcodeFactory.createCode128B(codigobar); 
            }
             
            barcode.setBarHeight(60);   
            barcode.setBarWidth(1);
            barcode.setDrawingText(false);


            BarcodeImageHandler.saveJPEG(barcode,file);

        } catch(BarcodeException e) {  
            e.printStackTrace();
        } catch(OutputException e) {  
            e.printStackTrace();
        }
        imagen = new ImageIcon("C:\\001.jpg");
        imagen.getImage().flush();
        lblprueba.setIcon(imagen);

        
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
      GeneraAleatorio();


    }//GEN-LAST:event_btnActualizarActionPerformed

    private void lstCodigosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstCodigosMouseClicked
        //int seleccion = lstCodigos.getSelectedIndex();
        //txtCodigoBar.setText(String.valueOf(seleccion));
        txtCodigoBar.setText(lstCodigos.getSelectedValue().toString());
        
    }//GEN-LAST:event_lstCodigosMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Map p=new HashMap();
        p.put("busqueda",txtBusqueda.getText());
        /*
        if(rbtnCodigo.isSelected()){
            p.put("criterio", "codigo");
        }
        else if(rbtnNombre.isSelected()){
            p.put("criterio", "nombre");
        }else if(rbtnDescripcion.isSelected()){
            p.put("criterio", "descripcion");
        }else if(rbtnCategoria.isSelected()){
            p.put("criterio", "categoria");
        }else{
            p.put("criterio", "");
        }*/
        p.put("criterio", "");
        JasperReport report;
        JasperPrint print;
        try{
            report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptProducto.jrxml");
            print=JasperFillManager.fillReport(report, p,connection);
            JasperViewer view=new JasperViewer(print,false);
            view.setTitle("Reporte General de Productos");
            view.setVisible(true);
        }catch(JRException e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        txtNombre.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyTyped
        txtStock.setBackground(Color.WHITE);        
    }//GEN-LAST:event_txtStockKeyTyped

    private void txtPrecioVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioVentaKeyTyped
        txtPrecioVenta.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtPrecioVentaKeyTyped

    private void txtCodigoBarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtNombre.requestFocus();        
    }//GEN-LAST:event_txtCodigoBarKeyReleased

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtDescripcion.requestFocus();         
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtStockKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtStockMin.requestFocus();        
    }//GEN-LAST:event_txtStockKeyReleased

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        Map p=new HashMap();
        String cod=txtCodigoBar.getText();
        p.put("codigo",cod);

        JasperReport report;
        JasperPrint print;
        if(cboTipoCodificacion.getSelectedItem().equals("Code 128")){
            try{
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptProducto_Code128.jrxml");
                print=JasperFillManager.fillReport(report, p,new JREmptyDataSource());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Código de Barras - CODE128");
                view.setVisible(true);
            }catch(JRException e){
                e.printStackTrace();
            }
         }else if(cboTipoCodificacion.getSelectedItem().equals("Code 128A")){
            try{
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptProducto_Code128A.jrxml");
                print=JasperFillManager.fillReport(report, p,new JREmptyDataSource());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Código de Barras - CODE128A");
                view.setVisible(true);
            }catch(JRException e){
                e.printStackTrace();
            }
         }else if(cboTipoCodificacion.getSelectedItem().equals("Code 128B")){
            try{
                report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptProducto_Code128B.jrxml");
                print=JasperFillManager.fillReport(report, p,new JREmptyDataSource());
                JasperViewer view=new JasperViewer(print,false);
                view.setTitle("Código de Barras - CODE128B");
                view.setVisible(true);
            }catch(JRException e){
                e.printStackTrace();
            }
         }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void txtStockMinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockMinKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtPrecioCosto.requestFocus();
    }//GEN-LAST:event_txtStockMinKeyReleased

    private void txtDescuentoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoProveedorActionPerformed

    private void txtUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUtilidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUtilidadActionPerformed

    private void txtDescuentoProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoProveedorKeyReleased
        // TODO add your handling code here:
        calcularValoresAdicionales();
        //CalcularUtilidad();
    }//GEN-LAST:event_txtDescuentoProveedorKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int[] selectedRows = this.tblProducto.getSelectedRows();
        
        int respuesta = JOptionPane.showConfirmDialog(FrmPrincipal.getInstance(), "<html>Estas seguro que deseas desactivar los productos seleccionados?<br>los productos desactivados no aparecen en las nuevas operaciones<html>");
        
        if(respuesta != JOptionPane.OK_OPTION){
            return;
        }
        
        for(int i : selectedRows){
            ClsEntidadProductoHib producto = this.productos.get(i);
            this.productoDao.desactivarProducto(producto);
        }
        
        this.actualizarTabla();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.BuscarProducto();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void chkInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkInactivosMouseClicked
        // TODO add your handling code here:
        this.actualizarTabla();
    }//GEN-LAST:event_chkInactivosMouseClicked
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cboCategoria;
    private javax.swing.JComboBox cboMoneda;
    private javax.swing.JComboBox cboProveedor;
    private javax.swing.JComboBox cboTipoCodificacion;
    private javax.swing.JCheckBox chkInactivos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblprueba;
    private javax.swing.JList lstCodigos;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JPanel panel;
    private javax.swing.JRadioButton rbtnActivo;
    private javax.swing.JRadioButton rbtnInactivo;
    private javax.swing.JTabbedPane tabProducto;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoBar;
    private javax.swing.JTextField txtCostoConDescuento;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtDescuentoAplicable;
    private javax.swing.JTextField txtDescuentoProveedor;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecioCosto;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtStockMin;
    private javax.swing.JTextField txtUtilidad;
    // End of variables declaration//GEN-END:variables

    private void cargarComboMoneda() {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        monedas = session.createQuery("FROM ClsEntidadMonedaHib").list();
        
        DefaultComboBoxModel monedaModel = new DefaultComboBoxModel();
        
        for(ClsEntidadMonedaHib moneda : monedas ){
            monedaModel.addElement(moneda.getNombre());
        }
        
        this.cboMoneda.setModel(monedaModel);
        
        session.getTransaction().commit();
        session.close();
        
    }

    private void cargarComboProveedor() {
        this.proveedores = this.proveedoresDao.getAllProveedores();
        
        DefaultComboBoxModel proveedorModel = new DefaultComboBoxModel();
        
        for(ClsEntidadProveedorHib proveedor : this.proveedores ){
            proveedorModel.addElement(proveedor.getNombre());
        }
        
        this.cboProveedor.setModel(proveedorModel);
    }
}
