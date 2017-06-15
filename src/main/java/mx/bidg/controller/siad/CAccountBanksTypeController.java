package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CAccountBanksType;
import mx.bidg.model.Users;
import mx.bidg.service.CAccountBanksTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Leonardo on 12/06/2017.
 */
@Controller
@RequestMapping("account-banks-type")
public class CAccountBanksTypeController {

    @Autowired
    private CAccountBanksTypeService cAccountBanksTypeService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    ResponseEntity<String> findAll () throws Exception {
        List<CAccountBanksType> accountBanksTypeList = cAccountBanksTypeService.findAll();
        System.out.println("Estoy aqui!");
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(accountBanksTypeList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save (@RequestBody String data, HttpSession session) throws Exception{
        Users users = (Users) session.getAttribute("user");

        JsonNode node = mapper.readTree(data);

        CAccountBanksType accountBanksType = new CAccountBanksType();
        accountBanksType.setNameBankType(node.get("NameBankType").asText());
        accountBanksType.setCreationDate(LocalDateTime.now());
        accountBanksType.setUsername(users.getUsername());

        cAccountBanksTypeService.save(accountBanksType);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cAccountBanksTypeService.findAll()), HttpStatus.OK);
    }
}


