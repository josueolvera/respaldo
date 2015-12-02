package mx.bidg.service;

import mx.bidg.model.CSystems;
import mx.bidg.model.UsersRole;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 1/12/15.
 */
public interface ApplicationMenuService {
    List<CSystems> buildMenuForRoles(List<UsersRole> usersRoles);
}
