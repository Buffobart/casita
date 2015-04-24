/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsEntidadCuenta;
import Entidad.ClsEntidadOperacionHib;
import Entidad.IntOperacion;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface OperacionDao {
    public List<ClsEntidadOperacionHib> listAllOperaciones();
    public List<ClsEntidadOperacionHib> listByFilters(Date inicio, Date fin, ClsEntidadCuenta cuenta);
    public void addOperacion(IntOperacion operacion);
    public void addOperacion(ClsEntidadOperacionHib operacion);
    //public void addOperacionAsIs(ClsEntidadOperacionHib operacion);
}
