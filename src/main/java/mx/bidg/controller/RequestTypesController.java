package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.service.CRequestTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
@Controller
@RequestMapping("/request-types")
public class RequestTypesController {

    @Autowired
    CRequestTypesService requestTypesService;

    ObjectMapper mapper = new ObjectMapper();


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String findAllRequestTypes() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestTypesService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findRequestById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestTypesService.findById(id));
    }
    
    @RequestMapping(value = "/request-category/{idRequestCategory}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getListByRequestCategory(@PathVariable int idRequestCategory) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestTypesService
            .findByRequestCategory(new CRequestsCategories(idRequestCategory)));
    }
}
