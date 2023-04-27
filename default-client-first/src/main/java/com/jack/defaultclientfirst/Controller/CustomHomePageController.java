package com.jack.defaultclientfirst.Controller;

import com.jack.clientauthority.annotation.HomePageProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

@Controller
public class CustomHomePageController implements HomePageProvider {
    @Override
    public String homePage(HttpServletRequest request, HttpServletResponse response) {
        return "homePage";
    }
}
