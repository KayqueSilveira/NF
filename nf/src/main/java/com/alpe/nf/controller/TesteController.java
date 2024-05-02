package com.alpe.nf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/teste")
public class TesteController {

    @GetMapping("mensagem")
    public String getTest() {
        return "DEU CERTO PORRA!";
    }
}
