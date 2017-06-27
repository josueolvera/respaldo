package mx.bidg.controller.siad;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.RequestBudgetSpending;
import mx.bidg.service.RequestBudgetSpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Serch on 23/06/2017.
 */
@Controller
@RequestMapping("request-budget-spending")
public class RequestBudgetSpendingController {

    @Autowired
    private ObjectMapper mapper;

     @Autowired
     private RequestBudgetSpendingService requestBudgetSpendingService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws IOException{
        List<RequestBudgetSpending> requestBudgetSpendings = requestBudgetSpendingService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(requestBudgetSpendings), HttpStatus.OK);
    }

    @RequestMapping(value = "/total-amount-month/{idDistributorCostCenter}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getTotalAmountByMonth(@PathVariable Integer idDistributorCostCenter) throws IOException{
        BigDecimal amount = requestBudgetSpendingService.getAmountDistributorCostCenter(idDistributorCostCenter);
        BigDecimal amountExpended = requestBudgetSpendingService.getAmountSpendedDistributorCostCenter(idDistributorCostCenter);

        BigDecimal totalAmount;

        if (amount != null){
            if (amountExpended != null){
                totalAmount = amount.subtract(amountExpended).setScale(2, BigDecimal.ROUND_HALF_UP);
            }else {
                totalAmount = amount;
            }
        }else {
            totalAmount = new BigDecimal(0.00);
        }

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(totalAmount), HttpStatus.OK);
    }
}
