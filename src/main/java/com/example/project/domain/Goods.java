package com.example.project.domain;

import javax.persistence.*;
import java.util.List;


@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String title;
    private String description;
    private String cost;

//    @OneToMany
//    private List<GoodInOrder> goodInOrders;

    private String filename;

    public Goods() {
    }

    public Goods(String title, String description, String cost) {
        this.title = title;
        this.description = description;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

//    public List<GoodInOrder> getGoodInOrders() {
//        return goodInOrders;
//    }
//
//    public void setGoodInOrders(List<GoodInOrder> goodInOrders) {
//        this.goodInOrders = goodInOrders;
//    }
}
