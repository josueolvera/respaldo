package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.service.DistributorAreaRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo8 on 15/08/16.
 */
@Controller
@RequestMapping("/distributor-area-rol")
public class DistributorAreaRolServiceController {

    @Autowired
    private DistributorAreaRolService distributorAreaRolService;

    @Autowired
    private ObjectMapper mapper;
}
