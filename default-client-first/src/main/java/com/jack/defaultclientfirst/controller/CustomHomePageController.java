package com.jack.defaultclientfirst.controller;

import com.jack.clientauthority.annotation.HomePageProvider;
import com.jack.clientauthority.utils.UserHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller
public class CustomHomePageController implements HomePageProvider {
    @Override
    public String homePage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("user", UserHelper.getUserinfo());
        return "homePage";
    }
}
