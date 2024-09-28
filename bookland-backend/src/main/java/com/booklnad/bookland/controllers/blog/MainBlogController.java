package com.booklnad.bookland.controllers.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/blog")
public class MainBlogController {

    @GetMapping("/")
    public String Main(){
        Map<String, String> text = new LinkedHashMap<>();
        text.put("text", "hello world");
        try {
            return new ObjectMapper().writeValueAsString(text);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
