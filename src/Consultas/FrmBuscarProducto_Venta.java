/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import Conexion.HibernateUtil;
import Presentacion.*;
import Entidad.ClsEntidadProductoHib;
import Negocio.ClsProducto;
import daos.impl.ProductoDaoImpl;
import java.sql.ResultSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author DAYPER-PERU
 */
public class FrmBuscarProducto_Venta extends javax.swing.JInternalFrame {


    static ResultSet rs=null;
    DefaultTableModel dtm=new DefaultTableModel();
    public String Total;
    String criterio,busqueda;
    FrmVenta parent;
    
    List<ClsEntidadProductoHib> productos;
    ProductoDaoImpl productoDao = new ProductoDaoImpl();
    
    public FrmBuscarProducto_Venta(FrmVenta parent) {
        
        this.parent = parent;

        initComponents();
        buttonGroup1.add(rbtnCodigo);
        buttonGroup1.add(rbtnNombre);
        buttonGroup1.add(rbtnDescripcion);


        //--------------------PANEL - PRODUCTO----------------------------
        
        this.productos = productoDao.getAllProductos(false);
        actualizarTablaProducto();
        CrearTablaProducto();
        //---------------------ANCHO Y ALTO DEL FORM----------------------
        this.setSize(536, 300);
        CantidadTotal();

    }

//-----------------------------------------------------------------------------------------------
//----------------------------------PANEL - PRODUCTO---------------------------------------------
//-----------------------------------------------------------------------------------------------
    void actualizarTablaProducto(){
       String titulos[]={"ID","Cód. de Barras","Nombre","Descripción","Stock","P. Costo","P. Venta","Max. Descuento"};
       DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
       
        //Session session = HibernateUtil.getInstance().getSession();
        //session.beginTransaction();
        //productos = session.createQuery("FROM ClsEntidadProductoHib where estado='ACTIVO'").list();
        
        String fila[]=new String[titulos.length];
        for(ClsEntidadProductoHib producto : productos){
            try{
                fila[0]=producto.getIdProducto().toString();
                fila[1]=producto.getCodigo();
                fila[2]=producto.getNombre();
                fila[3]=producto.getDescripcion();
                fila[4]=producto.getStock().toString();
                fila[5]=producto.getPrecioCosto().toString();
                fila[6]=producto.getPrecioVenta().toString();
                fila[7]=String.valueOf( producto.getDescuentoVenta() );
                defaultTableModel.addRow(fila);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        tblProducto.setModel(defaultTableModel);
        
        //session.getTransaction().commit();
        //session.close();
              
       /*
       ClsProducto productos=new ClsProducto();
       ArrayList<ClsEntidadProducto> producto=productos.listarProductoActivo();
       Iterator iterator=producto.iterator();
       DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
       
       String fila[]=new String[titulos.length];
       while(iterator.hasNext()){
           ClsEntidadProducto Producto=new ClsEntidadProducto();
           Producto=(ClsEntidadProducto) iterator.next();
           fila[0]=Producto.getStrIdProducto();
           fila[1]=Producto.getStrCodigoProducto();       
           fila[2]=Producto.getStrNombreProducto();
           fila[3]=Producto.getStrDescripcionProducto();
           fila[4]=Producto.getStrStockProducto();
           fila[5]=Producto.getStrPrecioCostoProducto();
           fila[6]=Producto.getStrPrecioVentaProducto();
           fila[7]=Producto.getStrDescuentoVenta();
           defaultTableModel.addRow(fila);               
       }
       tblProducto.setModel(defaultTableModel);
        */
   }
    void CrearTablaProducto(){
   //--------------------PRESENTACION DE JTABLE PRODUCTO----------------------
      
        /*
        TableCellRenderer render = new DefaultTableCellRenderer() { 

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) { 
                //aqui obtengo el render de la calse superior 
                JLabel l = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
                //Determinar Alineaciones   
                    if(column==0 || column==1 || column==4 || column==5 || column==6){
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
        /*
        int[] anchos = {40,100,150,200,60,60,60,60};
        for(int i = 0; i < tblProducto.getColumnCount(); i++) {
            tblProducto.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }
        */
   }
    void BuscarProductoPanel(){
        //String titulos[]={"ID","Cód. de Barras","Nombre","Descripción","Stock","P. Costo","P. Venta","Max. Descuento"};
        //DefaultTableModel defaultTableModel=new DefaultTableModel(null,titulos);
        
        //Session session = HibernateUtil.getInstance().getSession();
        //session.beginTransaction();
        //productos = session.createQuery("FROM ClsEntidadProductoHib where estado='ACTIVO'").list();
        
        busqueda=txtBusqueda.getText();
        if(rbtnCodigo.isSelected()){
            criterio="codigo";
            this.productos = this.productoDao.getProductosPorCodigo(busqueda, false);
        }else if(rbtnNombre.isSelected()){
            criterio="nombre";
            this.productos = this.productoDao.getProductosPorNombre(busqueda, false);
        }else if(rbtnDescripcion.isSelected()){
            criterio="descripcion";
            this.productos = this.productoDao.getProductosPorDescripcion(busqueda, false);
        }
        
        this.actualizarTablaProducto();
        
        //this.productos = query.list();
        /*
        session.getTransaction().commit();
        session.close();
        
        //"ID","Cód. de Barras","Nombre","Descripción","Stock","P. Costo","P. Venta","Max. Descuento"
        String datos[] = new String[titulos.length];
        for(ClsEntidadProductoHib producto: this.productos){
            try{
                datos[0] = producto.getIdProducto().toString();
                datos[1] = producto.getCodigo();
                datos[2] = producto.getNombre();
                datos[3] = producto.getDescripcion();
                datos[4] = producto.getStock().toString();
                datos[5] = producto.getPrecioCosto().toString();
                datos[6] = producto.getPrecioVenta().toString();
                datos[7] = String.valueOf( producto.getDescuentoVenta() );
                defaultTableModel.addRow(datos);
            }catch(Exception e){
                //continue;
                e.printStackTrace();
            }
        }
        
        tblProducto.setModel(defaultTableModel);
        */
    }
    void CantidadTotal(){
        Total= String.valueOf(tblProducto.getRowCount());   
        lblEstado.setText("Se cargaron " + Total + " registros");      
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rbtnCodigo = new javax.swing.JRadioButton();
        txtBusqueda = new javax.swing.JTextField();
        rbtnNombre = new javax.swing.JRadioButton();
        rbtnDescripcion = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        lblEstado = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Consultar Productos");
        getContentPane().setLayout(null);

        rbtnCodigo.setText("Cód. Producto");
        rbtnCodigo.setOpaque(false);
        rbtnCodigo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnCodigoStateChanged(evt);
            }
        });
        getContentPane().add(rbtnCodigo);
        rbtnCodigo.setBounds(30, 31, 110, 23);

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });
        getContentPane().add(txtBusqueda);
        txtBusqueda.setBounds(30, 61, 300, 20);

        rbtnNombre.setSelected(true);
        rbtnNombre.setText("Nombre");
        rbtnNombre.setOpaque(false);
        rbtnNombre.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtnNombreStateChanged(evt);
            }
        });
        getContentPane().add(rbtnNombre);
        rbtnNombre.setBounds(143, 31, 80, 23);

        rbtnDescripcion.setText("Descripción");
        rbtnDescripcion.setOpaque(false);
        getContentPane().add(rbtnDescripcion);
        rbtnDescripcion.setBounds(230, 31, 79, 23);

        jLabel18.setBackground(new java.awt.Color(223, 223, 223));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Criterios de Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jLabel18.setOpaque(true);
        getContentPane().add(jLabel18);
        jLabel18.setBounds(10, 11, 410, 80);

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
        jScrollPane2.setViewportView(tblProducto);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 101, 500, 150);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/door_in.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir);
        btnSalir.setBounds(430, 10, 80, 80);
        getContentPane().add(lblEstado);
        lblEstado.setBounds(10, 250, 180, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnCodigoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnCodigoStateChanged
        txtBusqueda.setText("");
    }//GEN-LAST:event_rbtnCodigoStateChanged

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        BuscarProductoPanel();
        CrearTablaProducto();
        CantidadTotal();
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void rbtnNombreStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtnNombreStateChanged
        txtBusqueda.setText("");
    }//GEN-LAST:event_rbtnNombreStateChanged

    private void tblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductoMouseClicked

        DefaultTableModel defaultTableModel;// = new DefaultTableModel();
        int index = tblProducto.getSelectedRow();

        if (index == -1){
            JOptionPane.showMessageDialog(null, "Se debe seleccionar un registro");
        }else{
            
            this.parent.setSelectedProduct(this.productos.get(index));
            this.parent.updateProductDetails();
            /*
            defaultTableModel = (DefaultTableModel)tblProducto.getModel();

                Presentacion.FrmVenta.lblIdProducto.setText((String)defaultTableModel.getValueAt(fila,0));
                Presentacion.FrmVenta.txtCodigoProducto.setText((String)defaultTableModel.getValueAt(fila,1));
                Presentacion.FrmVenta.txtNombreProducto.setText((String)defaultTableModel.getValueAt(fila,2));
                Presentacion.FrmVenta.txtDescripcionProducto.setText((String)defaultTableModel.getValueAt(fila,3));
                Presentacion.FrmVenta.txtStockProducto.setText((String)defaultTableModel.getValueAt(fila,4));
                Presentacion.FrmVenta.txtCostoProducto.setText((String)defaultTableModel.getValueAt(fila,5));          
                Presentacion.FrmVenta.txtPrecioProducto.setText((String)defaultTableModel.getValueAt(fila,6));
                Presentacion.FrmVenta.txtMaxDescuentoProducto.setText( (String)defaultTableModel.getValueAt(fila,7) );
              */  
                /*
                this.parent.setPrecioProducto((String)defaultTableModel.getValueAt(fila,6), 
                        (String)defaultTableModel.getValueAt(fila,7));
                */
        
        }

    }//GEN-LAST:event_tblProductoMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JRadioButton rbtnCodigo;
    private javax.swing.JRadioButton rbtnDescripcion;
    private javax.swing.JRadioButton rbtnNombre;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}
