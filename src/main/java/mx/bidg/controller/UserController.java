package mx.bidg.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Rafael Viveros
 * Created on 8/02/16.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value = "/inbox-page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getInboxPage(HttpSession session) throws IOException {
        return "inbox-page";
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getInbox(HttpSession session) throws IOException {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
