package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AuthorizationCostCenter;
import mx.bidg.model.Users;
import mx.bidg.service.AuthorizationCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Kevin Salvador on 15/03/2017.
 */
@Controller
@RequestMapping("authorizathion-costcenter")
public class AuthorizationCostCenterController {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private AuthorizationCostCenterService authorizationCostCenterService;

    @RequestMapping(value = "/cost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getBudgetsbyCC(@RequestParam(name = "cost_center", required = false) Integer idCostCenter,
                                                 @RequestParam(name = "year", required = false) Integer year) throws Exception {
        AuthorizationCostCenter authorizationCostCenter = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter, year);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenter));
    }

    @RequestMapping(value = "/modify",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> setBudgetModify(@RequestParam(name = "cost_center", required = false) Integer idCostCenter,
                                                  @RequestParam(name = "year", required = false)Integer year,
                                                  HttpSession session) throws Exception {
        Users user = (Users) session.getAttribute("user");
        List<AuthorizationCostCenter> authorizationCostCenter = authorizationCostCenterService.findAll();
        AuthorizationCostCenter  a = authorizationCostCenterService.findByIdCostCenterAndYear(idCostCenter,year);
        a.setModify(1);
        a.setUsers(user);
        authorizationCostCenterService.update(a);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(authorizationCostCenter));
    }

}
