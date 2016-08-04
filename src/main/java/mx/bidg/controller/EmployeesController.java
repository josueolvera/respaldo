package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.dao.DwEmployeesDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private Environment env;

    @Autowired
    private EmployeeDocumentsService employeeDocumentsService;

    @Autowired
    private EmployeesHistoryService employeesHistoryService;

    @Autowired
    private CEmployeeDocumentsTypesService cEmployeeDocumentsTypesService;

    @Autowired
    private CEducationService cEducationService;

    @Autowired
    private CStatusMaritalService cStatusMaritalService;

    @Autowired
    private CBanksService cBanksService;

    @Autowired
    private CCurrenciesService currenciesService;

    @Autowired
    private CRolesService cRolesService;

    @Autowired
    private CContractTypeService cContractTypeService;

    @Autowired
    private CEmployeeTypeService cEmployeeTypeService;

    @Autowired
    private CGendersService cGendersService;

    @Autowired
    private ObjectMapper mapper;

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

    @RequestMapping(value = "/{idEmployee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idEmployee) throws IOException{
        Employees employee = employeesService.findById(idEmployee);
        return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employee), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode jnode = mapper.readTree(data);

        Users user = (Users) session.getAttribute("user");

        Employees employee = new Employees();
        Accounts accounts;

        LocalDateTime joinDate = (jnode.get("joinDate") == null || jnode.findValue("joinDate").asText().equals("")) ? null :
                LocalDateTime.parse(jnode.get("joinDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime birthday = LocalDateTime.parse(jnode.get("birthday").asText(), DateTimeFormatter.ISO_DATE_TIME);

        employee.setStreet(jnode.get("street").asText());
        employee.setBirthday(birthday.toLocalDate());
        employee.setCellPhone(jnode.get("cellPhone").asText());
        employee.setContractType(new CContractType(jnode.get("contractType").asInt()));
        employee.setBirthplace(jnode.get("birthPlace").asText());
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
        employee.setEmployeeType(new CEmployeeType(jnode.get("employeeType").asInt()));
        employee.setSize(new CSizes(jnode.get("idSize").asInt()));
        employee.setSizeNumber(jnode.get("sizeNumber").asInt());
        employee.setState(jnode.get("state").asText());
        employee.setExteriorNumber(jnode.get("exteriorNumber").asText());
        employee.setInteriorNumber(jnode.get("interiorNumber").asText());
        employee.setGender(new CGenders(jnode.get("gender").asInt()));
        employee.setHomePhone(jnode.get("homePhone").asText());
        employee.setImss(jnode.get("imss").asText());
        employee.setInfonavitNumber(jnode.get("infonavitNumber").asText());
        employee.setStatusMarital(new CStatusMarital(jnode.get("idStatusMarital").asInt()));
        employee.setSalary(new BigDecimal(jnode.get("salary").asInt()));
        employee.setStatus(1);
        employee.setMail(jnode.get("mail").asText());
        employee.setSistarh(jnode.get("sistarh").asText());
        employee.setJoinDate(joinDate);

        employee = employeesService.save(employee);

        for (JsonNode node : jnode.get("employeeAccountList")){
            EmployeesAccounts employeesAccounts = new EmployeesAccounts();
            employeesAccounts.setIdAccessLevel(1);

            Accounts account = new Accounts();
            account.setAccountNumber(node.get("accountNumber").asText());
            account.setAccountClabe(node.get("accountClabe").asText());
            account.setBank(cBanksService.findById(node.get("idBank").asInt()));
            account.setCurrencies(currenciesService.geById(1));
            account.setAccountType(CAccountsTypes.DEFINITIVA);
            account.setIdAccessLevel(1);

            employeesAccounts.setEmployee(employee);
            employeesAccounts.setAccount(account);

            accountsService.save(account);
            employeesAccountsService.save(employeesAccounts);
        }


        DwEmployees dwEmployees = new DwEmployees();

        dwEmployees.setEmployee(employee);
        dwEmployees.setDwEnterprise(dwEnterprisesService.findByBranchAndArea(jnode.get("dwEmployees").get("branch").get("idBranch").asInt(), jnode.get("dwEmployees").get("area").get("idArea").asInt()));
        dwEmployees.setRole(cRolesService.findById(jnode.get("dwEmployees").get("role").get("idRole").asInt()));
        dwEmployees.setCreationDate(LocalDateTime.now());
        dwEmployees = dwEmployeesService.save(dwEmployees);

        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());
        accounts = employeesAccounts.getAccount();

        CActionTypes cActionType = CActionTypes.ALTA;
        employeesHistoryService.save(dwEmployees,cActionType, accounts, user);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employee), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> update(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode jnode = mapper.readTree(data);
        Accounts accounts;

        Users user = (Users) session.getAttribute("user");

        Employees employee = employeesService.findById(jnode.get("idEmployee").asInt());
        Accounts oldAccount = accountsService.findById(jnode.get("idAccount").asInt());
        oldAccount.setDeleteDay(LocalDateTime.now());
        accountsService.update(oldAccount);

        LocalDateTime joinDate = (jnode.get("joinDate") == null || jnode.findValue("joinDate").asText().equals("")) ? null :
                LocalDateTime.parse(jnode.get("joinDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime birthday = LocalDateTime.parse(jnode.get("birthday").asText(), DateTimeFormatter.ISO_DATE_TIME);

        employee.setStreet(jnode.get("street").asText());
        employee.setBirthday(birthday.toLocalDate());
        employee.setCellPhone(jnode.get("cellPhone").asText());
        employee.setContractType(cContractTypeService.findByContractTypeName(jnode.get("contractType").asText()));
        employee.setBirthplace(jnode.get("birthPlace").asText());
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
        employee.setEmployeeType(cEmployeeTypeService.findByEmployeeTypeName(jnode.get("employeeType").asText()));
        employee.setSize(new CSizes(jnode.get("idSize").asInt()));
        employee.setSizeNumber(jnode.get("sizeNumber").asInt());
        employee.setState(jnode.get("state").asText());
        employee.setExteriorNumber(jnode.get("exteriorNumber").asText());
        employee.setInteriorNumber(jnode.get("interiorNumber").asText());
        employee.setGender(cGendersService.findByGenderName(jnode.get("gender").asText()));
        employee.setHomePhone(jnode.get("homePhone").asText());
        employee.setImss(jnode.get("imss").asText());
        employee.setInfonavitNumber(jnode.get("infonavitNumber").asText());
        employee.setStatusMarital(new CStatusMarital(jnode.get("idStatusMarital").asInt()));
        employee.setSalary(new BigDecimal(jnode.get("salary").asInt()));
        employee.setStatus(1);
        employee.setMail(jnode.get("mail").asText());
        employee.setJoinDate(joinDate);

        employee = employeesService.update(employee);

        for (JsonNode node : jnode.get("employeeAccountList")){
            EmployeesAccounts employeesAccounts = new EmployeesAccounts();
            employeesAccounts.setIdAccessLevel(1);

            Accounts account = new Accounts();
            account.setAccountNumber(node.get("accountNumber").asText());
            account.setAccountClabe(node.get("accountClabe").asText());
            account.setBank(cBanksService.findById(node.get("idBank").asInt()));
            account.setCurrencies(currenciesService.geById(1));
            account.setAccountType(CAccountsTypes.DEFINITIVA);
            account.setIdAccessLevel(1);

            employeesAccounts.setEmployee(employee);
            employeesAccounts.setAccount(account);

            accountsService.save(account);
            employeesAccountsService.save(employeesAccounts);
        }

        DwEmployees dwEmployee = dwEmployeesService.findById(jnode.get("idDwEmployee").asInt());
        dwEmployeesService.delete(dwEmployee);

        DwEmployees dwEmployees = new DwEmployees();

        dwEmployees.setEmployee(employee);
        dwEmployees.setDwEnterprise(dwEnterprisesService.findById(jnode.get("dwEmployees").get("area").get("dwEnterprise").get("idDwEnterprise").asInt()));
        dwEmployees.setRole(cRolesService.findById(jnode.get("dwEmployees").get("role").get("idRole").asInt()));
        dwEmployees.setCreationDate(LocalDateTime.now());
        dwEmployees = dwEmployeesService.save(dwEmployees);

        EmployeesAccounts employeesAccounts = employeesAccountsService.findEmployeeAccountActive(employee.getIdEmployee());
        accounts = employeesAccounts.getAccount();

        CActionTypes cActionType = CActionTypes.MODIFICAION;
        employeesHistoryService.save(dwEmployees,cActionType, accounts, user);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employee), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idEmployee}/attachments", method = RequestMethod.POST)
    public ResponseEntity<String> attachDocuments(@PathVariable Integer idEmployee, @RequestBody String data) throws Exception {
        employeeDocumentsService.save(data,idEmployee);
        return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
    }

    @RequestMapping(value = "/validate/rfc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> validateRfc(@RequestParam(name = "rfc", required = true) String rfc) throws IOException {
        Employees employees = employeesService.findByRfc(rfc);

        if(employees != null){
            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employees), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Empleado no encontrado", HttpStatus.OK);
    }

    @RequestMapping(value = "/validate/curp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> validateCurp(@RequestParam(name = "curp", required = true) String curp) throws IOException {
        Employees employees = employeesService.findByCurp(curp);

        if(employees != null){
            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(employees), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Empleado no encontrado", HttpStatus.OK);
    }


    private EmployeeDocuments findDocument(Integer idDocumentType, List<EmployeeDocuments> documents) {
        if (documents.isEmpty()) {
            return null;
        }
        for (EmployeeDocuments document : documents) {
            if (document.getIdDocumentType().equals(idDocumentType)) {
                return document;
            }
        }

        return null;
    }

    private void saveAttachDocuments(Integer idEmployee, HttpServletRequest request) throws Exception{
        String SAVE_PATH = env.getRequiredProperty("employees.documents_dir");
        String[] fileMediaTypes = env.getRequiredProperty("employees.attachments.media_types").split(",");
        List<EmployeeDocuments> documents = employeeDocumentsService.findByIdEmployee(idEmployee);
        Pattern pattern = Pattern.compile("(\\d+)");

        for (Part filePart: request.getParts()) {
            boolean isValidMediaType = false;

            if (! filePart.getName().matches("^file-type-[0-12]+$")) {
                continue;
            }
            if (filePart.getSize() <= 0) {
                continue;
            }

            for (String mediaType : fileMediaTypes) {
                if (filePart.getContentType().equals(mediaType)) {
                    isValidMediaType = true;
                    break;
                }
            }

            if (! isValidMediaType) {
                throw new ValidationException("Tipo de archivo no admitido", "Tipo de archivo no admitido");
            }

            Matcher matcher = pattern.matcher(filePart.getName());
            matcher.find();
            Integer idDocumentType = Integer.parseInt(matcher.group(0));

            String destDir = "/employee_" + idEmployee;
            String destFile = destDir + "/Documento." + idDocumentType +
                    "." + Calendar.getInstance().getTimeInMillis();

            File dir = new File(SAVE_PATH + destDir);
            if (! dir.exists()) {
                dir.mkdir();
            }

            OutputStream outputStream = new FileOutputStream(new File(SAVE_PATH + destFile));
            InputStream inputStream = filePart.getInputStream();
            int read;
            byte[] buffer = new byte[1024];
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            EmployeeDocuments document = new EmployeeDocuments();
            document.setEmployee(new Employees(idEmployee));
            document.setcDocumentType(new CEmployeeDocumentsTypes(idDocumentType));
            document.setDocumentUrl(destFile);
            document.setDocumentName(filePart.getSubmittedFileName());
            document.setUploadingDate(LocalDateTime.now());
            document.setCurrentDocument(1);
            document.setIdAccessLevel(1);
           // employeeDocumentsService.save(document);

            EmployeeDocuments oldDocument = this.findDocument(idDocumentType, documents);
            if (oldDocument != null) {
                oldDocument.setCurrentDocument(0);
                employeeDocumentsService.update(oldDocument);
            }
        }
    }

}