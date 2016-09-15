
package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CTypeOperation;
import mx.bidg.service.CTypeOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author rubens
 */
@Controller
@RequestMapping("/c-type-operation")
public class CTypeOperationController {
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private CTypeOperationService cTypeOperationService;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll() throws Exception{
        List<CTypeOperation> cTypeOperations = cTypeOperationService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(cTypeOperations),
                HttpStatus.OK
        );
    } 
}
