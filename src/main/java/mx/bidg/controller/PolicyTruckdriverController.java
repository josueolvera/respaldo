package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.PolicyTruckdriver;
import mx.bidg.service.CTypeSecureService;
import mx.bidg.service.PolicyTruckdriverService;
import org.apache.http.HttpStatus;
import org.apache.xml.serialize.Method;
import org.exolab.castor.types.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by PC_YAIR on 11/01/2017.
 */

@Controller
@RequestMapping("/policy-truckdriver")
public class PolicyTruckdriverController {

    @Autowired
    private PolicyTruckdriverService policyTruckdriverService;

    @Autowired
    private CTypeSecureService cTypeSecureService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception {
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverService.findAll()));
    }


    @RequestMapping(value = "/get-by-date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByDate(@RequestParam(name = "startDate", required = true) String startDate) throws Exception {

        LocalDate dStartValidity = (startDate == null || startDate.equals("")) ? null :
                LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);

        List<PolicyTruckdriver> policyTruckdriverList = policyTruckdriverService.findByDStartValidity(dStartValidity);

        if(!policyTruckdriverList.isEmpty()){
            for (PolicyTruckdriver policyTruckdriver : policyTruckdriverList){
                BigDecimal priceSale = null;
                BigDecimal cost = null;
                BigDecimal total;
                if (policyTruckdriver.getcTypeSecure() != null){
                    if (policyTruckdriver.getcTypeSecure().getPriceSale() != null){
                        policyTruckdriver.setPriceSale(policyTruckdriver.getcTypeSecure().getPriceSale());
                        priceSale = policyTruckdriver.getcTypeSecure().getPriceSale();
                    }
                    if (policyTruckdriver.getcTypeSecure().getCommission() != null){
                        if (priceSale != null){
                            cost = priceSale.multiply(policyTruckdriver.getcTypeSecure().getCommission());
                        }
                        policyTruckdriver.setCost(cost);
                    }
                    if (cost != null){
                        total = priceSale.subtract(cost);
                        policyTruckdriver.setTotal(total);
                    }
                }
            }
        }

        return ResponseEntity.ok( mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(policyTruckdriverList));
    }


}
