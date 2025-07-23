package org.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MsgController {

    @GetMapping
    public String sayHello() {
        return "Welcome to MsgApp";
    }

    @PostMapping
    public void getMessage(@RequestBody String str){
        System.out.println(str);
    }


}
