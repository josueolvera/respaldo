package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.model.CNotificationsStatus;
import mx.bidg.model.Notifications;
import mx.bidg.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * @author Rafael Viveros
 * Created on 22/02/16.
 */
@Controller
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(
            value = "/archive/{idNotification}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    ) public ResponseEntity<String> archiveNotification(@PathVariable int idNotification) throws IOException {
        notificationsService.archive(idNotification);

        return new ResponseEntity<>("Notificacion archivada", HttpStatus.OK);
    }
}
