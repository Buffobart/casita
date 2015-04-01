/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Conexion.HibernateUtil;
import Entidad.*;
import Negocio.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.hibernate.Query;
import org.hibernate.Session;

public class FrmTipoUsuario extends javax.swing.JInternalFrame {

    String Total;
    String strCodigo;
    String accion;
    //---------------------Privilegios--------------------
    String p_venta="0",p_compra="0",p_producto="0",p_proveedor="0",p_empleado="0";
    String p_cliente="0",p_categoria="0",p_tipodoc="0",p_tipouser="0",p_anularv="0",p_anularc="0";
    String p_estadoprod="0",p_ventare="0",p_ventade="0",p_estadistica="0",p_comprare="0",p_comprade="0",p_pass="0",p_respaldar="0",p_restaurar="0";
    String p_caja="0",p_cuentas="0";
    //----------------------------------------------------
    int registros;
    String id[]=new String[50];
    static int intContador;
    
    //-----------------------------------------------
    public String codigo;
    static Connection conn=null;
    static ResultSet rs=null;
    DefaultTableModel dtm=new DefaultTableModel();
    String criterio,busqueda;
    
    private List<ClsEntidadTipousuarioHib> tipos;
    
    public FrmTipoUsuario() {
        initComponents();
        tabTipoUsuario.setIconAt(tabTipoUsuario.indexOfComponent(pBuscar), new ImageIcon("src/iconos/busca_p1.png"));
        tabTipoUsuario.setIconAt(tabTipoUsuario.indexOfComponent(pNuevo), new ImageIcon("src/iconos/nuevo1.png"));
        buttonGroup1.add(rbtnCodigo);
        buttonGroup1.add(rbtnDescripcion);
        mirar();
        actualizarTabla();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(654, 426);
        CrearTabla();
        CantidadTotal();
    }

//-----------------------------------------------------------------------------------------------
//--------------------------------------METODOS--------------------------------------------------
//-----------------------------------------------------------------------------------------------
  void CrearTabla(){
   //--------------------PRESENTACION DE JTABLE----------------------
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0){
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
        for (int i=0;i<tblTipoUsuario.getColumnCount();i++){
            tblTipoUsuario.getColumnModel().getColumn(i).setCellRenderer(render);
        }
      
        //Activar ScrollBar
        tblTipoUsuario.setAutoResizeMode(tblTipoUsuario.AUTO_RESIZE_OFF);

        //Anchos de cada columna
        int[] anchos = {50,180,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80,80};
        for(int i = 0; i < tblTipoUsuario.getColumnCount(); i++) {
            tblTipoUsuario.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
    }
   void CantidadTotal(){
        Total= String.valueOf(tblTipoUsuario.getRowCount());   
        lblEstado.setText("Se cargaron " + Total + " registros");      
   }
   void limpiarCampos(){
       txtCodigo.setText("");
       txtDescripcion.setText(""); 
       chkRProducto.isSelected();
       
       rbtnCodigo.setSelected(false);
       rbtnDescripcion.setSelected(false);
       chkRVenta.setSelected(false);
       chkRCompra.setSelected(false);
       chkRProducto.setSelected(false);
       chkRProveedor.setSelected(false);
       chkREmpleado.setSelected(false);
       chkRCliente.setSelected(false);
       chkRCategoria.setSelected(false);
       chkRTipodoc.setSelected(false);
       chkRTipouser.setSelected(false);
       chkAnularv.setSelected(false);
       chkAnularc.setSelected(false);
       chkEstado.setSelected(false);
       chkVentare.setSelected(false);
       chkVentade.setSelected(false);
       chkEstadistica.setSelected(false);
       chkComprare.setSelected(false);
       chkComprade.setSelected(false);
       chkPass.setSelected(false);
       chkRespaldar.setSelected(false);
       chkRestaurar.setSelected(false);
       chkCaja.setSelected(false);
       chkCuentas.setSelected(false);
       this.chkDescuento.setSelected(false);
       
       txtBusqueda.setText("");
   }
    
        
   void mirar(){
        tblTipoUsuario.setEnabled(true);
        btnNuevo.setEnabled(true);
        btnModificar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnSalir.setEnabled(true);
        
        txtDescripcion.setEnabled(false);
   
    }
   
    void modificar(){
        tblTipoUsuario.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(false);
        
        txtDescripcion.setEnabled(true);
        txtDescripcion.requestFocus();
    }
    
    private DefaultTableModel buildTableDataModel( List<ClsEntidadTipousuarioHib> tipos ){
        String titulos[]={"ID","Descripción","P. venta","P. compra","P. producto","P. proveedor","P. empleado","P. cliente","P. categoria","P. tipodoc","P. tipouser","P. anularv","P. anularc",
           "P. estadoprod","P. ventare ","P. ventade","P. estadist.","P. comprare","P. comprade","P. pass","P. respaldar","P. restaurar","P. caja","P. cuentas", "P. descuentos"};
        
        DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
        
        String fila[]=new String[titulos.length];
        for(ClsEntidadTipousuarioHib tipo : tipos){
            fila[0]=null == tipo.getIdTipoUsuario() ? "0" : tipo.getIdTipoUsuario().toString();//getStrIdTipoUsuario();
            fila[1]=null == tipo.getDescripcion() ? "0" : tipo.getDescripcion();//TipoUsuario.getStrDescripcionTipoUsuario();
            fila[2]=null == tipo.getPVenta() ? "0" : tipo.getPVenta().toString();//TipoUsuario.getStrP_Venta();  
            fila[3]=null == tipo.getPCompra() ? "0" : tipo.getPCompra().toString();//TipoUsuario.getStrP_Compra();  
            fila[4]=null == tipo.getPProducto() ? "0" : tipo.getPProducto().toString();//TipoUsuario.getStrP_Producto();  
            fila[5]=null == tipo.getPProveedor() ? "0" : tipo.getPProveedor().toString();//TipoUsuario.getStrP_Proveedor();  
            fila[6]=null == tipo.getPEmpleado() ? "0" : tipo.getPEmpleado().toString();//TipoUsuario.getStrP_Empleado();  
            fila[7]=null == tipo.getPCliente() ? "0" : tipo.getPCliente().toString();//TipoUsuario.getStrP_Cliente();  
            fila[8]=null == tipo.getPCategoria() ? "0" : tipo.getPCategoria().toString();//TipoUsuario.getStrP_Categoria();  
            fila[9]=null == tipo.getPTipodoc() ? "0" : tipo.getPTipodoc().toString();//TipoUsuario.getStrP_Tipodoc();  
            fila[10]=null == tipo.getPTipouser() ? "0" : tipo.getPTipouser().toString();//TipoUsuario.getStrP_Tipouser();  
            fila[11]=null == tipo.getPAnularv() ? "0" : tipo.getPAnularv().toString();//TipoUsuario.getStrP_Anularv();  
            fila[12]=null == tipo.getPAnularc() ? "0" : tipo.getPAnularc().toString();//TipoUsuario.getStrP_Anularc();
            fila[13]=null == tipo.getPEstadoprod() ? "0" : tipo.getPEstadoprod().toString();//TipoUsuario.getStrP_Estadoprod(); 
            fila[14]=null == tipo.getPVentare() ? "0" : tipo.getPVentare().toString();//TipoUsuario.getStrP_Ventare(); 
            fila[15]=null == tipo.getPVentade() ? "0" : tipo.getPVentade().toString();//TipoUsuario.getStrP_Ventade(); 
            fila[16]=null == tipo.getPEstadistica() ? "0" : tipo.getPEstadistica().toString();//TipoUsuario.getStrP_Estadistica(); 
            fila[17]=null == tipo.getPComprare() ? "0" : tipo.getPComprare().toString();//TipoUsuario.getStrP_Comprare(); 
            fila[18]=null == tipo.getPComprade() ? "0" : tipo.getPComprade().toString();//TipoUsuario.getStrP_Comprade(); 
            fila[19]=null == tipo.getPPass() ? "0" : tipo.getPPass().toString();//TipoUsuario.getStrP_Pass(); 
            fila[20]=null == tipo.getPRespaldar() ? "0" : tipo.getPRespaldar().toString();//TipoUsuario.getStrP_Respaldar(); 
            fila[21]=null == tipo.getPRestaurar() ? "0" : tipo.getPRestaurar().toString();//TipoUsuario.getStrP_Restaurar(); 
            fila[22]=null == tipo.getPCaja() ? "0" : tipo.getPCaja().toString();//TipoUsuario.getStrP_Caja();
            fila[23]=null == tipo.getPCuentas() ? "0" : tipo.getPCuentas().toString();//TipoUsuario.getStrP_Cuentas();
            fila[24]=null == tipo.getPDescuento() ? "0" : tipo.getPDescuento().toString();

            defaultTableModel.addRow(fila);
        }
        
        return defaultTableModel;
    }
   
    void actualizarTabla(){
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM ClsEntidadTipousuarioHib");
        this.tipos = query.list();
        session.getTransaction().commit();
        session.close();
        
        DefaultTableModel model = this.buildTableDataModel(this.tipos);

        tblTipoUsuario.setModel(model);
   }
   void BuscarTipoUsuario(){
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        Query query = null;
        
        busqueda=txtBusqueda.getText();
        if(rbtnCodigo.isSelected()){
            criterio="id";
            query = session.createQuery("FROM ClsEntidadTipousuarioHib WHERE IdTipoUsuario LIKE :id");
            query.setParameter("id", "%"+busqueda+"%");
        }else if(rbtnDescripcion.isSelected()){
            criterio="descripcion";
            query = session.createQuery("FROM ClsEntidadTipousuarioHib WHERE Descripcion LIKE :descripcion");
            query.setParameter("descripcion", "%"+busqueda+"%");
        }
        
        this.tipos = query.list();
        session.getTransaction().commit();
        session.close();
        
        DefaultTableModel model = this.buildTableDataModel(this.tipos);

        tblTipoUsuario.setModel(model);
    }
   
    void listarDatos(){
        //DefaultTableModel defaultTableModel = new DefaultTableModel();
        int fila = tblTipoUsuario.getSelectedRow();

        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            
            ClsEntidadTipousuarioHib tipo = this.tipos.get(fila);
            this.txtCodigo.setText(tipo.getIdTipoUsuario().toString());
            this.txtDescripcion.setText(tipo.getDescripcion());
            
            chkRVenta.setSelected( tipo.getPVenta() != null && tipo.getPVenta() != 0 );
            chkRCompra.setSelected( tipo.getPCompra() != null && tipo.getPCompra()!= 0 );
            chkRProducto.setSelected( tipo.getPProducto() != null && tipo.getPProducto()!= 0 );
            chkRProveedor.setSelected( tipo.getPProveedor() != null && tipo.getPProveedor()!= 0 );
            chkREmpleado.setSelected( tipo.getPEmpleado() != null && tipo.getPEmpleado()!= 0 );
            chkRCliente.setSelected( tipo.getPCliente() != null && tipo.getPCliente()!= 0 );
            chkRCategoria.setSelected( tipo.getPCategoria() != null && tipo.getPCategoria()!= 0 );
            chkRTipodoc.setSelected( tipo.getPTipodoc() != null && tipo.getPTipodoc()!= 0 );
            chkRTipouser.setSelected( tipo.getPTipouser() != null && tipo.getPTipouser()!= 0 );
            chkAnularv.setSelected( tipo.getPAnularv() != null && tipo.getPAnularv()!= 0 );
            chkAnularc.setSelected( tipo.getPAnularc() != null && tipo.getPAnularc()!= 0 );
            chkEstado.setSelected( tipo.getPEstadoprod() != null && tipo.getPEstadoprod()!= 0 );
            chkVentare.setSelected( tipo.getPVentare() != null && tipo.getPVentare()!= 0 );
            chkVentade.setSelected( tipo.getPVentade() != null && tipo.getPVentade()!= 0 );
            chkEstadistica.setSelected( tipo.getPEstadistica() != null && tipo.getPEstadistica()!= 0 );
            chkComprare.setSelected( tipo.getPComprare() != null && tipo.getPComprare()!= 0 );
            chkComprade.setSelected( tipo.getPComprade() != null && tipo.getPComprade()!= 0 );
            chkPass.setSelected( tipo.getPPass() != null && tipo.getPPass()!= 0 );
            chkRespaldar.setSelected( tipo.getPRespaldar() != null && tipo.getPRespaldar()!= 0 );
            chkRestaurar.setSelected( tipo.getPRestaurar() != null && tipo.getPRestaurar()!= 0 );
            chkCaja.setSelected( tipo.getPCaja() != null && tipo.getPCaja()!= 0 );
            chkCuentas.setSelected( tipo.getPCuentas() != null && tipo.getPCuentas()!= 0 );
            chkDescuento.setSelected( tipo.getPDescuento() != null && tipo.getPDescuento()!= 0 );
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tabTipoUsuario = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        rbtnCodigo = new javax.swing.JRadioButton();
        rbtnDescripcion = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTipoUsuario = new javax.swing.JTable();
        pNuevo = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        chkEstadistica = new javax.swing.JCheckBox();
        chkComprare = new javax.swing.JCheckBox();
        chkComprade = new javax.swing.JCheckBox();
        chkPass = new javax.swing.JCheckBox();
        chkRespaldar = new javax.swing.JCheckBox();
        chkRestaurar = new javax.swing.JCheckBox();
        chkRTipodoc = new javax.swing.JCheckBox();
        chkRTipouser = new javax.swing.JCheckBox();
        chkAnularv = new javax.swing.JCheckBox();
        chkAnularc = new javax.swing.JCheckBox();
        chkEstado = new javax.swing.JCheckBox();
        chkVentare = new javax.swing.JCheckBox();
        chkVentade = new javax.swing.JCheckBox();
        chkRVenta = new javax.swing.JCheckBox();
        chkRCompra = new javax.swing.JCheckBox();
        chkRProducto = new javax.swing.JCheckBox();
        chkRProveedor = new javax.swing.JCheckBox();
        chkREmpleado = new javax.swing.JCheckBox();
        chkRCliente = new javax.swing.JCheckBox();
        chkRCategoria = new javax.swing.JCheckBox();
        chkCaja = new javax.swing.JCheckBox();
        chkCuentas = new javax.swing.JCheckBox();
        chkDescuento = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro del Tipo de Usuario");
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
        btnModificar.setBounds(540, 170, 81, 70);

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
        btnCancelar.setBounds(540, 240, 81, 70);

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
        btnNuevo.setBounds(540, 30, 81, 70);

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
        btnSalir.setBounds(540, 310, 81, 70);

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
        btnGuardar.setBounds(540, 100, 81, 70);

        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mantenimiento"));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(530, 10, 100, 380);

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pBuscar.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 200, 20));

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        pBuscar.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 310, -1));

        rbtnCodigo.setText("ID Tipo de Usuario");
        rbtnCodigo.setOpaque(false);
        rbtnCodigo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnCodigoStateChanged(evt);
            }
        });
        pBuscar.add(rbtnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 130, -1));

        rbtnDescripcion.setText("Descripción");
        rbtnDescripcion.setOpaque(false);
        rbtnDescripcion.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnDescripcionStateChanged(evt);
            }
        });
        pBuscar.add(rbtnDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 100, -1));

        jLabel5.setBackground(new java.awt.Color(223, 223, 223));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criterios de Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jLabel5.setOpaque(true);
        pBuscar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 490, 80));

        tblTipoUsuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTipoUsuario.setRowHeight(22);
        tblTipoUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTipoUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTipoUsuario);

        pBuscar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 490, 230));

        tabTipoUsuario.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos del Tipo de Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        pNuevo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 11, 490, 50));

        txtCodigo.setEnabled(false);
        pNuevo.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 50, -1));

        jLabel3.setText("ID Tipo de Usuario:");
        pNuevo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 100, 20));

        jLabel2.setText("Descripción:");
        pNuevo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 70, 20));

        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        pNuevo.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 200, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Roles del Empleado [Operaciones que puede realizar el empleado en el sistema]", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        chkEstadistica.setText("Info. Estadística");
        chkEstadistica.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEstadisticaStateChanged(evt);
            }
        });
        jPanel1.add(chkEstadistica, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        chkComprare.setText("Info. Compra realizada");
        chkComprare.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkComprareStateChanged(evt);
            }
        });
        jPanel1.add(chkComprare, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, -1, -1));

        chkComprade.setText("Info. Compra detallada");
        chkComprade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkCompradeStateChanged(evt);
            }
        });
        jPanel1.add(chkComprade, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, -1, -1));

        chkPass.setText("Cambiar Contraseña");
        chkPass.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkPassStateChanged(evt);
            }
        });
        jPanel1.add(chkPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, -1, -1));

        chkRespaldar.setText("Respaldar DB");
        chkRespaldar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRespaldarStateChanged(evt);
            }
        });
        jPanel1.add(chkRespaldar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, -1, -1));

        chkRestaurar.setText("Restaurar DB");
        chkRestaurar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRestaurarStateChanged(evt);
            }
        });
        jPanel1.add(chkRestaurar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, -1, -1));

        chkRTipodoc.setText("Reg. Tipo de Documento");
        chkRTipodoc.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRTipodocStateChanged(evt);
            }
        });
        jPanel1.add(chkRTipodoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        chkRTipouser.setText("Reg. Tipo de Usuario");
        chkRTipouser.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRTipouserStateChanged(evt);
            }
        });
        jPanel1.add(chkRTipouser, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, -1, -1));

        chkAnularv.setText("Anular Venta");
        chkAnularv.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkAnularvStateChanged(evt);
            }
        });
        jPanel1.add(chkAnularv, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, -1));

        chkAnularc.setText("Anular Compra");
        chkAnularc.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkAnularcStateChanged(evt);
            }
        });
        jPanel1.add(chkAnularc, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, -1, -1));

        chkEstado.setText("Info. Estado");
        chkEstado.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkEstadoStateChanged(evt);
            }
        });
        jPanel1.add(chkEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 150, -1, -1));

        chkVentare.setText("Info. Venta realizada");
        chkVentare.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkVentareStateChanged(evt);
            }
        });
        jPanel1.add(chkVentare, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, -1, -1));

        chkVentade.setText("Info. Venta detallada");
        chkVentade.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkVentadeStateChanged(evt);
            }
        });
        jPanel1.add(chkVentade, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, -1, -1));

        chkRVenta.setText("Reg. Venta");
        chkRVenta.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRVentaStateChanged(evt);
            }
        });
        jPanel1.add(chkRVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        chkRCompra.setText("Reg. Compra");
        chkRCompra.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRCompraStateChanged(evt);
            }
        });
        jPanel1.add(chkRCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        chkRProducto.setText("Reg. Producto");
        chkRProducto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRProductoStateChanged(evt);
            }
        });
        jPanel1.add(chkRProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        chkRProveedor.setText("Reg. Proveedor");
        chkRProveedor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRProveedorStateChanged(evt);
            }
        });
        jPanel1.add(chkRProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        chkREmpleado.setText("Reg. Empleado");
        chkREmpleado.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkREmpleadoStateChanged(evt);
            }
        });
        jPanel1.add(chkREmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        chkRCliente.setText("Reg. Cliente");
        chkRCliente.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRClienteStateChanged(evt);
            }
        });
        jPanel1.add(chkRCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        chkRCategoria.setText("Reg. Categoria");
        chkRCategoria.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkRCategoriaStateChanged(evt);
            }
        });
        jPanel1.add(chkRCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        chkCaja.setText("Info. Caja");
        chkCaja.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkCajaStateChanged(evt);
            }
        });
        jPanel1.add(chkCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, -1, -1));

        chkCuentas.setText("Mod. Cuentas");
        chkCuentas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkCuentasStateChanged(evt);
            }
        });
        jPanel1.add(chkCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        chkDescuento.setText("Autorizar Descuentos Sup.");
        jPanel1.add(chkDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, -1, -1));

        pNuevo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, 490, 280));

        tabTipoUsuario.addTab("Nuevo / Modificar", pNuevo);

        getContentPane().add(tabTipoUsuario);
        tabTipoUsuario.setBounds(10, 10, 520, 380);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTipoUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTipoUsuarioMouseClicked
        this.listarDatos();
        mirar();
    }//GEN-LAST:event_tblTipoUsuarioMouseClicked

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z')) evt.consume();
        txtDescripcion.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
    if(tblTipoUsuario.getSelectedRows().length > 0 ) { 
        accion="Modificar";
        modificar();
        tabTipoUsuario.setSelectedIndex(tabTipoUsuario.indexOfComponent(pNuevo));
        
    }else{
        JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
    }
          
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        tabTipoUsuario.setSelectedIndex(tabTipoUsuario.indexOfComponent(pBuscar));

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion="Nuevo";
        modificar();
        limpiarCampos();
        tabTipoUsuario.setSelectedIndex(tabTipoUsuario.indexOfComponent(pNuevo));
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed
//----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos(){
        if (txtDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese una descripción");
            txtDescripcion.requestFocus();
            txtDescripcion.setBackground(Color.YELLOW);
            return false;
        }else{
            return true;
        }

    }
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    if(validardatos()==true){
        
        ClsEntidadTipousuarioHib tipo = new ClsEntidadTipousuarioHib();
        tipo.setDescripcion( txtDescripcion.getText() );
            
        tipo.setPVenta( this.chkRVenta.isSelected() ? 1 : 0 );//tipousuario.setStrP_Venta(p_venta);
        tipo.setPCompra( this.chkRCompra.isSelected() ? 1 : 0 );//tipousuario.setStrP_Compra(p_compra);
        tipo.setPProducto( this.chkRProducto.isSelected() ? 1 : 0 );//tipousuario.setStrP_Producto(p_producto);
        tipo.setPProveedor( this.chkRProveedor.isSelected() ? 1 : 0 );// tipousuario.setStrP_Proveedor(p_proveedor);
        tipo.setPEmpleado( this.chkREmpleado.isSelected() ? 1 : 0 );//tipousuario.setStrP_Empleado(p_empleado);
        tipo.setPCliente( this.chkRCliente.isSelected() ? 1 : 0 );//tipousuario.setStrP_Cliente(p_cliente);
        tipo.setPCategoria( this.chkRCategoria.isSelected() ? 1 : 0 );//tipousuario.setStrP_Categoria(p_categoria);
        tipo.setPTipodoc( this.chkRTipodoc.isSelected() ? 1 : 0 );//tipousuario.setStrP_Tipodoc(p_tipodoc);
        tipo.setPTipouser( this.chkRTipouser.isSelected() ? 1 : 0 );//tipousuario.setStrP_Tipouser(p_tipouser);
        tipo.setPAnularv( this.chkAnularv.isSelected() ? 1 : 0 );//tipousuario.setStrP_Anularv(p_anularv);
        tipo.setPAnularc( this.chkAnularc.isSelected() ? 1 : 0 );//tipousuario.setStrP_Anularc(p_anularc);
        tipo.setPEstadoprod( this.chkEstado.isSelected() ? 1 : 0 );//tipousuario.setStrP_Estadoprod(p_estadoprod);
        tipo.setPVentare( this.chkVentare.isSelected() ? 1 : 0 );//tipousuario.setStrP_Ventare(p_ventare);
        tipo.setPVentade( this.chkVentade.isSelected() ? 1 : 0 );//tipousuario.setStrP_Ventade(p_ventade);
        tipo.setPEstadistica( this.chkEstadistica.isSelected() ? 1 : 0 );//tipousuario.setStrP_Estadistica(p_estadistica);
        tipo.setPComprare( this.chkComprare.isSelected() ? 1 : 0 );//tipousuario.setStrP_Comprare(p_comprare);
        tipo.setPComprade( this.chkComprade.isSelected() ? 1 : 0 );//tipousuario.setStrP_Comprade(p_comprade);
        tipo.setPPass( this.chkPass.isSelected() ? 1 : 0 );//tipousuario.setStrP_Pass(p_pass);
        tipo.setPRespaldar( this.chkRespaldar.isSelected() ? 1 : 0 );//tipousuario.setStrP_Respaldar(p_respaldar);
        tipo.setPRestaurar( this.chkRestaurar.isSelected() ? 1 : 0 );//tipousuario.setStrP_Restaurar(p_restaurar);
        tipo.setPCaja( this.chkCaja.isSelected() ? 1 : 0 );//tipousuario.setStrP_Caja(p_caja);
        tipo.setPCuentas( this.chkCuentas.isSelected() ? 1 : 0 );//tipousuario.setStrP_Cuentas(p_cuentas);
        tipo.setPDescuento( this.chkDescuento.isSelected() ? 1 : 0 );//tipousuarios.agregarTipoUsuario(tipousuario);
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        if(accion.equals("Nuevo")){
            session.save(tipo);
        }
        if(accion.equals("Modificar")){
            tipo.setIdTipoUsuario( Integer.valueOf(this.txtCodigo.getText()) );
            session.update(tipo);
            limpiarCampos();
            modificar();
        }
        
        session.getTransaction().commit();
        session.close();
        
        actualizarTabla();
        CantidadTotal();
        
        CrearTabla();
        mirar();
        tabTipoUsuario.setSelectedIndex(tabTipoUsuario.indexOfComponent(pBuscar)); 
    }    
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        BuscarTipoUsuario();
        CrearTabla();
        CantidadTotal();
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void rbtnCodigoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnCodigoStateChanged
        txtBusqueda.setText("");
    }//GEN-LAST:event_rbtnCodigoStateChanged

    private void rbtnDescripcionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnDescripcionStateChanged
        txtBusqueda.setText("");
    }//GEN-LAST:event_rbtnDescripcionStateChanged

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
        String cadena= (txtDescripcion.getText()).toUpperCase();
        txtDescripcion.setText(cadena);
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) btnGuardar.requestFocus();
    }//GEN-LAST:event_txtDescripcionKeyReleased

    private void chkRVentaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRVentaStateChanged
        if (chkRVenta.isSelected()){
            p_venta="1";
        }else{
            p_venta="0";
        }
    }//GEN-LAST:event_chkRVentaStateChanged

    private void chkRCompraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRCompraStateChanged
        if (chkRCompra.isSelected()){
            p_compra="1";
        }else{
            p_compra="0";
        }
    }//GEN-LAST:event_chkRCompraStateChanged

    private void chkRProductoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRProductoStateChanged
        if (chkRProducto.isSelected()){
            p_producto="1";
        }else{
            p_producto="0";
        }
    }//GEN-LAST:event_chkRProductoStateChanged

    private void chkRProveedorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRProveedorStateChanged
        if (chkRProveedor.isSelected()){
            p_proveedor="1";
        }else{
            p_proveedor="0";
        }
    }//GEN-LAST:event_chkRProveedorStateChanged

    private void chkREmpleadoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkREmpleadoStateChanged
        if (chkREmpleado.isSelected()){
            p_empleado="1";
        }else{
            p_empleado="0";
        }
    }//GEN-LAST:event_chkREmpleadoStateChanged

    private void chkRClienteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRClienteStateChanged
        if (chkRCliente.isSelected()){
            p_cliente="1";
        }else{
            p_cliente="0";
        }
    }//GEN-LAST:event_chkRClienteStateChanged

    private void chkRCategoriaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRCategoriaStateChanged
        if (chkRCategoria.isSelected()){
            p_categoria="1";
        }else{
            p_categoria="0";
        }
    }//GEN-LAST:event_chkRCategoriaStateChanged

    private void chkRTipodocStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRTipodocStateChanged
        if (chkRTipodoc.isSelected()){
            p_tipodoc="1";
        }else{
            p_tipodoc="0";
        }
    }//GEN-LAST:event_chkRTipodocStateChanged

    private void chkRTipouserStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRTipouserStateChanged
        if (chkRTipouser.isSelected()){
            p_tipouser="1";
        }else{
            p_tipouser="0";
        }
    }//GEN-LAST:event_chkRTipouserStateChanged

    private void chkAnularvStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkAnularvStateChanged
        if (chkAnularv.isSelected()){
            p_anularv="1";
        }else{
            p_anularv="0";
        }
    }//GEN-LAST:event_chkAnularvStateChanged

    private void chkAnularcStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkAnularcStateChanged
        if (chkAnularc.isSelected()){
            p_anularc="1";
        }else{
            p_anularc="0";
        }
    }//GEN-LAST:event_chkAnularcStateChanged

    private void chkEstadoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEstadoStateChanged
        if (chkEstado.isSelected()){
            p_estadoprod="1";
        }else{
            p_estadoprod="0";
        }
    }//GEN-LAST:event_chkEstadoStateChanged

    private void chkVentareStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkVentareStateChanged
        if (chkVentare.isSelected()){
            p_ventare="1";
        }else{
            p_ventare="0";
        }
    }//GEN-LAST:event_chkVentareStateChanged

    private void chkVentadeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkVentadeStateChanged
        if (chkVentade.isSelected()){
            p_ventade="1";
        }else{
            p_ventade="0";
        }
    }//GEN-LAST:event_chkVentadeStateChanged

    private void chkEstadisticaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkEstadisticaStateChanged
        if (chkEstadistica.isSelected()){
            p_estadistica="1";
        }else{
            p_estadistica="0";
        }
    }//GEN-LAST:event_chkEstadisticaStateChanged

    private void chkComprareStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkComprareStateChanged
        if (chkComprare.isSelected()){
            p_comprare="1";
        }else{
            p_comprare="0";
        }
    }//GEN-LAST:event_chkComprareStateChanged

    private void chkCompradeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkCompradeStateChanged
        if (chkComprade.isSelected()){
            p_comprade="1";
        }else{
            p_comprade="0";
        }
    }//GEN-LAST:event_chkCompradeStateChanged

    private void chkPassStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkPassStateChanged
        if (chkPass.isSelected()){
            p_pass="1";
        }else{
            p_pass="0";
        }
    }//GEN-LAST:event_chkPassStateChanged

    private void chkRespaldarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRespaldarStateChanged
        if (chkRespaldar.isSelected()){
            p_respaldar="1";
        }else{
            p_respaldar="0";
        }
    }//GEN-LAST:event_chkRespaldarStateChanged

    private void chkRestaurarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkRestaurarStateChanged
        if (chkRestaurar.isSelected()){
            p_restaurar="1";
        }else{
            p_restaurar="0";
        }
    }//GEN-LAST:event_chkRestaurarStateChanged

    private void chkCajaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkCajaStateChanged
        if (chkCaja.isSelected()){
            p_caja="1";
        }else{
            p_caja="0";
        }
    }//GEN-LAST:event_chkCajaStateChanged

    private void chkCuentasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkCuentasStateChanged
        // TODO add your handling code here:
        if (this.chkCuentas.isSelected()){
            p_cuentas="1";
        }else{
            p_cuentas="0";
        }
    }//GEN-LAST:event_chkCuentasStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox chkAnularc;
    private javax.swing.JCheckBox chkAnularv;
    private javax.swing.JCheckBox chkCaja;
    private javax.swing.JCheckBox chkComprade;
    private javax.swing.JCheckBox chkComprare;
    private javax.swing.JCheckBox chkCuentas;
    private javax.swing.JCheckBox chkDescuento;
    private javax.swing.JCheckBox chkEstadistica;
    private javax.swing.JCheckBox chkEstado;
    private javax.swing.JCheckBox chkPass;
    private javax.swing.JCheckBox chkRCategoria;
    private javax.swing.JCheckBox chkRCliente;
    private javax.swing.JCheckBox chkRCompra;
    private javax.swing.JCheckBox chkREmpleado;
    private javax.swing.JCheckBox chkRProducto;
    private javax.swing.JCheckBox chkRProveedor;
    private javax.swing.JCheckBox chkRTipodoc;
    private javax.swing.JCheckBox chkRTipouser;
    private javax.swing.JCheckBox chkRVenta;
    private javax.swing.JCheckBox chkRespaldar;
    private javax.swing.JCheckBox chkRestaurar;
    private javax.swing.JCheckBox chkVentade;
    private javax.swing.JCheckBox chkVentare;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtnCodigo;
    private javax.swing.JRadioButton rbtnDescripcion;
    private javax.swing.JTabbedPane tabTipoUsuario;
    private javax.swing.JTable tblTipoUsuario;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables
}
