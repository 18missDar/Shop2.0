package com.example.project.controller;

import com.example.project.domain.Goods;
import com.example.project.domain.Item;
import com.example.project.domain.User;
import com.example.project.repository.GoodsRepository;
import com.example.project.repository.ItemRepository;
import com.example.project.service.GoodService;
import com.example.project.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class GoodsController {
    @Autowired
    private GoodsRepository messageRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private GoodService goodService;

    @Autowired
    private UserService userSevice;

    @GetMapping("/delete/{id}")
    public String deleteGood(@PathVariable("id") long id, Model model) {
        Optional<Item> item = itemRepo.find(id);
        if (item.isPresent()) {
            model.addAttribute("goodi", id);
            return "stop";
        } else {
            messageRepo.deleteById(id);
            return "redirect:/main";
        }
    }

    @GetMapping("/discounts")
    public String discountGood(Model model) {
        return "discounts";
    }


    public void changeGoodsWithDiscounts(List<Goods> goodsList, Double dis) {
        for (int i = 0; i < goodsList.size(); i++) {
            Goods good = messageRepo.findById(goodsList.get(i).getId()).get();
            Double cost = Double.valueOf(good.getCost());
            cost *= (1 - dis);
            good.setCost(cost.toString());
            messageRepo.save(good);
        }
    }

    @PostMapping("/discounts")
    public String addDiscount(
            @RequestParam String discount,
            @RequestParam String category) {
        changeGoodsWithDiscounts(messageRepo.findByCategory(category), Double.valueOf(discount) / 100);

        List<User> userList = userSevice.findAll();
        for (int i = 0; i < userList.size(); i++) {
            userSevice.sendDiscountMessage(userList.get(i), category, discount);
        }

        return "redirect:/main";
    }


    @GetMapping("/update/{id}")
    public String updateGood(@PathVariable("id") long id, Model model) {
        model.addAttribute("good", messageRepo.findById(id).get());
        return "updateGood";
    }

    public void encodingAndSavePicture(Goods message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            byte[] fileContent = FileUtils.readFileToByteArray(convFile);
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            message.setFilename("data:image/jpeg;base64," + encodedString);
        }
        messageRepo.save(message);
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
        encodingAndSavePicture(goodF, file);
        return "redirect:/main";
    }

    @PostMapping("/stop")
    public String stop(@RequestParam String goodi) {
        Goods good = messageRepo.findById(Long.valueOf(goodi)).get();
        good.setActive(false);
        messageRepo.save(good);
        return "redirect:/main";
    }

}