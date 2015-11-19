package mx.bidg.controller;

import java.util.List;
import mx.bidg.model.CRequestTypes;
import mx.bidg.model.CRequestsCategories;
import mx.bidg.service.CRequestCategoriesService;
import mx.bidg.service.CRequestTypesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping(value = "/request-types", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<CRequestTypes> findAllRequestTypes() {
        return requestTypesService.findAll();
    }

    @RequestMapping(value = "/request-categories", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<CRequestsCategories> findAllRequestCategories() {
        return categoriesService.findAll();
    }
}
