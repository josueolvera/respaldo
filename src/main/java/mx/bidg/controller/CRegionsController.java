package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.google.common.io.Files.map;

import java.io.IOException;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRegions;
import mx.bidg.service.CRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.IIOException;

/**
 *
 * @author rubens
 */
@Controller
@RequestMapping("/regions")
public class CRegionsController {
    @Autowired
    CRegionsService cRegionsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<CRegions> regionsList = cRegionsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(regionsList), HttpStatus.OK);
    }
    
}
