/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadCuenta;
import Entidad.ClsEntidadOperacionHib;
import Entidad.IntOperacion;
import daos.OperacionDao;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class OperacionDaoImpl implements OperacionDao{

    @Override
    public void addOperacion(IntOperacion operacion) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        List<ClsEntidadOperacionHib> operaciones = operacion.getOperaciones();
        
        for(ClsEntidadOperacionHib op : operaciones){
            session.save(op);
        }
        
        session.getTransaction().commit();
        session.close();
        
    }

    @Override
    public List<ClsEntidadOperacionHib> listAllOperaciones() {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        List<ClsEntidadOperacionHib> cuentas = session.createQuery("FROM ClsEntidadOperacionHib").list();
        
        //session.getTransaction().commit();
        session.close();
        
        return cuentas;
        
    }

    @Override
    public List<ClsEntidadOperacionHib> listByFilters(Date inicio, Date fin, ClsEntidadCuenta cuenta) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        String query = "FROM ClsEntidadOperacionHib c WHERE c.hora >= :fechaInicial AND c.hora <= :fechaFinal";
        if(cuenta != null){
            query += " AND cuenta="+cuenta.getIdCuenta().toString();
        }
        
        
        
        Query query_e = session.createQuery(query);
        query_e.setParameter("fechaInicial", inicio);
        query_e.setParameter("fechaFinal", fin);
        
        System.out.println("Query: " + query_e.getQueryString());
        
        List<ClsEntidadOperacionHib> cuentas = query_e.list();
        
        //session.getTransaction().commit();
        session.close();
        
        return cuentas;
        
    }

    @Override
    public void addOperacion(ClsEntidadOperacionHib operacion) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        //Query query = session.createQuery("FROM ClsEntidadCuenta WHERE IdCuenta=:idcuenta");
        //query.setParameter("idcuenta", operacion.getCuenta().getIdCuenta().toString());
        
        //ClsEntidadCuenta cuenta = (ClsEntidadCuenta)query.list().get(0);
        
        //operacion.setMontoInicial(cuenta.getBalance());
        //operacion.setMontoFinal(cuenta.getBalance().add(operacion.getCantidad()));
        
        session.save(operacion);
        
        session.getTransaction().commit();
        session.close();
    }
    
}
