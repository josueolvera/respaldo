package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface UsersService {
    Users findByUserName(String username);
    Users verifyUserLogin(Users user);
    List<Users> findAll();
    Users findById(Integer idUser);
}
