package mx.bidg.service;

import mx.bidg.model.UsersRole;
import mx.bidg.model.ViewsRole;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/11/15.
 */
public interface ViewsRolesService {
    List<ViewsRole> findViewsRolesFor(List<UsersRole> roles);
}
