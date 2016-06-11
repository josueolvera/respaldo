package mx.bidg.controller;

import mx.bidg.model.CTicketsCategories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * SIMA: Sistema de Mesa de Ayuda
 * Created by gerardo8 on 19/05/16.
 */
@Controller
@RequestMapping("/sima")
public class SIMAController {

    @RequestMapping(value = "/tickets-management", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView ticketsManagement() {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketCategory", CTicketsCategories.DISEÑO);
        model.setViewName("TicketsManagement");
        return model;
    }

    @RequestMapping(value = "/tickets-management-ti", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView ticketsManagementTi() {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketCategory", CTicketsCategories.SOPORTE_TECNICO);
        model.setViewName("TicketsManagement");
        return model;
    }

    @RequestMapping(value = "/tickets-management-dev", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView ticketsManagementDev() {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketCategory", CTicketsCategories.DESARROLLO);
        model.setViewName("TicketsManagement");
        return model;
    }

    @RequestMapping(value = "/tickets",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView tickets() {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketCategory", CTicketsCategories.DISEÑO);
        model.setViewName("Tickets");
        return model;
    }

    @RequestMapping(value = "/tickets-ti",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView ticketsTi() {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketCategory", CTicketsCategories.SOPORTE_TECNICO);
        model.setViewName("Tickets");
        return model;
    }

    @RequestMapping(value = "/tickets-dev",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView ticketsDev() {
        ModelAndView model = new ModelAndView();
        model.addObject("ticketCategory", CTicketsCategories.DESARROLLO);
        model.setViewName("Tickets");
        return model;
    }

    @RequestMapping(value = "/ticket",method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView ticket(@RequestParam(name = "folio") String folio) {
        ModelAndView model = new ModelAndView();
        model.addObject("folio", folio);
        model.setViewName("Ticket");
        return model;
    }
}
