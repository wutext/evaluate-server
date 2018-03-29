package com.litsoft.evaluateserver.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VisitorController {

    @RequestMapping("/visitor")
    public String visit() {

        return "";
    }
}
