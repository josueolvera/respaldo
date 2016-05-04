package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.SapSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Controller
@RequestMapping("/sap-sale")
public class SapSaleController {
    @Autowired
    private SapSaleService sapSaleService;

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findSapSales() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(sapSaleService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findSapSaleById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(sapSaleService.findById(id));
    }

    @RequestMapping(value = "/excel",method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> saveExcelSapSale(@RequestParam("file") MultipartFile file) throws Exception {

        sapSaleService.saveFromExcel(file);
        return ResponseEntity.ok("ok");

//        return new ResponseEntity<>(
//                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSale), HttpStatus.OK
//        );
    }

    @RequestMapping(value = "/excel",method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> updateExcelSapSale(@RequestParam("file") MultipartFile file) throws Exception {

//        sapSaleService.saveFromExcel(file);
        return ResponseEntity.ok("ok");

//        return new ResponseEntity<>(
//                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSale), HttpStatus.OK
//        );
    }

    @RequestMapping(value = "/check-existing-sale",method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<Boolean> checkExistingSale(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(sapSaleService.existsSales(file));

//        return new ResponseEntity<>(
//                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(sapSale), HttpStatus.OK
//        );
    }

}
