package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.google.common.io.Files.map;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRegions;
import mx.bidg.model.CSubareas;
import mx.bidg.model.Users;
import mx.bidg.service.CRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.IIOException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rubens
 */
@Controller
@RequestMapping("/regions")
public class CRegionsController {
    private static final long serialVersionUID = 1L;
    @Autowired
    CRegionsService cRegionsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll()throws IOException{
        List<CRegions> regionsList = cRegionsService.findAll();
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(regionsList)
                ,HttpStatus.OK);
    }

    @RequestMapping(value = "/save-regions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession session)throws IOException{
        JsonNode node = mapper.readTree(data);
        Users user = (Users) session.getAttribute("user");
        CRegions cRegions = new CRegions();
        cRegions.setRegionName(node.get("name").asText());
        cRegions.setSaemFlag(0);
        cRegions.setCreationDate(LocalDateTime.now());
        cRegions.setUsername(user.getUsername());

        cRegionsService.save(cRegions);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(cRegionsService.findAll())
                ,HttpStatus.OK);
    }
}
