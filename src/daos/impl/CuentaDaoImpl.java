/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadCuenta;
import Entidad.ClsEntidadOperacionHib;
import daos.CuentaDao;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class CuentaDaoImpl implements CuentaDao{

    @Override
    public List<ClsEntidadCuenta> getAllCuentas() {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        List<ClsEntidadCuenta> cuentas = session.createQuery("FROM ClsEntidadCuenta").list();
        
        session.close();
        
        return cuentas;
    }
    
}
