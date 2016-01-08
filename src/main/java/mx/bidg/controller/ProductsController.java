package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CProducts;
import mx.bidg.service.CProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Rafael Viveros
 *         Created on 20/11/15.
 */
@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    CProductsService productsService;

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findProducts() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(productsService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findProductsById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(productsService.findById(id));
    }
    
    @RequestMapping(value = "/product-type/{idProductType}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<String> findByProductType(@PathVariable int idProductType) throws Exception {
        
        List<CProducts> list = productsService.findByProductTypes(new CProductTypes(idProductType));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }
}
