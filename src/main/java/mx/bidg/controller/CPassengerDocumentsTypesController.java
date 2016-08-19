package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.service.CPassengerDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/passenger-documents-types")
public class CPassengerDocumentsTypesController {

    @Autowired
    private CPassengerDocumentsTypesService cPassengerDocumentsTypesService;

    @Autowired
    private ObjectMapper mapper;
}
