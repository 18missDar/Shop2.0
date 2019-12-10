package com.example.project.controller;


import com.example.project.domain.Basket;
import com.example.project.domain.Goods;
import com.example.project.domain.User;
import com.example.project.repository.BasketRepository;
import com.example.project.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BasketController {
    @Autowired
    private BasketRepository basketRepo;

    @Autowired
    private GoodsRepository goodRepo;

    @GetMapping("/add/{id}")
    public String addBasket(@PathVariable("id") Long id, @AuthenticationPrincipal User user, Model model){
        Goods good = goodRepo.findById(id).get();
           Basket basket = basketRepo.findByUser(user.getId());
           if (basket == null){
               List<Goods> goodsAtBasket = new ArrayList<>();
               goodsAtBasket.add(good);
               basket = new Basket(goodsAtBasket,user.getId());
           }
           else{
               List<Goods> goodsAtBasket = basket.getGoodsList();
               goodsAtBasket.add(good);
               basket.setGoodsList(goodsAtBasket);
           }
           basketRepo.save(basket);
           model.addAttribute("messages", basket.getGoodsList());
        return "cart";
    }
}
