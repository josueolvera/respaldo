package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRoles;
import mx.bidg.model.EmployeesHistory;
import mx.bidg.model.Users;
import mx.bidg.service.CRolesService;
import mx.bidg.service.EmployeesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * Created by jolvera on 24/06/16.
 */
@Controller
@RequestMapping("/roles")
public class CRolesController {

    @Autowired
    CRolesService cRolesService;

    @Autowired
    EmployeesHistoryService employeesHistoryService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CRoles> roles = cRolesService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(roles), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{idRole}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable int idRole) throws IOException{
        CRoles roles = cRolesService.findById(idRole);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(roles), HttpStatus.OK);
    }

    @RequestMapping(value = "/employee-history/{idEH}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findRoleByEmployeeHistory(@PathVariable Integer idEH) throws IOException{
        EmployeesHistory employeesHistory = employeesHistoryService.findById(idEH);
        CRoles rol = cRolesService.findById(employeesHistory.getIdRole());
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(rol), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-roles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        CRoles cRoles = new CRoles();
        cRoles.setRoleName(node.get("name").asText());
        cRoles.setCreationDate(LocalDateTime.now());
        cRoles.setUsername(user.getUsername());

        cRolesService.save(cRoles);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cRolesService.findAll())
                ,HttpStatus.OK);
    }
}
