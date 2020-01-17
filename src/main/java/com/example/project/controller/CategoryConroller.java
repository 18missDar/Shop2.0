package com.example.project.controller;

import com.example.project.domain.Goods;
import com.example.project.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/main")
public class CategoryConroller {
    @Autowired
    private GoodsRepository messageRepo;

    public boolean isActive(Goods good){
        return messageRepo.findById(good.getId()).get().isActive();
    }

    public List<Goods> findActiveGoodsByCategory(String category){
        return messageRepo.findByCategory(category).stream()
                .filter(this::isActive)
                .collect(Collectors.toList());
    }

    @GetMapping("category")
    public String category(@RequestParam String category, Model model) {
        model.addAttribute("messages", findActiveGoodsByCategory(category));

        return "category";
    }
}