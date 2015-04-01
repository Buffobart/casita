/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadCuenta;
import Entidad.ClsEntidadEmpleadoHib;
import Entidad.ClsEntidadProductoHib;
import Entidad.ClsEntidadTipoUsuario;
import daos.UsuarioDao;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class UsuarioDaoImpl implements UsuarioDao{

    @Override
    public ClsEntidadEmpleadoHib login(String user, String password) {
        
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadEmpleadoHib WHERE Usuario=:usuario AND Contrasena=:password");
        query.setParameter("usuario", user);
        query.setParameter("password", password);
        
        ClsEntidadEmpleadoHib empleado = null;
        
        if( query.list().size() > 0 ){
            
            empleado = (ClsEntidadEmpleadoHib) query.list().get(0);
            if( !empleado.getEstado().equals("ACTIVO") ){
                JOptionPane.showMessageDialog(null, "Este Usuario esta desactivado");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Nombre de usuario o password incorrectos");
        }
        
        session.close();
        return empleado;
        
    }

    @Override
    public List<ClsEntidadEmpleadoHib> getAllUsuarios() {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadEmpleadoHib");
        List<ClsEntidadEmpleadoHib> empleados = query.list();
        session.close();
        return empleados;
    }

    @Override
    public ClsEntidadEmpleadoHib getUsuario(int id) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadEmpleadoHib WHERE IdEmpleado=:id");
        query.setParameter("id", id);
        ClsEntidadEmpleadoHib empleado = (ClsEntidadEmpleadoHib) query.list().get(0);
        session.close();
        return empleado;
    }

    @Override
    public List<ClsEntidadEmpleadoHib> buscar(String busqueda) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        String str_query = "FROM ClsEntidadEmpleadoHib where (IdEmpleado like :id OR Apellido like :apellido OR Nombre like :nombre OR Usuario like :usuario)";
        //str_query += includeInactive ? "" : " AND Estado='ACTIVO'";
        Query query = session.createQuery(str_query);
        query.setParameter("id", "%"+busqueda+"%");
        query.setParameter("apellido", "%"+busqueda+"%");
        query.setParameter("nombre", "%"+busqueda+"%");
        query.setParameter("usuario", "%"+busqueda+"%");
        
        List<ClsEntidadEmpleadoHib> empleados = query.list();
        
        session.getTransaction().commit();
        session.close();
        
        return empleados;
    }

    @Override
    public void saveOrUpdate(ClsEntidadEmpleadoHib empleado) {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        session.saveOrUpdate(empleado);
        
        session.getTransaction().commit();
        session.close();
    }
    
}
