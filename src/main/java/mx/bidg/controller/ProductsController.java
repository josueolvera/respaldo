package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CProductTypes;
import mx.bidg.model.CProducts;
import mx.bidg.service.CProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rafael Viveros
 *         Created on 20/11/15.
 */
@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    CProductsService productsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findProducts() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(productsService.findAll());
    }

    @RequestMapping(value = "/subcategory/{idBudgetSubcategory}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String save(@PathVariable int idBudgetSubcategory, @RequestBody CProducts product) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(productsService.save(idBudgetSubcategory, product));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findProductsById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(productsService.findById(id));
    }
    
    @RequestMapping(value = "/product-type/{idAccountingAccount}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> findByProductType(@PathVariable int idAccountingAccount) throws Exception {
        
        List<CProducts> list = productsService.findByProductTypes(new AccountingAccounts(idAccountingAccount));
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list), HttpStatus.OK);
    }

    @RequestMapping(value = "/subcategory/{idBudgetSubcategory}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> findByBudgetSubcategory(@PathVariable int idBudgetSubcategory) throws Exception {

        List<CProducts> list = productsService.findByBudgetSubcategory(idBudgetSubcategory);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list));
    }
}
