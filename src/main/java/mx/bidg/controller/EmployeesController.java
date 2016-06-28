package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private EmployeesAccountsService employeesAccountsService;

    @Autowired
    private DwEnterprisesService dwEnterprisesService;

    @Autowired
    private DwEmployeesService dwEmployeesService;

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

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data)throws IOException{
        JsonNode jnode = mapper.readTree(data);

        Employees employee = new Employees();

        LocalDateTime joinDate = (jnode.get("joinDate") == null || jnode.findValue("joinDate").asText().equals("")) ? null :
                LocalDateTime.parse(jnode.get("joinDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime birthday = LocalDateTime.parse(jnode.get("birthday").asText(), DateTimeFormatter.ISO_DATE_TIME);

        employee.setStreet(jnode.get("street").asText());
        employee.setBirthday(birthday.toLocalDate());
        employee.setCellPhone(jnode.get("cellPhone").asText());
        employee.setContractType(jnode.get("contractType").asInt());
        employee.setBirthPlace(jnode.get("birthPlace").asText());
        employee.setColonia(jnode.get("colonia").asText());
        employee.setCity(jnode.get("city").asText());
        employee.setClaveSap(jnode.get("claveSap").asText());
        employee.setCurp(jnode.get("curp").asText());
        employee.setRfc(jnode.get("rfc").asText());
        employee.setMotherName(jnode.get("motherName").asText());
        employee.setFatherName(jnode.get("fatherName").asText());
        employee.setFirstName(jnode.get("firstName").asText());
        employee.setMiddleName(jnode.get("middleName").asText());
        employee.setParentalLast(jnode.get("parentalLast").asText());
        employee.setMotherLast(jnode.get("motherLast").asText());
        employee.setPostcode(jnode.get("postcode").asText());
        employee.setEducation(new CEducation(jnode.get("idEducation").asInt()));
        employee.setEmployeeType(jnode.get("employeeType").asInt());
        employee.setSize(jnode.get("size").asText());
        employee.setSizeNumber(jnode.get("sizeNumber").asInt());
        employee.setState(jnode.get("state").asText());
        employee.setExteriorNumber(jnode.get("exteriorNumber").asText());
        employee.setInteriorNumber(jnode.get("interiorNumber").asText());
        employee.setGender(jnode.get("gender").asInt());
        employee.setHomePhone(jnode.get("homePhone").asText());
        employee.setImss(jnode.get("imss").asText());
        employee.setInfonavitNumber(jnode.get("infonavitNumber").asText());
        employee.setStatusMarital(new CStatusMarital(jnode.get("idStatusMarital").asInt()));
        employee.setSalary(jnode.get("salary").decimalValue());
        employee.setStatus(1);
        employee.setMail(jnode.get("mail").asText());
        employee.setJoinDate(joinDate);

        employeesService.save(employee);

        for (JsonNode node : jnode.get("employeeAccountList")){
            EmployeesAccounts employeesAccounts = new EmployeesAccounts();
            employeesAccounts.setIdAccessLevel(1);

            Accounts account = new Accounts();
            account.setAccountNumber(node.get("accountNumber").asText());
            account.setAccountClabe(node.get("accountClabe").asText());
            account.setBank(new CBanks(node.get("idBank").asInt()));
            account.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
            account.setAccountType(CAccountsTypes.DEFINITIVA);
            account.setIdAccessLevel(1);

            employeesAccounts.setEmployee(employee);
            employeesAccounts.setAccount(account);

            accountsService.save(account);
            employeesAccountsService.save(employeesAccounts);
        }


        DwEmployees dwEmployees = new DwEmployees();

        dwEmployees.setEmployee(employee);
        dwEmployees.setDwEnterprise(new DwEnterprises(jnode.get("dwEmployees").get("area").get("dwEnterprise").get("idDwEnterprise").asInt()));
        dwEmployees.setRole(new CRoles(jnode.get("dwEmployees").get("role").asInt()));
        dwEmployees.setCreationDate(LocalDateTime.now());
        dwEmployeesService.save(dwEmployees);


        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employee), HttpStatus.OK);
    }
}
