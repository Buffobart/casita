/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadOperacionHib;
import Entidad.ClsGastosVariosHib;
import daos.GastosVariosDao;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class GastosVariosDaoImpl implements GastosVariosDao{

    @Override
    public List<ClsGastosVariosHib> getAllGastos() {
        Session session = null;
        List results = null;
        try{
        session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        results = session.createQuery("SELECT c FROM ClsGastosVariosHib c").list();
        }finally{
            if(session != null){
                session.close();
            }
        }
        return results;
    }

    @Override
    public void addGasto(ClsGastosVariosHib gasto) {
        Session session = null;
        try{
        session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        session.save(gasto);
        session.getTransaction().commit();
        }finally{
            if(session != null){
                session.close();
            }
        }
    }
    
}
