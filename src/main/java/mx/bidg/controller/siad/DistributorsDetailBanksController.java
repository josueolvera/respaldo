package mx.bidg.controller.siad;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DistributorsDetailBanks;
import mx.bidg.model.Users;
import mx.bidg.service.DistributorsDetailBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<String> findAll() throws Exception {
        List<DistributorsDetailBanks> distributorsDetailBanksList = distributorsDetailBanksService.findAll();
        return  new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanksList), HttpStatus.OK);
    }

    @RequestMapping(value = "/distributor/{idDistributor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDistributor(@PathVariable Integer idDistributor) throws Exception{
        List<DistributorsDetailBanks> distributorsDetailBanksList = distributorsDetailBanksService.getByDistributor(idDistributor);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(distributorsDetailBanksList), HttpStatus.OK);
    }

}
