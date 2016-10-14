package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BonusCommisionableEmployee;
import mx.bidg.model.Employees;
import mx.bidg.service.BonusCommisionableEmployeeService;
import mx.bidg.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by josue on 12/10/2016.
 */
@Controller
@RequestMapping("employee-bonus")
public class BonusCommisionableEmployeeController {

    @Autowired
    EmployeesService employeesService;

    @Autowired
    BonusCommisionableEmployeeService bonusCommisionableEmployeeService;

    @Autowired
    ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll ()throws IOException{
        List<BonusCommisionableEmployee> bonusCommisionableEmployeeList = bonusCommisionableEmployeeService.findAll();

        for (BonusCommisionableEmployee bonusCommisionableEmployee : bonusCommisionableEmployeeList){
            Employees employee = employeesService.findById(bonusCommisionableEmployee.getIdEmployee());
            bonusCommisionableEmployee.setEmployees(employee);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(bonusCommisionableEmployeeList), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{idBonusCommissionableEmployee}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> delete(@PathVariable Integer idBonusCommissionableEmployee)throws Exception{
        BonusCommisionableEmployee bonusCommisionableEmployee = bonusCommisionableEmployeeService.findById(idBonusCommissionableEmployee);
        bonusCommisionableEmployeeService.delete(bonusCommisionableEmployee);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(bonusCommisionableEmployeeService.findAll()), HttpStatus.OK);
    }
}
