package com.litsoft.evaluateserver.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visit")
public class VisitorController {

    @RequestMapping("/research")
    public String visit() {

        return "/view/research/research";
    }

    @RequestMapping("/save")
    public String save() {

        return "/view/research/success";
    }

}
