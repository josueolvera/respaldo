package mx.bidg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Controller
@RequestMapping("/siad")
public class SIADController {

    @RequestMapping(value = "/budgets", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String budgetsView() {
        return "Budget";
    }

    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String stockView() {
        return "stock";
    }
    
        @RequestMapping(value="/servicerequest", produces= {"text/html;charset=UTF-8"})
    public String serviceRequest(Model model){
        return "ServiceRequest";
    }
}
