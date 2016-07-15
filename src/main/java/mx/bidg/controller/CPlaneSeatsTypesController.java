package mx.bidg.controller;

import mx.bidg.service.CPlaneSeatsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/plane-seats-types")
public class CPlaneSeatsTypesController {

    @Autowired
    private CPlaneSeatsTypesService cPlaneSeatsTypesService;
}
