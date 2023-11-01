package com.telusko.rewardsapp.beans;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Rewards
{

    private int id;
    private String name;
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
