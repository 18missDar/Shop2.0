package com.example.project.domain;


import javax.persistence.*;

@Entity
@Table(name = "payedgoods")
public class Usercarts {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;
    private String title;
    private String amount;
    private String cost;
    private boolean payed;
    private boolean mailed;
    private String totalcost;

    public Usercarts(){

    }

    public Usercarts(String username, String title, String cost, String amount) {
        this.username = username;
        this.title = title;
        this.amount = amount;
        this.cost = cost;
        this.payed = false;
        this.mailed = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public boolean isMailed() {
        return mailed;
    }

    public void setMailed(boolean mailed) {
        this.mailed = mailed;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }
}

