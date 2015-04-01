/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadDetalleventaHib;
import Entidad.ClsEntidadVentaHib;
import daos.VentaDao;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class VentaDaoImpl implements VentaDao{

    /**
     *
     * @param id
     */
    @Override
    public void generarOrdenDeCompra(int id) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        List<ClsEntidadVentaHib> ventas = session.createQuery( "FROM ClsEntidadVentaHib WHERE IdVenta=" + id ).list();

        if(ventas.size() <= 0){
            return;
        }
        
        ClsEntidadVentaHib venta = ventas.get(0);
        
        Collection<ClsEntidadDetalleventaHib> detallesVenta = venta.getClsEntidadDetalleventaHibCollection();
        
        float total = 0.0f;
        
        for( ClsEntidadDetalleventaHib detalle : detallesVenta ){
            detalle.setTotal( detalle.getCosto().multiply(detalle.getCantidad()) );
            total += detalle.getTotal().floatValue();
        }
        
        venta.setTotalPagar(new BigDecimal(total));
        venta.setIgv(new BigDecimal( total * 0.16 ));
        venta.setSubTotal(new BigDecimal( total / 1.16 ));
        
        
        Presentacion.FrmOrdenCompra frmOrdenCompra = new Presentacion.FrmOrdenCompra( );
        frmOrdenCompra.setIntEntidadTransaccionImprimible(venta);
        Presentacion.FrmPrincipal.Escritorio.add(frmOrdenCompra);
        frmOrdenCompra.show();
        
        session.close();
        
    }

    @Override
    public void saveVenta(ClsEntidadVentaHib venta) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();

        session.save(venta);
        
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<ClsEntidadVentaHib> getAllVentas() {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery( "FROM ClsEntidadVentaHib WHERE Estado=:estadoVenta OR Estado=:estadoEmitido");
        query.setParameter("estadoVenta", ClsEntidadVentaHib.PRO_TIPO_VENTA);
        query.setParameter("estadoEmitido", ClsEntidadVentaHib.PRO_TIPO_EMITIDO);
        List<ClsEntidadVentaHib> ventas = query.list();
        
        session.close();
        
        return ventas;
    }

    @Override
    public List<ClsEntidadVentaHib> getAllCotizaciones() {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery( "FROM ClsEntidadVentaHib WHERE Estado=:estado");
        query.setParameter("estado", ClsEntidadVentaHib.PRO_TIPO_COTIZACION);
        List<ClsEntidadVentaHib> ventas = query.list();
        
        session.close();
        
        return ventas;
    }
    
}
