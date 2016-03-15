package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.Authorizations;
import mx.bidg.model.CAuthorizationStatus;
import mx.bidg.model.CFolios;
import mx.bidg.model.Users;
import mx.bidg.service.AuthorizationsService;
import mx.bidg.service.FoliosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Rafael Viveros
 * Created on 4/12/15.
 */
@Controller
@RequestMapping("/folios")
public class FoliosController {

    @Autowired
    FoliosService foliosService;

    @Autowired
    AuthorizationsService authorizationsService;

    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<String> findFolio(@RequestParam(name = "folio", required = false) String folio) throws IOException {
        String response;
        if (folio == null) {
            response = mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(foliosService.findAll());
        } else {
            response = mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(foliosService.findByFolio(folio));
        }
        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/authorizations/{id}/authorize", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> authorizeAuthorization(@PathVariable int id, HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");
        Authorizations auth = authorizationsService.findById(id);

        if (! auth.getIdUser().equals(user.getIdUser())) {
            throw new ValidationException(
                    "El usuario con id: " + user.getIdUser() + " intentó modificar la autorizacion del usuario con id: " + auth.getIdUser(),
                    "Acceso no autorizado",
                    HttpStatus.UNAUTHORIZED
            );
        }

        changeAuthorizationStatus(auth, CAuthorizationStatus.AUTORIZADA);

        return new ResponseEntity<>("Operacion realizada con exito", HttpStatus.OK);
    }

    @RequestMapping(value = "/authorizations/{id}/reject", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody ResponseEntity<String> rejectAuthorization(@PathVariable int id, HttpSession session) throws IOException {
        Users user = (Users) session.getAttribute("user");
        Authorizations auth = authorizationsService.findById(id);

        if (! auth.getIdUser().equals(user.getIdUser())) {
            throw new ValidationException(
                    "El usuario con id: " + user.getIdUser() + " intentó modificar la autorizacion del usuario con id: " + auth.getIdUser(),
                    "Acceso no autorizado",
                    HttpStatus.UNAUTHORIZED
            );
        }

        changeAuthorizationStatus(auth, CAuthorizationStatus.RECHAZADA);

        return new ResponseEntity<>("Operacion realizada con exito", HttpStatus.OK);
    }

    private void changeAuthorizationStatus(Authorizations auth, int status) {
        auth.setCAuthorizationStatus(new CAuthorizationStatus(status));
        auth.setAuthorizationDate(LocalDateTime.now());
        authorizationsService.update(auth);
    }
}
