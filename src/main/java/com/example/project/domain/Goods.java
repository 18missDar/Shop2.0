package com.example.project.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String cost;
    private String category;
    private boolean active;


    private String filename;

    public Goods() {
    }

    public Goods(String title, String description, String cost, String category) {
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.category = category;
        this.active = true;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return Objects.equals(id, goods.id) &&
                Objects.equals(title, goods.title) &&
                Objects.equals(description, goods.description) &&
                Objects.equals(cost, goods.cost) &&
                Objects.equals(category, goods.category) &&
                Objects.equals(filename, goods.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, cost, category, filename);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
