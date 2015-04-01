/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadProveedorContactoHib;
import Entidad.ClsEntidadProveedorHib;
import daos.ProveedorDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class ProveedorDaoImpl implements ProveedorDao{

    @Override
    public List<ClsEntidadProveedorHib> getAllProveedores() {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        List<ClsEntidadProveedorHib> proveedores = session.createQuery("FROM ClsEntidadProveedorHib WHERE estado='ACTIVO'").list();
        
        //session.getTransaction().commit();
        session.close();
        
        return proveedores;
    }

    @Override
    public ClsEntidadProveedorHib getProveedorById(int id) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadProveedorHib WHERE estado='ACTIVO' AND IdProveedor=:IdProveedor");
        query.setParameter("IdProveedor", id);
        
        List<ClsEntidadProveedorHib> proveedores = query.list();
        
        //session.getTransaction().commit();
        session.close();
        
        return proveedores.get(0);
    }

    @Override
    public List<ClsEntidadProveedorHib> buscaPorId( String id ) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadProveedorHib WHERE estado='ACTIVO' AND IdProveedor LIKE :IdProveedor");
        query.setParameter("IdProveedor", "%"+id+"%");
        
        List<ClsEntidadProveedorHib> proveedores = query.list();
        
        //session.getTransaction().commit();
        session.close();
        
        return proveedores;
    }

    @Override
    public List<ClsEntidadProveedorHib> buscaPorNombre( String nombre ) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadProveedorHib WHERE estado='ACTIVO' AND Nombre LIKE :Nombre");
        query.setParameter("Nombre", "%"+nombre+"%");
        
        List<ClsEntidadProveedorHib> proveedores = query.list();
        
        //session.getTransaction().commit();
        session.close();
        
        return proveedores;
    }

    @Override
    public List<ClsEntidadProveedorHib> buscaPorRfc( String rfc ) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadProveedorHib WHERE estado='ACTIVO' AND Ruc LIKE :Ruc");
        query.setParameter("Ruc", "%"+rfc+"%");
        
        List<ClsEntidadProveedorHib> proveedores = query.list();
        
        //session.getTransaction().commit();
        session.close();
        
        return proveedores;
    }

    @Override
    public List<ClsEntidadProveedorHib> buscaPorDni( String dni ) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadProveedorHib WHERE estado='ACTIVO' AND Dni LIKE :Dni");
        query.setParameter("Dni", "%"+dni+"%");
        
        List<ClsEntidadProveedorHib> proveedores = query.list();
        
        //session.getTransaction().commit();
        session.close();
        
        return proveedores;
    }
    
    @Override
    public void agregarOActualizaProveedor(ClsEntidadProveedorHib proveedor){
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        session.saveOrUpdate(proveedor);
        
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void borrarContactoProveedor(ClsEntidadProveedorContactoHib contacto) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        session.delete(contacto);
        
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void agregarOActualizaContactoProveedor(ClsEntidadProveedorContactoHib contacto) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        session.saveOrUpdate(contacto);
        
        session.getTransaction().commit();
        session.close();
    }
    
}
