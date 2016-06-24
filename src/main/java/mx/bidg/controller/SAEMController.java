package mx.bidg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by gerardo8 on 23/06/16.
 */
@Controller
@RequestMapping("/saem")
public class SAEMController {

    @RequestMapping(value = "/save-employee", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saveEmployee() {
        ModelAndView model = new ModelAndView();
        model.setViewName("SaveEmployee");
        return model;
    }

    @RequestMapping(value = "/search-employees", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView searchEmployees() {
        ModelAndView model = new ModelAndView();
        model.setViewName("SearchEmployees");
        return model;
    }

    @RequestMapping(value = "/update-employee", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView updateEmployee() {
        ModelAndView model = new ModelAndView();
        model.setViewName("UpdateEmployee");
        return model;
    }
}