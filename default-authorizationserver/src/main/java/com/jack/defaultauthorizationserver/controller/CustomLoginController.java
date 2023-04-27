package com.jack.defaultauthorizationserver.controller;

import com.jack.authserver.annotation.LoginEntryProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller
public class CustomLoginController implements LoginEntryProvider {
    @Override
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "customLoginPage";
    }
}
