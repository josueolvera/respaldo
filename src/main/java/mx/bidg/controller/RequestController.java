package mx.bidg.controller;

import java.util.List;
import mx.bidg.model.CRequestTypes;
import mx.bidg.service.CRequestTypesService;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/request-types")
    public @ResponseBody List<CRequestTypes> findAllRequestTypes() {
        return requestTypesService.findAll();
    }
}
