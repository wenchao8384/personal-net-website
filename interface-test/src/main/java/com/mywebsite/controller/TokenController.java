package com.mywebsite.controller;

import com.mywebsite.util.annotion.GetUserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokenTest")
public class TokenController {

    @RequestMapping("/getMethod")
    @GetUserInfo
    public String getTokenMethod(@RequestParam("msg") String  msg){
        System.out.println("方法被调用");
        return msg;
    }
}
