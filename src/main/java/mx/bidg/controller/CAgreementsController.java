package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAgreements;
import mx.bidg.model.CAgreementsGroups;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.GroupsAgreements;
import mx.bidg.service.CAgreementsGroupsService;
import mx.bidg.service.CAgreementsService;
import mx.bidg.service.GroupsAgreementsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jolvera on 17/06/16.
 */
@Controller
@RequestMapping("agreements")
public class CAgreementsController {

    @Autowired
    CAgreementsService cAgreementsService;

    @Autowired
    CAgreementsGroupsService cAgreementsGroupsService;

    @Autowired
    GroupsAgreementsService groupsAgreementsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<CAgreements> agreements = cAgreementsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(agreements), HttpStatus.OK);
    }

    @RequestMapping(value="/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public  ResponseEntity<String> save(@RequestBody String data) throws IOException{
        JsonNode node = mapper.readTree(data);
        String clearAgreementName = StringUtils.stripAccents(node.get("agreementName").asText());
        String agreementNameClean= clearAgreementName.replaceAll("\\W", "").toUpperCase();
        if(!cAgreementsService.diferentAgreement(agreementNameClean)){
            CAgreements cAgreements = new CAgreements();
            cAgreements.setAgreementName(node.get("agreementName").asText().toUpperCase());
            cAgreements.setUploadedDate(LocalDateTime.now());
            cAgreements.setAgreementNameClean(agreementNameClean);
            cAgreements.setStatus(1);
            cAgreements.setIdAccessLevel(1);
            cAgreements = cAgreementsService.save(cAgreements);

            List<CAgreementsGroups> agreementsGroups = cAgreementsGroupsService.findGroupsActives();

            for (CAgreementsGroups agreementGroup : agreementsGroups){
                GroupsAgreements groupsAgreements = new GroupsAgreements();
                groupsAgreements.setAgreement(cAgreements);
                groupsAgreements.setAgreementGroup(agreementGroup);
                groupsAgreements.setIdAccessLevel(1);
                groupsAgreements.setHasAgreement(false);
                groupsAgreementsService.save(groupsAgreements);
            }

            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cAgreements),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("El nombre ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/low-date/{idAgreement}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> lowDate(@PathVariable Integer idAgreement)throws IOException{

        cAgreementsService.lowDate(idAgreement);

        return new ResponseEntity<>("Convenio Eliminado",HttpStatus.OK);
    }

    @RequestMapping(value = "/report-agreements", method = RequestMethod.GET)
    public ResponseEntity<String> report(@RequestParam(required = true, name = "file_name") String fileName, HttpServletResponse response) throws IOException{

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName +".xls\"");

        OutputStream outputStream = response.getOutputStream();
        cAgreementsService.agreementsReport(outputStream);
        outputStream.flush();
        outputStream.close();
        return new ResponseEntity<>("Reporte", HttpStatus.OK);
    }
}
