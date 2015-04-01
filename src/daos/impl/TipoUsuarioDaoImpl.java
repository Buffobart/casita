/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos.impl;

import Conexion.HibernateUtil;
import Entidad.ClsEntidadProductoHib;
import Entidad.ClsEntidadTipousuarioHib;
import daos.TipoUsuarioDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alan
 */
public class TipoUsuarioDaoImpl implements TipoUsuarioDao{

    @Override
    public List<ClsEntidadTipousuarioHib> getAllTipoUsuario() {
        Session session = HibernateUtil.getInstance().getSession();
        session.beginTransaction();
        
        Query query = session.createQuery("FROM ClsEntidadTipousuarioHib");
        
        List<ClsEntidadTipousuarioHib> tipos = query.list();
        session.close();
        
        return tipos;
    }
    
}
