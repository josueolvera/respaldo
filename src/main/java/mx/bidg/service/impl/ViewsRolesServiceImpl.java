package mx.bidg.service.impl;

import mx.bidg.dao.ViewsRolesDao;
import mx.bidg.model.UsersRole;
import mx.bidg.model.ViewsRole;
import mx.bidg.service.ViewsRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/11/15.
 */
@Service
@Transactional
public class ViewsRolesServiceImpl implements ViewsRolesService {

    @Autowired
    ViewsRolesDao viewsRolesDao;

    @Override
    public List<ViewsRole> findViewsRolesFor(List<UsersRole> roles) {
        int[] rolesIds = new int[roles.size()];
        for (int i = 0; i < rolesIds.length; i++) {
            rolesIds[i] = roles.get(i).getIdSystemRole().getIdSystemRole();
        }

        return viewsRolesDao.findByRoles(rolesIds);
    }
}
