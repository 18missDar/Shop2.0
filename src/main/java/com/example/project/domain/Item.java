package com.example.project.domain;


import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue
    private Long id;


    private long goodID;


    @NotNull
    private Integer quantity;


    public Item(){

    }

    @ManyToOne
    private User user;

    public Item(long goodID, @NotNull Integer quantity, User user) {
        this.goodID = goodID;
        this.quantity = quantity;
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public long getGoodID() {
        return goodID;
    }

    public void setGoodID(long goodID) {
        this.goodID = goodID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
