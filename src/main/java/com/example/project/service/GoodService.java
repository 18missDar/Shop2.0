package com.example.project.service;


import com.example.project.domain.Goods;
import com.example.project.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodService {
    @Autowired
    private GoodsRepository goodRepo;

    public Goods saveGood(Goods good, String title, String description, String cost, String category){
        good.setTitle(title);
        good.setDescription(description);
        good.setCost(cost);
        good.setCategory(category);
        return good;
    }


}
