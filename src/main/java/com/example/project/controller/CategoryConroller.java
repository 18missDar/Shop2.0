package com.example.project.controller;

import com.example.project.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class CategoryConroller {
    @Autowired
    private GoodsRepository messageRepo;

    @GetMapping("category")
    public String category(@RequestParam String category, Model model) {
        model.addAttribute("messages", messageRepo.findByCategory(category));

        return "category";
    }
}