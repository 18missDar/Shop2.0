package com.example.project.domain;



public class Usercarts {

    private String username;
    private String title;
    private String amount;
    private String cost;

    public Usercarts(){

    }

    public Usercarts(String username, String title, String cost, String amount) {
        this.username = username;
        this.title = title;
        this.amount = amount;
        this.cost = cost;
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

}
