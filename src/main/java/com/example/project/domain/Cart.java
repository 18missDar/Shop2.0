package com.example.project.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ElementCollection(targetClass = Item.class, fetch = FetchType.EAGER)
    @ManyToMany
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private List<Item> items;

    @OneToOne
    private User user;

    public Cart(){

    }

    public Cart(List<Item> items, User user) {
        this.items = items;
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public Item find(Goods good){
        for (int i = 0 ; i < items.size(); i++){
            if (items.get(i).getGoodID() == good.getId()){
                return items.get(i);
            }
        }
        return null;
    }
}
