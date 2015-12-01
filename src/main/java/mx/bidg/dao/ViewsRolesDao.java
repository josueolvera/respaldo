package mx.bidg.dao;

import mx.bidg.model.ViewsRole;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/11/15.
 */
public interface ViewsRolesDao extends InterfaceDao<ViewsRole> {
    List<ViewsRole> findByRoles(int[] roles);
}
