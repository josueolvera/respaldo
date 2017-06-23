package mx.bidg.controller.siad;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.DistributorsDetailBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Leonardo on 13/06/2017.
 */
@Controller
@RequestMapping("distributors-detail-banks")
public class DistributorsDetailBanksController {

    @Autowired
    private DistributorsDetailBanksService distributorsDetailBanksService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    ResponseEntity<String> findAll() throws Exception {
        List<DistributorsDetailBanks> distributorsDetailBanksList = distributorsDetailBanksService.findAll();
        return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanksList), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributor(@PathVariable Integer idDistributor) throws Exception{
        List<DistributorsDetailBanks> distributorsDetailBanksList = distributorsDetailBanksService.getByDistributor(idDistributor);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanksList), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session) throws IOException{
        Users user = (Users) session.getAttribute("user");
        JsonNode node = mapper.readTree(data);
        DistributorsDetailBanks distributorsDetailBanks = new DistributorsDetailBanks();
        distributorsDetailBanks.setBanks(new CBanks(node.get("idBank").asInt()));
        distributorsDetailBanks.setCurrencies(new CCurrencies(node.get("idCurrency").asInt()));
        distributorsDetailBanks.setDistributors(new CDistributors(node.get("idDistributor").asInt()));
        distributorsDetailBanks.setAccountNumber(node.get("accountnumber").asText());
        distributorsDetailBanks.setAmount(node.get("amount").decimalValue());
        distributorsDetailBanks.setCreationDate(LocalDateTime.now());
        distributorsDetailBanks.setUsername(user.getUsername());

        distributorsDetailBanksService.save(distributorsDetailBanks);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class)
                .writeValueAsString(distributorsDetailBanksService.findAll()),HttpStatus.OK);
    }

    @RequestMapping(value = "/account-number", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateAmount(@RequestBody String  data) throws IOException{
        JsonNode node = mapper.readTree(data);
        DistributorsDetailBanks distributorsDetailBanks = distributorsDetailBanksService.findByAccountNumber(node.get("accountNumber").asText());
        BigDecimal newAmount = distributorsDetailBanks.getAmount().add(node.get("amount").decimalValue());
        distributorsDetailBanks.setAmount(newAmount);
        distributorsDetailBanks = distributorsDetailBanksService.update(distributorsDetailBanks);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanks), HttpStatus.OK);
    }

}
