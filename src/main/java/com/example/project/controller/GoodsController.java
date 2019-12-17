package com.example.project.controller;

import com.example.project.domain.Goods;
import com.example.project.domain.Item;
import com.example.project.repository.CartRepository;
import com.example.project.repository.GoodsRepository;
import com.example.project.repository.ItemRepository;
import com.example.project.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodsController {
    @Autowired
    private GoodsRepository messageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private GoodService goodService;

    @GetMapping("/delete/{id}")
    public String deleteGood(@PathVariable("id") long id, Model model){
        Optional<Item> item = itemRepo.find(id);
        if (item.isPresent()){
            model.addAttribute("goodi", id);
            return "stop";
        }
        else {
            messageRepo.deleteById(id);
            return "redirect:/main";
        }
    }

    @GetMapping("/discounts")
    public String discountGood(Model model){
        return "discounts";
    }

    @PostMapping("/discounts")
    public String addDiscount(
            @RequestParam String discount,
            @RequestParam String category){
        Double dis = Double.valueOf(discount);
        dis /= 100;
        List<Goods> goodsList = messageRepo.findByCategory(category);
         for (int i = 0; i< goodsList.size(); i++){
             Goods good = messageRepo.findById(goodsList.get(i).getId()).get();
             Double cost = Double.valueOf(good.getCost());
             cost*=(1-dis);
             good.setCost(cost.toString());
             messageRepo.save(good);
         }

        return "redirect:/main";
    }


    @GetMapping("/update/{id}")
    public String updateGood(@PathVariable("id") long id, Model model){
        model.addAttribute("good", messageRepo.findById(id).get());
        return "updateGood";
    }

    @PostMapping("/updateGood")
    public String goodSave(
            @RequestParam String description,
            @RequestParam String title,
            @RequestParam String cost,
            @RequestParam String category,
            @RequestParam("file") MultipartFile file,
            @RequestParam long id
    ) throws IOException {
        Goods goodF = goodService.saveGood(messageRepo.findById(id).get(), title, description, cost, category);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            goodF.setFilename(resultFilename);
        }
        messageRepo.save(goodF);
        return "redirect:/main";
    }

    @PostMapping("/stop")
    public String stop(
            @RequestParam String goodi
    ){
        Goods good = messageRepo.findById(Long.valueOf(goodi)).get();
        good.setActive(false);
        messageRepo.save(good);
        return "redirect:/main";
    }



}
