package mx.bidg.controller;

import mx.bidg.model.CRequestsCategories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author rubens
 */

@Controller
@RequestMapping("/agenda")
public class CalendarController {
    
    @RequestMapping(value = "/rooms",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView roomsView() {
        ModelAndView model = new ModelAndView();
        //model.addObject("idRefund", idRefund);
        //model.addObject("cat", CRequestsCategories.REEMBOLSOS);
        model.setViewName("Rooms");
        return model;
    }
    

    
}
