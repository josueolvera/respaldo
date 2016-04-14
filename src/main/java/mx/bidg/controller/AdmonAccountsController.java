package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AdmonAccounts;
import mx.bidg.service.AdmonAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Controller
@RequestMapping("admon-accounts")
public class AdmonAccountsController {

    @Autowired
    AdmonAccountsService admonAccountsService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<AdmonAccounts> admonAccounts = admonAccountsService.findAll();
        return new ResponseEntity<String>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(admonAccounts),
                HttpStatus.OK
        );
    }

}
