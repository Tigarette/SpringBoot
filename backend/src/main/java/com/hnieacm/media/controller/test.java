package com.hnieacm.media.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/test/")
    public void Test(){
        System.out.println("asdsadasda");
    }
}
