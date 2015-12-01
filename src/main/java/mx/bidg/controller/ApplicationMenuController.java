package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CSystems;
import mx.bidg.model.Users;
import mx.bidg.service.ApplicationMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/11/15.
 */
@Controller
@RequestMapping("app-menu")
public class ApplicationMenuController {

    @Autowired
    ApplicationMenuService appMenuService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getAppMenu(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        Users user = (Users) session.getAttribute("user");
        List<CSystems> systems = (List<CSystems>) session.getAttribute("app-menu");

        if (systems == null) {
            systems = appMenuService.buildMenuForRoles(user.getUsersRoleList());
            session.setAttribute("app-menu", systems);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(systems), HttpStatus.OK);
    }
}
