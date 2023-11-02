package com.telusko.rewardsapp.beans;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity(name= "giftcard")
public class GiftCard
{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "name")
    private String name;

    @Column(name= "points")
    private int points;

    @Column(name= "couponCode")
    private String couponCode;

    @Column(name= "userId")
    private int userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GiftCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", points=" + points +
                ", couponCode='" + couponCode + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
