package mx.bidg.controller;

import mx.bidg.service.PassengersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/passengers")
public class PassengersController {

    @Autowired
    private PassengersService passengersService;
}
