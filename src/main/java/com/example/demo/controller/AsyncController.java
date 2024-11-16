package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/async")
public class AsyncController {

    @GetMapping
    public Callable<ModelAndView> async() {
        return() -> {
            // Thread.sleep 을 통해 시간이 소요되는 작업을 진행 중이라고 가정합니다.
            Thread.sleep(Duration.ofMinutes(1));
            return new ModelAndView("async");
        };
    }

}