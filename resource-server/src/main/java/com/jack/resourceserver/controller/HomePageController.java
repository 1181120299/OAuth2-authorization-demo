package com.jack.resourceserver.controller;

import com.jack.clientauthority.annotation.HomePageProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller
public class HomePageController implements HomePageProvider {

    @Override
    public String homePage(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/user/page";
    }
}
