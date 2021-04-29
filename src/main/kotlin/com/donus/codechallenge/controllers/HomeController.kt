package com.donus.codechallenge.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/")
class HomeController {
    /*@GetMapping("/")
    fun home(): ModelAndView {
        val mv = ModelAndView()
        mv.viewName = "index.html"
        return mv
    }*/
    @GetMapping
    fun home(): String{
        return "Hi, Jardel!"
    }
}