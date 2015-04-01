/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadProductoHib;
import daos.ProductoDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class ProductoDaoImpl implements ProductoDao{

    @Override
    public List<ClsEntidadProductoHib> getAllProductos(boolean includeInactive) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        String str_query = "FROM ClsEntidadProductoHib";
        str_query += includeInactive ? "" : " WHERE Estado='ACTIVO'";
        List<ClsEntidadProductoHib> productos = session.createQuery(str_query).list();
        
        //session.getTransaction().commit();
        session.close();
        
        return productos;
    }

    @Override
    public List<ClsEntidadProductoHib> getProductosPorNombre(String nombre, boolean includeInactive) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        String str_query = "FROM ClsEntidadProductoHib where Nombre like :nombre";
        str_query += includeInactive ? "" : " AND Estado='ACTIVO'";
        Query query = session.createQuery(str_query);
        query.setParameter("nombre", "%"+nombre+"%");
        
        List<ClsEntidadProductoHib> productos = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return productos;
    }

    @Override
    public List<ClsEntidadProductoHib> getProductosPorCodigo(String codigo, boolean includeInactive) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        String str_query = "FROM ClsEntidadProductoHib where Codigo like :codigo";
        str_query += includeInactive ? "" : " AND Estado='ACTIVO'";
        Query query = session.createQuery(str_query);
        query.setParameter("codigo", "%"+codigo+"%");
        
        List<ClsEntidadProductoHib> productos = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return productos;
    }

    @Override
    public List<ClsEntidadProductoHib> getProductosPorDescripcion(String descripcion, boolean includeInactive) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        String str_query = "FROM ClsEntidadProductoHib where Descripcion like :descripcion";
        str_query += includeInactive ? "" : " AND Estado='ACTIVO'";
        Query query = session.createQuery(str_query);
        query.setParameter("descripcion", "%"+descripcion+"%");
        
        List<ClsEntidadProductoHib> productos = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return productos;
    }

    @Override
    public void borrarProducto(ClsEntidadProductoHib borrar) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        session.delete(borrar);
        
        session.getTransaction().commit();
        session.close();
    }

    public void desactivarProducto(ClsEntidadProductoHib producto) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        producto.setEstado(ClsEntidadProductoHib.PROP_INACTIVO);
        session.update(producto);
        
        session.getTransaction().commit();
        session.close();
    }

    public List<ClsEntidadProductoHib> buscar(String busqueda, boolean includeInactive) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        String str_query = "FROM ClsEntidadProductoHib where (Descripcion like :descripcion OR Codigo like :codigo OR Nombre like :nombre)";
        str_query += includeInactive ? "" : " AND Estado='ACTIVO'";
        Query query = session.createQuery(str_query);
        query.setParameter("descripcion", "%"+busqueda+"%");
        query.setParameter("codigo", "%"+busqueda+"%");
        query.setParameter("nombre", "%"+busqueda+"%");
        
        List<ClsEntidadProductoHib> productos = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return productos;
    }
    
}
