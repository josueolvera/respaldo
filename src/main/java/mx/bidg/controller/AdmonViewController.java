package mx.bidg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admon")
public class AdmonViewController 
{
    @RequestMapping(value= "/budget", produces = {"text/html;charset=UTF-8"})
    public String home(Model model) {
        return "Budget";
    }
    
    @RequestMapping(value="/budgets", produces= {"text/html;charset=UTF-8"})
    public String home2(Model model){
        return "Budgets";
    }
}
