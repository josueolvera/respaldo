package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.service.CProductTypesService;
import mx.bidg.service.CRequestCategoriesService;
import mx.bidg.service.CRequestTypesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Rafael Viveros
 * created on 18/11/15.
 */
@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    CRequestTypesService requestTypesService;
    @Autowired
    CRequestCategoriesService categoriesService;
    @Autowired
    CProductTypesService productTypesService;


    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/request-types", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findAllRequestTypes() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestTypesService.findAll());
    }

    @RequestMapping(value = "/request-types/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findRequestById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(requestTypesService.findById(id));
    }

    @RequestMapping(value = "/request-categories", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findAllRequestCategories() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(categoriesService.findAll());
    }

    @RequestMapping(value = "/request-categories/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findRequestCategoryById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(categoriesService.findById(id));
    }

    @RequestMapping(value = "/product-types", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String findAllProductTypes() throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(productTypesService.findAll());
    }
}
