package com.example.wrappedback.web;

import org.springframework.web.bind.annotation.*;

import com.example.wrappedback.utils.ReadFiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/datas")
    public HashMap<String, Long> getAll() {
        HashMap<String, Long> tulos = ReadFiles.processJsonFiles("data/Spotify Account Data");
        String json = ReadFiles.toJson(tulos);
        return tulos;
    }

}
