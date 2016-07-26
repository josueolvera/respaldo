package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Refunds;
import mx.bidg.model.Users;
import mx.bidg.service.RefundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by gerardo8 on 25/07/16.
 */
@Controller
@RequestMapping("/refunds")
public class RefundsController {

    @Autowired
    private RefundsService refundsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession httpSession) throws Exception {

        Users user = (Users) httpSession.getAttribute("user");
        Refunds refunds = refundsService.save(data, user);

        return new ResponseEntity<>(
                mapper.writerWithView(
                        JsonViews.Embedded.class
                ).writeValueAsString(refunds.getIdRefund()),
                HttpStatus.OK
        );
    }
}
