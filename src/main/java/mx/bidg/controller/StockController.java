package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Stocks;
import mx.bidg.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Controller
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockService stockService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> findAll() throws IOException {
        List<Stocks> stock = stockService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/{idDistributor}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> findByDistributor(@PathVariable int idDistributor) throws IOException {
        List<Stocks> stock = stockService.findByDistributor(idDistributor);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(stock),
                HttpStatus.OK
        );
    }
}
