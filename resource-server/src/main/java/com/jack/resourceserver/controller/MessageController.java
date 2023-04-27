package com.jack.resourceserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {

    @GetMapping("/messages")
    public String[] getMessage(Authentication authentication) {
        log.info("authentication: {}", authentication);
        log.info("name: {}", authentication.getName());

        /*SecurityContext context = SecurityContextHolder.getContext();
        log.info("get context from holder");
        log.info("context = {}", context);
        log.info("get authentication from context: {}", context.getAuthentication());*/

        return new String[] {"金瓶梅", "mysql innodb引擎内幕", "java编程思想"};
    }
}
