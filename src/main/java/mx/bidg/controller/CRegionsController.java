package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.google.common.io.Files.map;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRegions;
import mx.bidg.service.CRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @RequestMapping(produces = "application/json;charset=UTF-8")
    public @ResponseBody String getRegions() throws Exception {
        List<CRegions> list = cRegionsService.findAll();
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(list);
    }
    
}
