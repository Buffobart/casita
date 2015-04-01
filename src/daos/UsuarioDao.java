/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsEntidadEmpleadoHib;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface UsuarioDao {
    public ClsEntidadEmpleadoHib login(String user, String password);
    public List<ClsEntidadEmpleadoHib> getAllUsuarios();
    public ClsEntidadEmpleadoHib getUsuario(int id);
    public List<ClsEntidadEmpleadoHib> buscar(String query);
    public void saveOrUpdate(ClsEntidadEmpleadoHib empleado);
}
