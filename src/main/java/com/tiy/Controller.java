package com.tiy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by jessicatracy on 11/15/16.
 */
@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
}
