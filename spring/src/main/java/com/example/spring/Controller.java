package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:3000")
@RestController

public class Controller {


    /*@PostMapping(path="/nothello")
    public String setname(@RequestBody String name) {
        System.out.println("1");
        return String.format("hello ");
    }*/

}
