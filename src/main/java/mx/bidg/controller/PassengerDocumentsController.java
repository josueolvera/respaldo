package mx.bidg.controller;

import mx.bidg.service.PassengerDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo8 on 14/07/16.
 */
@Controller
@RequestMapping("/passenger-documents")
public class PassengerDocumentsController {

    @Autowired
    private PassengerDocumentsService passengerDocumentsService;
}
