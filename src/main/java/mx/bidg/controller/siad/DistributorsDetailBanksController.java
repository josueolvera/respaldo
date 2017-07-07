package mx.bidg.controller.siad;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.*;
import mx.bidg.service.DistributorsDetailBanksHistoryService;
import mx.bidg.service.DistributorsDetailBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private DistributorsDetailBanksHistoryService distributorsDetailBanksHistoryService;

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
            distributorsDetailBanks.setAccountClabe(node.get("accountclabe").asText());
            distributorsDetailBanks.setAccountNumber(node.get("accountnumber").asText());
            distributorsDetailBanks.setAmount(node.get("amount").decimalValue());
            distributorsDetailBanks.setCreationDate(LocalDateTime.now());
            distributorsDetailBanks.setUsername(user.getUsername());

            distributorsDetailBanksService.save(distributorsDetailBanks);

            return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class)
                    .writeValueAsString(distributorsDetailBanksService.findAll()), HttpStatus.OK);
    }

    @RequestMapping(value = "/account-number", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> updateAmount(@RequestBody String  data, HttpSession session) throws IOException{
        JsonNode node = mapper.readTree(data);
        Users user = (Users) session.getAttribute("user");

        DistributorsDetailBanks distributorsDetailBanks = distributorsDetailBanksService.findByAccountNumber(node.get("accountNumber").asText());
        Integer idDistributor = distributorsDetailBanks.getIdDistributor();

        String amount = node.get("amount").asText().replace(",", "");
        BigDecimal newAmount = distributorsDetailBanks.getAmount().add(new BigDecimal(amount));

        distributorsDetailBanks.setAmount(newAmount);
        distributorsDetailBanks = distributorsDetailBanksService.update(distributorsDetailBanks);

        DistributorsDetailBanksHistory distributorsDetailBanksHistory = distributorsDetailBanksHistoryService.saveData(data, user, idDistributor);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanks), HttpStatus.OK);
    }

    @RequestMapping(value = "/find-account-number", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findLikeAccountNumber(@RequestParam(name = "accountNumber", required = false) String accountNumber) throws IOException{
        DistributorsDetailBanks distributorsDetailBanks = distributorsDetailBanksService.findLikeAccountNumber(accountNumber);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanks), HttpStatus.OK);
    }

    @RequestMapping(value = "/report-distributors-detail-banks", method = RequestMethod.GET)
    public ResponseEntity<String> reporteSemanal(HttpServletResponse response, @RequestParam(name = "startDate", required = true) String startDate
        , @RequestParam(name = "endDate", required = true) String endDate ) throws IOException {

    LocalDateTime ofDate = (startDate == null || startDate.equals("")) ? null :
            LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
    LocalDateTime untilDate = (endDate == null || endDate.equals("")) ? null :
            LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);

    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=\"ReporteDetalleDeBanco.xls"+ "\"");
    OutputStream outputStream = response.getOutputStream();
    distributorsDetailBanksService.exportFile(outputStream, ofDate, untilDate);
    outputStream.flush();
    outputStream.close();

    return  ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanksService.findAll()));
    }

}
