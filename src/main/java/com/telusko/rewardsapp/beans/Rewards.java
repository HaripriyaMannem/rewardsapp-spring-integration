package com.telusko.rewardsapp.beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Entity(name= "rewards")
public class Rewards
{
    @Id
    @Column(name= "rid")
    private int id;

    @Column(name= "name")
    private String name;

    @OneToMany(cascade= CascadeType.ALL)
    List<Category> categoryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Rewards{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }
}
