package com.telusko.rewardsapp.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Component
@Entity(name= "users")
public class User
{
    @Id
    @Column(name = "userId")
    private int id;

    @Column(name = "userName")
    private String name;

    @Column(name = "userPwd")
    private String password;

    @Column(name = "transAmount")
    private int transAmount;

    @Column(name = "redeemPoints")
    private int points;

    @OneToMany(cascade= CascadeType.ALL)
    private List<GiftCard> giftCards;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(int transAmount) {
        this.transAmount = transAmount;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public List<GiftCard> getGiftCards() {
        return giftCards;
    }

    public void setGiftCards(List<GiftCard> giftCards) {
        this.giftCards = giftCards;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", transAmount=" + transAmount +
                ", points=" + points +
                '}';
    }
}
