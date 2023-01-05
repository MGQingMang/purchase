package com.example.purchase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * RouterController class
 * description: 前端页面导航
 *
 * @author huangyiran
 * @date 2022/9/13
 */

@Controller
public class RouterController {

    @RequestMapping("/customerPage")
    public String customerPage () {
        return "customer";
    }

    @RequestMapping("/managerPage")
    public String managerPage () {
        return "manager";
    }
}
