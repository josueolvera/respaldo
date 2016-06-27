package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.DwEmployees;
import mx.bidg.service.DwEmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Created by gerardo8 on 24/06/16.
 */
@Controller
@RequestMapping("/dw-employees")
public class DwEmployeesController {

    @Autowired
    private DwEmployeesService dwEmployeesService;

    private ObjectMapper map = new ObjectMapper().registerModule(new Hibernate4Module());

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEmployees
            (
                    @RequestParam(name = "idDistributor", required = false) Integer idDistributor,
                    @RequestParam(name = "idRegion", required = false) Integer idRegion,
                    @RequestParam(name = "idBranch", required = false) Integer idBranch,
                    @RequestParam(name = "idArea", required = false) Integer idArea,
                    @RequestParam(name = "idRole", required = false) Integer idRole
            ) throws IOException {

        List<DwEmployees> dwEmployees;

        if (idDistributor == null &&
                idRegion == null &&
                idBranch == null &&
                idArea == null &&
                idRole == null
                ) {
            dwEmployees = dwEmployeesService.findAll();
        } else {
            dwEmployees = dwEmployeesService.findByDistributorAndRegionAndBranchAndAreaAndRole(idDistributor,idRegion,idBranch,idArea,idRole);

        }

        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployees),
                HttpStatus.OK
        );
    }


    @RequestMapping(value = "/{idDwEmployee}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getDwEmployees(@PathVariable Integer idDwEmployee) throws IOException {

        DwEmployees dwEmployee = dwEmployeesService.findById(idDwEmployee);

        return new ResponseEntity<>(
                map.writerWithView(JsonViews.Embedded.class).writeValueAsString(dwEmployee),
                HttpStatus.OK
        );
    }
}
