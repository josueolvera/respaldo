package mx.bidg.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.RolesCostCenter;
import mx.bidg.service.RolesCostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Controller
@RequestMapping("/roles-cost-center")
public class RolesCostCenterController {

    @Autowired
    private RolesCostCenterService rolesCostCenterService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/role/{idRole}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByUser(@PathVariable Integer idRole) throws IOException {
        List<RolesCostCenter> rolesCostCenter = rolesCostCenterService.findByRole(idRole);
        return ResponseEntity.ok(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(rolesCostCenter));
    }

}
