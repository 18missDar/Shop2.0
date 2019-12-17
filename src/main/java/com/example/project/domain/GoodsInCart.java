package com.example.project.domain;


import javax.persistence.*;

@Entity
@Table(name = "goodcart")
public class GoodsInCart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;
    private String cost;
    private int amount;
    private long uid;

    public GoodsInCart(){

    }

    public GoodsInCart(String title, String cost, int amount, long uid) {
        this.title = title;
        this.cost = cost;
        this.amount = amount;
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
