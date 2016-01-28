package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Employees;
import mx.bidg.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 26/01/16.
 */
@Controller
@RequestMapping("employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findEmployeesByDwEnterprise(
            @RequestParam(name = "idDwEnterprise", required = false) String idDwEnterprise
    ) throws IOException {
        List<Employees> employees;

        if (idDwEnterprise != null) {
            Integer id = Integer.parseInt(idDwEnterprise);
             employees = employeesService.findSimpleBy(new DwEnterprises(id));
        } else {
            employees = employeesService.findAll();
        }

        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employees),
                HttpStatus.OK
        );
    }
}
