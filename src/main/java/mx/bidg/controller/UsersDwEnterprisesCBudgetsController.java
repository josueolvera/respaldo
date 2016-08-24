package mx.bidg.controller;

import mx.bidg.service.UsersDwEnterprisesCBudgetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo8 on 24/08/16.
 */
@Controller
@RequestMapping("/users-dw-enterprises-budgets")
public class UsersDwEnterprisesCBudgetsController {

    @Autowired
    private UsersDwEnterprisesCBudgetsService usersDwEnterprisesCBudgetsService;
}
