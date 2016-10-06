package mx.bidg.controller;

import java.time.LocalDateTime;
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
    public ModelAndView roomsView(@RequestParam(name = "room", required = false) Integer idRoom) {
        ModelAndView model = new ModelAndView();
        if (idRoom != null) {
            model.addObject("room", idRoom);
            model.addObject("now", LocalDateTime.now());
            model.setViewName("Room");
        } else {
            model.setViewName("Rooms");
        }
        return model;
    }
}
