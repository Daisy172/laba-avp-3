package com.stankin.lab6;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorHandler implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
