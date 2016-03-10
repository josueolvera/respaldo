package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Notifications;
import mx.bidg.model.Users;
import mx.bidg.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 8/02/16.
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private NotificationsService notificationsService;

    private ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(value = "/inbox-page", method = RequestMethod.GET)
    public String getInboxPage(HttpSession session) throws IOException {
        return "inbox-page";
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getLoggedInUser(HttpSession session) throws IOException {
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(session.getAttribute("user")),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getInbox(HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");
        List<Notifications> notifications = notificationsService.findAllForUser(user);
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(notifications),
                HttpStatus.OK
        );
    }
}
