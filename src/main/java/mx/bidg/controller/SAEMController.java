package mx.bidg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView updateEmployee(@RequestParam(name = "idDwEmployee") Integer idDwEmployee) {
        ModelAndView model = new ModelAndView();
        model.addObject("idDwEmployee", idDwEmployee);
        model.setViewName("UpdateEmployee");
        return model;
    }

    @RequestMapping(value = "/reports", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemReports() {
        ModelAndView model = new ModelAndView();
        model.setViewName("saemReports");
        return model;
    }

    @RequestMapping(value = "/incidences", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemIncidences() {
        ModelAndView model = new ModelAndView();
        model.setViewName("employeeIncidence");
        return model;
    }

    @RequestMapping(value = "/distributor-pd", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemDistributorPd() {
        ModelAndView model = new ModelAndView();
        model.setViewName("DistributorPD");
        return model;
    }

    @RequestMapping(value = "/pd-by-employee", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemPdByEmployee() {
        ModelAndView model = new ModelAndView();
        model.setViewName("persectionsDeductionsByEmployee");
        return model;
    }
    
    @RequestMapping(value = "/multilevel-employee", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemMultilevelEmployee() {
        ModelAndView model = new ModelAndView();
        model.setViewName("MultilevelEmployee");
        return model;
    }

    @RequestMapping(value = "/managment-roster", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemManagmentRoster() {
        ModelAndView model = new ModelAndView();
        model.setViewName("managmentRoster");
        return model;
    }

    @RequestMapping(value = "/corporate-reports", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemCorporateReports() {
        ModelAndView model = new ModelAndView();
        model.setViewName("reportsCorporateRoster");
        return model;
    }

    @RequestMapping(value = "/outsourcing-reports", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView saemOutsourcingReports() {
        ModelAndView model = new ModelAndView();
        model.setViewName("reportsOutsourcingRoster");
        return model;
    }
}
