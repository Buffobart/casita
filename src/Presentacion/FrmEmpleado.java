/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Conexion.ClsConexion;
import Entidad.*;
import daos.impl.CuentaDaoImpl;
import daos.impl.TipoUsuarioDaoImpl;
import daos.impl.UsuarioDaoImpl;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class FrmEmpleado extends javax.swing.JInternalFrame {
    private Connection connection=new ClsConexion().getConection();
    //algoritmos
    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";
    
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
    private List<ClsEntidadCuenta> cuentas;
    CuentaDaoImpl cuentasDao = new CuentaDaoImpl();
    UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
    private List<ClsEntidadEmpleadoHib> empleados;
    
    public FrmEmpleado() {
        initComponents();
        //---------------------FECHA ACTUAL-------------------------------
        Date date=new Date();
        //String format=new String("yyyy-MM-dd");
        //SimpleDateFormat formato=new SimpleDateFormat(format);
        txtFechaIng.setDate(date);
        txtFechaNac.setDate(date);
        
        cargarComboTipoUsuario();
        tabEmpleado.setIconAt(tabEmpleado.indexOfComponent(pBuscar), new ImageIcon("src/iconos/busca_p1.png"));
        tabEmpleado.setIconAt(tabEmpleado.indexOfComponent(pNuevo), new ImageIcon("src/iconos/nuevo1.png"));
        buttonGroup1.add(rbtnMasculino);
        buttonGroup1.add(rbtnFemenino);
        buttonGroup2.add(rbtnActivo);
        buttonGroup2.add(rbtnInactivo);
        /*
        buttonGroup3.add(rbtnCodigo);
        buttonGroup3.add(rbtnNombre);
        buttonGroup3.add(rbtnDni);
                */
        mirar();        
        actualizarTabla();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        //this.setSize(737, 439);
        //this.setPreferredSize(null);
        CrearTabla();                
        CantidadTotal();
        //Ocultar Columnas
        //tblEmpleado.removeColumn(tblEmpleado.getColumnModel().getColumn(14));
        
        this.cargarComboCuentas();
        this.cargarComboTipoUsuario();
        
    }

//-----------------------------------------------------------------------------------------------
//--------------------------------------METODOS--------------------------------------------------
//-----------------------------------------------------------------------------------------------
  void CrearTabla(){
   
        int[] anchos = {40, 100, 100, 30};
        for(int i = 0; i < anchos.length; i++) {
            tblEmpleado.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        //Ocultar columnas
        //setOcultarColumnasJTable(tblEmpleado,new int[]{14});
    }
   void CantidadTotal(){
        Total= String.valueOf(tblEmpleado.getRowCount());   
        lblEstado.setText("Se cargaron " + Total + " registros");      
   }
   void limpiarCampos(){
       txtCodigo.setText("");
       txtNombre.setText("");
       txtApellido.setText("");
       txtDireccion.setText("");
       txtTelefono.setText("");
       txtCelular.setText("");
       txtEmail.setText("");
       txtDni.setText("");
       txtSueldo.setText("");
       txtUsuario.setText("");
       txtContraseña.setText("");
       
       rbtnMasculino.setSelected(true);
       rbtnFemenino.setSelected(false);
       rbtnActivo.setSelected(true);
       rbtnInactivo.setSelected(false);
       txtBusqueda.setText("");
   }
    
        
   void mirar(){
        tblEmpleado.setEnabled(true);
        btnNuevo.setEnabled(true);
        btnModificar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnSalir.setEnabled(true);
        
        txtCodigo.setEnabled(false);
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        rbtnMasculino.setEnabled(false);
        rbtnFemenino.setEnabled(false);
        txtFechaNac.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtCelular.setEnabled(false);
        txtEmail.setEnabled(false);
        txtDni.setEnabled(false);
        txtFechaIng.setEnabled(false);
        txtSueldo.setEnabled(false);
        rbtnActivo.setEnabled(false);
        rbtnInactivo.setEnabled(false);
        txtUsuario.setEnabled(false);
        txtContraseña.setEnabled(false);
        cboTipoUsuario.setEnabled(false);
    }
   
    void modificar(){
        tblEmpleado.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnModificar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnSalir.setEnabled(false);
        
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        rbtnMasculino.setEnabled(true);
        rbtnFemenino.setEnabled(true);
        txtFechaNac.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtCelular.setEnabled(true);
        txtEmail.setEnabled(true);
        txtDni.setEnabled(true);
        txtFechaIng.setEnabled(true);
        txtSueldo.setEnabled(true);
        rbtnActivo.setEnabled(true);
        rbtnInactivo.setEnabled(true);
        txtUsuario.setEnabled(true);
        txtContraseña.setEnabled(true);
        cboTipoUsuario.setEnabled(true);
        txtNombre.requestFocus();
    }
   
   
    void actualizarTabla(){
        this.empleados = this.usuarioDao.getAllUsuarios();
        this.setDatosTabla();
   }
    
    private void setDatosTabla(){
        String titulos[]={"ID","Nombre","Apellidos","Sexo","Fecha Nac.","Dirección","Teléfono","Celular","Email","DNI","Fecha Ing.","Sueldo","Estado","Usuario","Tipo de Usuario"};
        DefaultTableModel tableModel = new DefaultTableModel(null, titulos);
        

        for( ClsEntidadEmpleadoHib empleado : this.empleados ){
            Vector rowData = new Vector();
            rowData.add(empleado.getIdEmpleado());
            rowData.add(empleado.getNombre());
            rowData.add(empleado.getApellido());
            rowData.add(empleado.getSexo());
            rowData.add(empleado.getFechaNac());
            rowData.add(empleado.getDireccion());
            rowData.add(empleado.getTelefono());
            rowData.add(empleado.getCelular());
            rowData.add(empleado.getEmail());
            rowData.add(empleado.getDni());
            rowData.add(empleado.getFechaIng());
            rowData.add(empleado.getSueldo());
            rowData.add(empleado.getEstado());
            rowData.add(empleado.getUsuario());
            //rowData.add(empleado.getContrasena());
            rowData.add(empleado.getIdTipoUsuario().getDescripcion());

            tableModel.addRow(rowData);
        }

        this.tblEmpleado.setModel(tableModel);
    }
    
   void BuscarEmpleado(){
       String query = this.txtBusqueda.getText();
       this.empleados = this.usuarioDao.buscar(query);
       
       this.setDatosTabla();
    }
   
    void listardatos(){

        DefaultTableModel defaultTableModel=new DefaultTableModel();
        if(registros==-1){
            JOptionPane.showMessageDialog(null,"¡Se debe seleccionar un registro!");
        }else{
            
            ClsEntidadEmpleadoHib empleado = this.empleados.get(registros);
            
            //defaultTableModel=(DefaultTableModel) tblEmpleado.getModel();
            strCodigo=empleado.getIdEmpleado().toString();//(String) defaultTableModel.getValueAt(registros,0));
            txtCodigo.setText(empleado.getIdEmpleado().toString());//(String)defaultTableModel.getValueAt(registros,0));
            txtNombre.setText(empleado.getNombre());//(String)defaultTableModel.getValueAt(registros,1));
            txtApellido.setText(empleado.getApellido());//(String)defaultTableModel.getValueAt(registros,2));
            if ("M".equals(empleado.getSexo())){
               rbtnMasculino.setSelected(true);
            }else{
               rbtnFemenino.setSelected(true);
            }
            txtFechaNac.setDate( empleado.getFechaNac() );
            txtDireccion.setText( empleado.getDireccion() );
            txtTelefono.setText( empleado.getTelefono() );
            txtCelular.setText( empleado.getCelular() );
            txtEmail.setText( empleado.getEmail() );
            txtDni.setText( empleado.getDni() );
            txtFechaIng.setDate( empleado.getFechaIng() );
            txtSueldo.setText( empleado.getSueldo().toString() );
            if ("ACTIVO".equals( empleado.getEstado() )){
               rbtnActivo.setSelected(true);
            }else{
               rbtnInactivo.setSelected(true);
            }
            txtUsuario.setText( empleado.getUsuario() );
            txtContraseña.setText( empleado.getContrasena() );
            cboTipoUsuario.setSelectedItem( empleado.getIdTipoUsuario().getDescripcion() );
            cboCuentas.setSelectedItem( empleado.getCuenta().getNombre() );
            
            tblEmpleado.setRowSelectionInterval(registros,registros);
        }
    
    }
    
    TipoUsuarioDaoImpl tipoUsuarioDao = new TipoUsuarioDaoImpl();
    List<ClsEntidadTipousuarioHib> tiposUsurio;
    
    void cargarComboTipoUsuario(){
        
        this.tiposUsurio = this.tipoUsuarioDao.getAllTipoUsuario();
        
        DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
        
        for(ClsEntidadTipousuarioHib tipo : this.tiposUsurio ){
            comboModel.addElement(tipo.getDescripcion());
        }
        
        this.cboTipoUsuario.setModel(comboModel);
    }
    
//-----------------------ENCRIPTACION - SHA----------------------------------------
    private static String toHexadecimal(byte[] digest)
    {
        String hash = "";
        for(byte aux : digest) 
        {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }
    
    public static String getStringMessageDigest(String message, String algorithm)
    {
        byte[] digest = null;
        byte[] buffer = message.getBytes();
        try 
        {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        }
        catch (NoSuchAlgorithmException ex) 
        {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tabEmpleado = new javax.swing.JTabbedPane();
        pBuscar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleado = new javax.swing.JTable();
        lblEstado = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        txtBusqueda = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        pNuevo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rbtnMasculino = new javax.swing.JRadioButton();
        rbtnFemenino = new javax.swing.JRadioButton();
        txtFechaNac = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtFechaIng = new com.toedter.calendar.JDateChooser();
        txtTelefono = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDni = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSueldo = new javax.swing.JTextField();
        rbtnActivo = new javax.swing.JRadioButton();
        rbtnInactivo = new javax.swing.JRadioButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        txtUsuario = new javax.swing.JTextField();
        cboTipoUsuario = new javax.swing.JComboBox();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cboCuentas = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro del Empleado");
        setPreferredSize(new java.awt.Dimension(737, 457));
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
        btnModificar.setBounds(620, 170, 81, 70);

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
        btnCancelar.setBounds(620, 240, 81, 70);

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
        btnNuevo.setBounds(620, 30, 81, 70);

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
        btnSalir.setBounds(620, 310, 81, 70);

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
        btnGuardar.setBounds(620, 100, 81, 70);

        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mantenimiento"));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(610, 10, 100, 390);

        pBuscar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblEmpleado.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEmpleado.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmpleadoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEmpleado);

        pBuscar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 570, 240));
        pBuscar.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 200, 20));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/report.png"))); // NOI18N
        jButton3.setText("Reporte");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pBuscar.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 120, 50));
        pBuscar.add(txtBusqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 260, -1));

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        pBuscar.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Busqueda"));
        jLabel5.setOpaque(true);
        pBuscar.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 570, 80));

        tabEmpleado.addTab("Buscar", pBuscar);

        pNuevo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Empleado"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 150, -1));

        txtCodigo.setEnabled(false);
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 70, -1));

        jLabel3.setText("ID Empleado:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 70, 20));

        jLabel2.setText("Nombre:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 50, 20));

        jLabel6.setText("Apellidos:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 60, 20));

        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 150, -1));

        jLabel7.setText("Sexo:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 40, 20));

        rbtnMasculino.setText("Masculino");
        jPanel1.add(rbtnMasculino, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, -1, -1));

        rbtnFemenino.setText("Femenino");
        jPanel1.add(rbtnFemenino, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        txtFechaNac.setDateFormatString("yyyy-MM-dd");
        jPanel1.add(txtFechaNac, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 110, -1));

        jLabel8.setText("Fecha de Nacimiento:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel9.setText("Dirección:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 60, 20));

        jLabel10.setText("Teléfono:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 60, 20));

        jLabel11.setText("Celular:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 50, 20));

        jLabel12.setText("Email:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 40, 20));

        jLabel13.setText("DNI:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 40, 20));

        jLabel14.setText("Fecha de Ingreso:");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 100, 20));

        txtFechaIng.setDateFormatString("yyyy-MM-dd");
        jPanel1.add(txtFechaIng, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 120, -1));
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 150, -1));
        jPanel1.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 150, -1));
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 180, -1));

        txtDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniKeyTyped(evt);
            }
        });
        jPanel1.add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 150, -1));

        jLabel15.setText("Sueldo:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 50, -1));
        jPanel1.add(txtSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 100, -1));

        rbtnActivo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbtnActivo.setText("ACTIVO");
        jPanel1.add(rbtnActivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 136, -1, -1));

        rbtnInactivo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rbtnInactivo.setText("INACTIVO");
        jPanel1.add(rbtnInactivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 136, -1, -1));

        jLabel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Estado"));
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 190, 50));

        jLabel17.setText("Usuario:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 50, 20));

        jLabel18.setText("Contraseña:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, 70, 20));

        jLabel19.setText("Tipo de usuario:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 80, 20));

        txtDireccion.setColumns(20);
        txtDireccion.setRows(5);
        jScrollPane2.setViewportView(txtDireccion);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 430, 40));
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, 140, -1));

        cboTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cboTipoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 290, 140, 20));

        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyTyped(evt);
            }
        });
        jPanel1.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 140, -1));

        jLabel21.setText("<html>Los campos marcado con un asterísco (<a style=\"color:red\">*</a>) son obligatorios</html>");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 280, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("*");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 20, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("*");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 20, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 0, 0));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("*");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 260, 20, 20));

        jLabel20.setText("Asignar una cuenta:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        cboCuentas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cboCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, 150, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 0, 0));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("*");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, 20, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 0, 0));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("*");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 20, 20));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("*");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, 20, 20));

        pNuevo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 370));

        tabEmpleado.addTab("Nuevo / Modificar", pNuevo);

        getContentPane().add(tabEmpleado);
        tabEmpleado.setBounds(10, 10, 600, 400);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if(tblEmpleado.getSelectedRows().length > 0 ) {
            accion="Modificar";
            modificar();
            tabEmpleado.setSelectedIndex(tabEmpleado.indexOfComponent(pNuevo));
        }else{
            JOptionPane.showMessageDialog(null, "¡Se debe seleccionar un registro!");
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        mirar();
        tabEmpleado.setSelectedIndex(tabEmpleado.indexOfComponent(pBuscar));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion="Nuevo";
        this.strCodigo = null;
        modificar();
        limpiarCampos();
        txtContraseña.setEnabled(true);
        tabEmpleado.setSelectedIndex(tabEmpleado.indexOfComponent(pNuevo));
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed
//----------------------VALIDACIÓN DE DATOS-------------------------------------
    public boolean validardatos(){
        if (txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese nombre del Empleado");
            txtNombre.requestFocus();
            txtNombre.setBackground(Color.YELLOW);
            return false;
        }else if (txtApellido.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese apellido del Empleado");
            txtApellido.requestFocus();
            txtApellido.setBackground(Color.YELLOW);
            return false;
        }else if (txtDni.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese DNI del Empleado");
            txtDni.requestFocus();
            txtDni.setBackground(Color.YELLOW);
            return false;
        }else if (txtUsuario.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese Usuario del Empleado");
            txtUsuario.requestFocus();
            txtUsuario.setBackground(Color.YELLOW);
            return false;
        }else if (txtContraseña.getPassword().length <= 0){
            JOptionPane.showMessageDialog(null,"Ingrese el Password del Empleado");
            txtContraseña.requestFocus();
            txtContraseña.setBackground(Color.YELLOW);
            return false;
        }else{
            return true;
        }

    }
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String algorithm="SHA-512";
        
        if(validardatos()==true){
            
            ClsEntidadEmpleadoHib empleado = new ClsEntidadEmpleadoHib();
            empleado.setIdEmpleado( new Integer(this.txtCodigo.getText()) );
            empleado.setNombre(this.txtNombre.getText());
            empleado.setApellido(this.txtApellido.getText());
            if (this.rbtnMasculino.isSelected()){
                empleado.setSexo("M");
            }else{
                empleado.setSexo("F");
            }
            empleado.setFechaNac(this.txtFechaNac.getDate());
            empleado.setDireccion(this.txtDireccion.getText());
            empleado.setTelefono(this.txtTelefono.getText());
            empleado.setCelular(this.txtCelular.getText());
            empleado.setEmail(this.txtEmail.getText());
            empleado.setDni(this.txtDni.getText());
            empleado.setFechaIng(this.txtFechaIng.getDate());
            if(this.txtSueldo.getText().equals("")){
                empleado.setSueldo( BigDecimal.ZERO );
            }else{
                empleado.setSueldo( new BigDecimal(txtSueldo.getText()) );
            }
            if (this.rbtnActivo.isSelected()){
                empleado.setEstado("ACTIVO");
            }else{
                empleado.setEstado("INACTIVO");
            }
            empleado.setUsuario(this.txtUsuario.getText());
            String password = getStringMessageDigest(new String(this.txtContraseña.getPassword()), algorithm); 
            empleado.setContrasena( password );
            empleado.setIdTipoUsuario( this.tiposUsurio.get( this.cboTipoUsuario.getSelectedIndex() ));
            empleado.setCuenta( this.cuentas.get( this.cboCuentas.getSelectedIndex() ));
            
            this.usuarioDao.saveOrUpdate(empleado);
            this.limpiarCampos();
            actualizarTabla();
            CantidadTotal();
            mirar();
        }
        
        //tabEmpleado.setSelectedIndex(tabEmpleado.indexOfComponent(pBuscar));
    //}
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmpleadoMouseClicked
    //SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
    //Date fechanac = null,fechaing=null;
    
        int fila = tblEmpleado.getSelectedRow();

        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            ClsEntidadEmpleadoHib empleado = this.empleados.get(fila);
            
            //defaultTableModel=(DefaultTableModel) tblEmpleado.getModel();
            strCodigo=empleado.getIdEmpleado().toString();//(String) defaultTableModel.getValueAt(registros,0));
            txtCodigo.setText(empleado.getIdEmpleado().toString());//(String)defaultTableModel.getValueAt(registros,0));
            txtNombre.setText(empleado.getNombre());//(String)defaultTableModel.getValueAt(registros,1));
            txtApellido.setText(empleado.getApellido());//(String)defaultTableModel.getValueAt(registros,2));
            if ("M".equals(empleado.getSexo())){
               rbtnMasculino.setSelected(true);
            }else{
               rbtnFemenino.setSelected(true);
            }
            txtFechaNac.setDate( empleado.getFechaNac() );
            txtDireccion.setText( empleado.getDireccion() );
            txtTelefono.setText( empleado.getTelefono() );
            txtCelular.setText( empleado.getCelular() );
            txtEmail.setText( empleado.getEmail() );
            txtDni.setText( empleado.getDni() );
            txtFechaIng.setDate( empleado.getFechaIng() );
            txtSueldo.setText( empleado.getSueldo().toString() );
            if ("ACTIVO".equals( empleado.getEstado() )){
               rbtnActivo.setSelected(true);
            }else{
               rbtnInactivo.setSelected(true);
            }
            txtUsuario.setText( empleado.getUsuario() );
            txtContraseña.setText( empleado.getContrasena() );
            cboTipoUsuario.setSelectedItem( empleado.getIdTipoUsuario().getDescripcion() );
            cboCuentas.setSelectedItem( empleado.getCuenta() == null ? "" : empleado.getCuenta().getNombre() );
            
        }

        mirar();
    }//GEN-LAST:event_tblEmpleadoMouseClicked

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z')) evt.consume();
        txtNombre.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode==KeyEvent.VK_ENTER) txtApellido.requestFocus();
    }//GEN-LAST:event_txtNombreKeyReleased

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char car = evt.getKeyChar();
        if((car<'a' || car>'z') && (car<'A' || car>'Z')) evt.consume();
        txtApellido.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniKeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9')) evt.consume();
        //----------------Poner limite de caracteres--------------------
        int i = txtDni.getText().length();
        if(txtDni.getText().trim().length()<8){

        }else{
            i=10;
            String com=txtDni.getText().substring(0, 7);
            txtDni.setText("");
            txtDni.setText(com);
        }
        txtDni.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtDniKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Map p=new HashMap();
        p.put("busqueda",txtBusqueda.getText());
        p.put("criterio", "");
        JasperReport report;
        JasperPrint print;
        try{
            
            InputStream inputstream = getClass().getResource("/Reportes/RptEmpleado.jrxml").openStream();
            //File reporte = new File(getClass().getResource("/Reportes/RptEmpleado.jrxml").openStream());
            //report=JasperCompileManager.compileReport(new File("").getAbsolutePath()+ "/src/Reportes/RptEmpleado.jrxml");
            report=JasperCompileManager.compileReport(inputstream);
            print=JasperFillManager.fillReport(report, p,connection);
            JasperViewer view=new JasperViewer(print,false);
            view.setTitle("Reporte de Clientes");
            view.setVisible(true);
        }catch(JRException e){
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(FrmEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyTyped
        char car = evt.getKeyChar();
        if((car<'0' || car>'9')) evt.consume();
    }//GEN-LAST:event_txtContraseñaKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.BuscarEmpleado();
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox cboCuentas;
    private javax.swing.JComboBox cboTipoUsuario;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JPanel pBuscar;
    private javax.swing.JPanel pNuevo;
    private javax.swing.JRadioButton rbtnActivo;
    private javax.swing.JRadioButton rbtnFemenino;
    private javax.swing.JRadioButton rbtnInactivo;
    private javax.swing.JRadioButton rbtnMasculino;
    private javax.swing.JTabbedPane tabEmpleado;
    private javax.swing.JTable tblEmpleado;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtEmail;
    private com.toedter.calendar.JDateChooser txtFechaIng;
    private com.toedter.calendar.JDateChooser txtFechaNac;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSueldo;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
    
    private void cargarComboCuentas() {
        this.cuentas = this.cuentasDao.getAllCuentas();
        
        DefaultComboBoxModel monedaModel = new DefaultComboBoxModel();
        
        for(ClsEntidadCuenta cuenta : this.cuentas ){
            monedaModel.addElement(cuenta.getNombre());
        }
        
        this.cboCuentas.setModel(monedaModel);
    }
}

