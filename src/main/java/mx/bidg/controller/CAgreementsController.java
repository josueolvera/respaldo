package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAgreements;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.DwEnterprisesAgreements;
import mx.bidg.service.CAgreementsService;
import mx.bidg.service.DwEnterprisesAgreementsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
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
    DwEnterprisesAgreementsService dwEnterprisesAgreementsService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

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
            cAgreements.setAgreementName(node.get("agreementName").asText());
            cAgreements.setUploadedDate(LocalDateTime.now());
            cAgreements.setAgreementNameClean(agreementNameClean);
            cAgreements.setStatus(1);
            cAgreements.setIdAccessLevel(1);
            cAgreementsService.save(cAgreements);
            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cAgreements),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("El nombre ya existe", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{idAgreement}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity <String> saveDwEnterpriseAgreement(@PathVariable Integer idAgreement, @RequestBody String data) throws IOException{
        CAgreements agreement = cAgreementsService.findById(idAgreement);
        JsonNode node = mapper.readTree(data);
        DwEnterprises dwEnterprise = new DwEnterprises(node.get("idDwEnterprise").asInt());

        DwEnterprisesAgreements dwEnterprisesAgreements = new DwEnterprisesAgreements();

        dwEnterprisesAgreements.setDwEnterprise(dwEnterprise);
        dwEnterprisesAgreements.setAgreement(agreement);
        dwEnterprisesAgreements.setIdAccessLevel(1);

        dwEnterprisesAgreementsService.save(dwEnterprisesAgreements);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEnterprisesAgreements),HttpStatus.OK);
    }
}
