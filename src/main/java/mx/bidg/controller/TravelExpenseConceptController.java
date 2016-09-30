package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by josueolvera on 14/07/16.
 */
@Controller
@RequestMapping("travel-expense-concept")
public class TravelExpenseConceptController {

    @Autowired
    private ObjectMapper mapper;
}
