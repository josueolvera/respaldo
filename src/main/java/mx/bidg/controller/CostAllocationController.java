package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.CCustomersService;
import mx.bidg.service.CostAllocationService;
import mx.bidg.service.DwEmployeesService;
import mx.bidg.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Kevin Salvador on 20/12/2016.
 */
@Controller
@RequestMapping("cost-allocation")
public class CostAllocationController {

    @Autowired
    private CostAllocationService costAllocationService;

    @Autowired
    private CCustomersService cCustomersService;

    @Autowired
    private DwEmployeesService dwEmployeesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CostAllocation> perceptionsDeductionsList = costAllocationService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(perceptionsDeductionsList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String>save(@RequestBody String data,HttpSession session) throws IOException{

        Users user = (Users) session.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        DwEmployees dwEmployees = dwEmployeesService.findById(node.get("idDwEmployee").asInt());

        for (JsonNode jnode: node.get("employeeClients")) {

            CostAllocation ca= new CostAllocation();

            if (dwEmployees != null){
                if (dwEmployees.getEmployee() != null){
                    ca.setEmployee(dwEmployees.getEmployee());
                }
            }
            CCustomers cc = cCustomersService.findById(jnode.get("customer").get("idCustomer").asInt());
            ca.setCreationDate(LocalDateTime.now());
            ca.setCustomer(cc);
            ca.setPercentage(new BigDecimal(jnode.get("percentage").asDouble()));
            ca.setUsername(user.getUsername());

            costAllocationService.save(ca);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(costAllocationService.findAll()), HttpStatus.OK);
    }
}
