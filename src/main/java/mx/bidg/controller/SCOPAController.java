package mx.bidg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kevin Salvador on 10/01/2017.
 */
@Controller
@RequestMapping("/scopa")
public class SCOPAController {

    @RequestMapping(value = "/truck-driver", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public ModelAndView truckdriver(){
        ModelAndView model= new ModelAndView();
        model.setViewName("truckDriver");
        return model;
    }

    @RequestMapping(value = "/sinister-drivers", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public ModelAndView sinisterTruckdriver(){
        ModelAndView model= new ModelAndView();
        model.setViewName("sinisterTruckDriver");
        return model;
    }

    @RequestMapping(value = "/aterna-drivers", method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public ModelAndView aternaTruckdriver(){
        ModelAndView model= new ModelAndView();
        model.setViewName("aternaDrivers");
        return model;
    }
}
