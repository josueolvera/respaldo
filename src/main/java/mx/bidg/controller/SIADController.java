package mx.bidg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@Controller
@RequestMapping("/siad")
public class SIADController {

    @RequestMapping(value = "/budgets", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String budgetsView() {
        return "BudgetPruebas";
    }
    
    @RequestMapping(value = "/individual-budget", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String individualBudget() {
        return "Budgets";
    }
    

    @RequestMapping(value = "/stock", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String stockView() {
        return "stock";
    }

    @RequestMapping(value = "/stock/{idStock}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView singleStockView(@PathVariable Integer idStock) {
        ModelAndView model = new ModelAndView();
        model.addObject("idStock", idStock);
        model.setViewName("single-stock");
        return model;
    }

    @RequestMapping(value="/request", method = RequestMethod.GET, produces= {"text/html;charset=UTF-8"})
    public String serviceRequest(){
        return "Request";
    }
    
    @RequestMapping(value = "/cotizable", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String cotizableRequestType() {
        return "CotizableRequest";
    }
    
    @RequestMapping(value = "/directa/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView directaRequestType(@PathVariable int idRequest) {
        int cat= 2;
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.addObject("cat", cat);
        model.setViewName("DirectRequest");
        return model;
    }
    
    @RequestMapping(value = "/periodica", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String periodicRequestType() {
        return "PeriodicRequest";
    }
    
    @RequestMapping(value = "/search-request", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public String searchRequest() {
        return "SearchRequest";
    }
    
    @RequestMapping(value = "/cotizableb/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView cotizableRequestTypeSearch(@PathVariable int idRequest) {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("CotizableRequest");
        return model;
    }
    
    @RequestMapping(value = "/directab/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView directaRequestTypeSearch(@PathVariable int idRequest) {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("DirectRequest");
        return model;
    }
    
    @RequestMapping(value = "/periodicab/{idRequest}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView periodicRequestTypeSearch(@PathVariable int idRequest) 
    {
        ModelAndView model= new ModelAndView();
        model.addObject("idRequest", idRequest);
        model.setViewName("PeriodicRequest");
        return model;
    }
    
}
