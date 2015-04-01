/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Entidad.ClsEntidadTipousuarioHib;
import java.util.List;

/**
 *
 * @author Alan
 */
public interface TipoUsuarioDao {
    public List<ClsEntidadTipousuarioHib> getAllTipoUsuario();
}
