package com.zrr.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangrunrong
 * @date 2022/6/20 2:06
 * @Version 1.0
 **/
@RestController
public class HelloController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/CurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }
}
