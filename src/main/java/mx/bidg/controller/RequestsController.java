package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import javax.servlet.http.HttpSession;
import mx.bidg.config.JsonViews;
import mx.bidg.model.Requests;
import mx.bidg.model.Users;
import mx.bidg.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rafael Viveros
 * created on 18/11/15.
 */
@Controller
@RequestMapping("/requests")
public class RequestsController {
    
    @Autowired
    RequestsService requestsService;
    
    ObjectMapper mapper = new ObjectMapper().registerModule(new Hibernate4Module());
    
    @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json;charset=UTF-8"})
    public @ResponseBody ResponseEntity<String> saveRequest(@RequestBody String data, HttpSession session) 
        throws Exception{
        
        Users user = (Users) session.getAttribute("user");
        Requests request = requestsService.save(data, user);
        String response;
        
        if(request != null) {
            response = mapper.writeValueAsString(request);
        } else {
            response = "Error al guardar la Solicitud";
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
        
    }
    
}