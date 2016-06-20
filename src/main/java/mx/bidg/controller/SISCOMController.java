package mx.bidg.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Controller
@RequestMapping("/siscom")
public class SISCOMController {

    @RequestMapping(value = "/sap-files-upload", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView filesUpload() {
        ModelAndView model = new ModelAndView();
        model.setViewName("SapFilesUpload");
        return model;
    }

<<<<<<< HEAD
    @RequestMapping(value = "/branchs-management", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public ModelAndView branchsManagement() {
        ModelAndView model = new ModelAndView();
        model.setViewName("BranchsManagement");
=======
    @RequestMapping(value = "/agreements-management", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView agreementsManagment( )
    {
        ModelAndView model= new ModelAndView();
        model.setViewName("Agreements");
        return model;
    }
    
    @RequestMapping(value = "/generator", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView generatorOfrules() {
        ModelAndView model = new ModelAndView();
        model.setViewName("generator");
>>>>>>> master
        return model;
    }
}
