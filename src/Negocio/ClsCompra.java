/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Conexion.*;
import Entidad.*;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

public class ClsCompra {
private Connection connection=new ClsConexion().getConection();
//--------------------------------------------------------------------------------------------------
//-----------------------------------------METODOS--------------------------------------------------
//-------------------------------------------------------------------------------------------------- 
    public void agregarCompra(ClsEntidadCompra nuevaCompra){
        try{
            /*
            CallableStatement statement=connection.prepareCall("{call SP_I_Compra(?,?,?,?,?,?,?,?,?)}");
            statement.setString("pidtipodocumento",Compra.getStrIdTipoDocumento());
            statement.setString("pidproveedor",Compra.getStrIdProveedor());
            statement.setString("pidempleado",Compra.getStrIdEmpleado());
            statement.setString("pnumero",Compra.getStrNumeroCompra());
            statement.setDate ("pfecha", new java.sql.Date(Compra.getStrFechaCompra().getTime()));
            statement.setString("psubtotal",Compra.getStrSubTotalCompra());
            statement.setString("pigv",Compra.getStrIgvCompra());
            statement.setString("ptotal",Compra.getStrTotalCompra());
            statement.setString("pestado",Compra.getStrEstadoCompra());
            statement.execute();
            */
            
            ClsEntidadCompraHib compra = new ClsEntidadCompraHib();
            
            compra.setIdTipoDocumento( new ClsEntidadTipodocumentoHib(new Integer(nuevaCompra.getStrIdTipoDocumento())) );
            compra.setIdProveedor(new ClsEntidadProveedorHib( new Integer(nuevaCompra.getStrIdProveedor())));
            compra.setIdEmpleado(new ClsEntidadEmpleadoHib( new Integer(nuevaCompra.getStrIdEmpleado()) ));
            compra.setNumero(nuevaCompra.getStrNumeroCompra());
            compra.setFecha(nuevaCompra.getStrFechaCompra());
            compra.setSubTotal(new BigDecimal( nuevaCompra.getStrSubTotalCompra() ));
            compra.setIgv(new BigDecimal( nuevaCompra.getStrIgvCompra() ));
            compra.setTotal(new BigDecimal( nuevaCompra.getStrTotalCompra() ));
            compra.setEstado(nuevaCompra.getStrEstadoCompra());
            
            Session session = HibernateUtil.getInstance().getSession();
            session.beginTransaction();
            session.save(compra);
            session.getTransaction().commit();
            session.close();

            JOptionPane.showMessageDialog(null,"¡Compra Realizada con éxito!","Mensaje del Sistema",1);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }    
    public void modificarCompra(String codigo,ClsEntidadCompra Compra){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_U_Compra(?,?,?,?,?,?,?,?,?,?)}");
            statement.setString("pidcompra",codigo);
            statement.setString("pidtipodocumento",Compra.getStrIdTipoDocumento());
            statement.setString("pidproveedor",Compra.getStrIdProveedor());
            statement.setString("pidempleado",Compra.getStrIdEmpleado());
            statement.setString("pnumero",Compra.getStrNumeroCompra());
            statement.setDate ("pfecha", new java.sql.Date(Compra.getStrFechaCompra().getTime()));
            statement.setString("psubtotal",Compra.getStrSubTotalCompra());
            statement.setString("pigv",Compra.getStrIgvCompra());
            statement.setString("ptotal",Compra.getStrTotalCompra());
            statement.setString("pestado",Compra.getStrEstadoCompra());
            statement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"¡Compra Actualizada!","Mensaje del Sistema",1);
    }
    public ArrayList<ClsEntidadCompra> listarCompra(){
        ArrayList<ClsEntidadCompra> compras=new ArrayList<ClsEntidadCompra>();
        try{
            CallableStatement statement=connection.prepareCall("{call SP_S_Compra}");
            ResultSet resultSet=statement.executeQuery();
            
            while (resultSet.next()){
                ClsEntidadCompra compra=new ClsEntidadCompra();
                compra.setStrIdCompra(resultSet.getString("IdCompra"));
                compra.setStrTipoDocumento(resultSet.getString("TipoDocumento"));
                compra.setStrProveedor(resultSet.getString("Proveedor"));
                compra.setStrEmpleado(resultSet.getString("Empleado"));               
                compra.setStrNumeroCompra(resultSet.getString("Numero"));
                compra.setStrFechaCompra(resultSet.getDate("Fecha"));
                compra.setStrSubTotalCompra(resultSet.getString("SubTotal"));
                compra.setStrIgvCompra(resultSet.getString("Igv"));
                compra.setStrTotalCompra(resultSet.getString("Total"));
                compra.setStrEstadoCompra(resultSet.getString("Estado"));

                compras.add(compra);
            }
            return compras;
         }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    public ResultSet listarCompraPorParametro(String criterio, String busqueda) throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_CompraPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    }    
    public ResultSet obtenerUltimoIdCompra() throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_UltimoIdCompra()}");
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    }      
    
    public ResultSet listarCompraPorFecha(String criterio,Date fechaini, Date fechafin, String doc) throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_CompraPorFecha(?,?,?,?)}");
            statement.setString ("pcriterio", criterio);
            statement.setDate ("pfechaini", new java.sql.Date(fechaini.getTime()));
            statement.setDate ("pfechafin", new java.sql.Date(fechafin.getTime()));
            statement.setString("pdocumento", doc);
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    } 
    public void actualizarCompraEstado(String codigo,ClsEntidadCompra Compra){
        try{
            CallableStatement statement=connection.prepareCall("{call SP_U_ActualizarCompraEstado(?,?)}");
            statement.setString("pidcompra",codigo);
            statement.setString("pestado",Compra.getStrEstadoCompra());        
            statement.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null,"¡Compra Anulada!","Mensaje del Sistema",1);
    }
    public ResultSet listarCompraPorDetalle(String criterio,Date fechaini, Date fechafin) throws Exception{
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_CompraPorDetalle(?,?,?)}");
            statement.setString ("pcriterio", criterio);
            statement.setDate ("pfechaini", new java.sql.Date(fechaini.getTime()));
            statement.setDate ("pfechafin", new java.sql.Date(fechafin.getTime()));
            rs = statement.executeQuery();
            return rs;
        }catch(SQLException SQLex){
            throw SQLex;            
        }        
    }
    
    public void generarOrdenDeCompra(int IDCompra) throws Exception{
        /*
        ResultSet rs = null;
        try{
            CallableStatement statement = connection.prepareCall("{call SP_S_CompraPorID(?)}");
            statement.setInt("pidcompra", IDCompra);
            
            rs = statement.executeQuery();
            
            while(rs.next()){
                ClsEntidadCompra compra = new ClsEntidadCompra();
                
                compra.setStrIdCompra( Integer.toString(rs.getInt("IdCompra")) );
                compra.setStrFechaCompra( rs.getDate("Fecha") );
                compra.setStrNumeroCompra( rs.getString("Numero"));
                compra.setStrEstadoCompra( rs.getString("Estado"));
                compra.setStrSubTotalCompra( Double.toString(rs.getDouble("SubTotal")) );
                compra.setStrIgvCompra( Double.toString(rs.getDouble("Igv")) );
                compra.setStrTotalCompra( Double.toString(rs.getDouble("Total")) );
                
                ClsEntidadProveedor proveedor = new ClsEntidadProveedor();
                proveedor.setStrNombreProveedor( rs.getString("Proveedor"));
                proveedor.setStrRucProveedor( rs.getString("Ruc"));
                proveedor.setStrIdProveedor( Integer.toString(rs.getInt("IdProveedor")) );
                proveedor.setStrTelefonoProveedor( rs.getString("Telefono") );
                proveedor.setStrDireccionProveedor(rs.getString("Direccion"));
                
                HashMap<String, IntEntidad> parametros = new HashMap<String, IntEntidad>();
                parametros.put("compra", compra);
                parametros.put("proveedor", proveedor);
                
                //reteive the products of this sale
                CallableStatement productsStatement = connection.prepareCall("{call SP_S_DetalleCompraPorParametro(?,?)}");
                productsStatement.setString("pcriterio", "id");
                productsStatement.setString("pbusqueda", String.valueOf(IDCompra));
                ResultSet detalleRs = productsStatement.executeQuery();
                
                ArrayList<ClsEntidadProducto> productos = new ArrayList<ClsEntidadProducto>();
                ArrayList<ClsEntidadDetalleCompra> detalles = new ArrayList<ClsEntidadDetalleCompra>();
                while(detalleRs.next()){
                    //PRODUCTO
                    ClsEntidadProducto producto = new ClsEntidadProducto();
                    producto.setStrIdProducto(String.valueOf( detalleRs.getInt("IdProducto")) );
                    producto.setStrCodigoProducto( detalleRs.getString("Codigo") );
                    producto.setStrNombreProducto( detalleRs.getString("Nombre") );
                    producto.setStrDescripcionProducto(detalleRs.getString("Descripcion") );
                    productos.add(producto);
                            
                    //DETALLE
                    ClsEntidadDetalleCompra detalleDeCompra = new ClsEntidadDetalleCompra();
                    detalleDeCompra.setStrIdCompra( String.valueOf(detalleRs.getInt("IdCompra")) );
                    detalleDeCompra.setStrCantidadDet( String.valueOf(detalleRs.getDouble("Cantidad")) );
                    detalleDeCompra.setStrPrecioDet( String.valueOf(detalleRs.getDouble("Precio")) );
                    detalleDeCompra.setStrTotalDet( String.valueOf(detalleRs.getDouble("Total")) );
                    detalles.add(detalleDeCompra);
                    
                }
                
                Presentacion.FrmOrdenCompra frmOrdenCompra = new Presentacion.FrmOrdenCompra( );
                frmOrdenCompra.setClsEntidadCompra(compra);
                frmOrdenCompra.setClsEntidadProveedor(proveedor);
                frmOrdenCompra.setProductos(productos);
                frmOrdenCompra.setDetalles(detalles);
                
                Presentacion.FrmPrincipal.Escritorio.add(frmOrdenCompra);
                frmOrdenCompra.show();
            }
            
        }catch(SQLException SQLex){
            throw SQLex;
        } 
                */
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        List<ClsEntidadCompraHib> compras = session.createQuery("FROM ClsEntidadCompraHib WHERE IdCompra = " + IDCompra).list();
        Presentacion.FrmOrdenCompra frmOrdenCompra = new Presentacion.FrmOrdenCompra( );
        
        //frmOrdenCompra.setClsEntidadCompraHib(compras.get(0));
        frmOrdenCompra.setIntEntidadTransaccionImprimible(compras.get(0));
        
        Presentacion.FrmPrincipal.Escritorio.add(frmOrdenCompra);
        frmOrdenCompra.show();
        
        //session.getTransaction().commit();
        session.close();
        
    }
}
