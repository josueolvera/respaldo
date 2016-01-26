package mx.bidg.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Rafael Viveros
 * Created on 21/01/16.
 */
@Controller
@RequestMapping("error")
public class ErrorPagesController {

    @RequestMapping(value = "404", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String get404page() {
        return "error404";
    }

    @RequestMapping(value = "401", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String get401page() {
        return "error";
    }

    @RequestMapping(value = "403", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String get403page() {
        return "error";
    }
}
