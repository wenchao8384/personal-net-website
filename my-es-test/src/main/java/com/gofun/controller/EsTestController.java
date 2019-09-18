package com.gofun.controller;

import com.gofun.utils.EsHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author by wangwenchao
 * @Description:
 * @Create: 2019/8/26
 */
@RestController
@RequestMapping("/es")
@ConfigurationProperties(prefix = "gofun.mq")
public class EsTestController {

    @Autowired
    private EsHandle esHandle;

    //命名服务器
    private String namesrvAddr;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    @RequestMapping("/namesrvAddr")
    @ResponseBody
    public String namesrvAddr(){
        System.out.println("方法呗访问！！！！！！！！！！！！！！！！！"+namesrvAddr);
        return namesrvAddr;
    }

    @RequestMapping("/getById")
    @ResponseBody
    public String getById(@RequestParam String userId){
        System.out.println("方法呗访问！！！！！！！！！！！！！！！！！"+userId);
        try {
            esHandle.queryUserForGroupRule(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
}
