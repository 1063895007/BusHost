package com.fp.bus.Observed.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fp.bus.test;

/**
 * @Copyright (C), 2018,北京同创永益科技发展有限公司
 * @ProjectName: hatech
 * @Package maven.dome.idea1.controller
 * @ClassName: index
 * @Author: fengpeng
 * @Description:
 * @create: 2019-06-11-09-48
 **/
@RestController
@RequestMapping("/index")
public class index {

    @GetMapping("/hello")
    public String hello() {
    	test test = new test();
		test.test();
        return "Hello World";
    }

}
