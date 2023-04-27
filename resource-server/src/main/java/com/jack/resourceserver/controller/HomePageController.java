package com.jack.resourceserver.controller;

import com.jack.clientauthority.annotation.HomePageProvider;
import org.springframework.stereotype.Controller;

@Controller
public class HomePageController implements HomePageProvider {

    @Override
    public String homePage() {
        return "redirect:/user/page";
    }
}
