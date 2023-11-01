package com.telusko.rewardsapp.util;



import com.telusko.rewardsapp.beans.Category;
import com.telusko.rewardsapp.beans.Rewards;
import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.repository.RewardsRepo;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.telusko.rewardsapp.util.Constants.*;

@Component
public class Util
{

    public String decryptPwd(String encPwd)
    {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encPwd));
    }


    public boolean validateId(int id)
    {
        return (id == 1 || id == 2 || id == 3);
    }
    
    public User getUser(List<User> users, int id)
    {
        User user = null;
        for (User user1 : users) 
        {
            if(user1.getId() == id)
            {
                user = user1;
                break;
            }
        }
        return user;
    }

    public String generateCouponCode()
    {
        SecureRandom random = new SecureRandom();
        byte[] codeBytes = new byte[16];
        random.nextBytes(codeBytes);
        return Base64.getEncoder().encodeToString(codeBytes);
    }
    
    public void msg1()
    {
        System.out.println(CYAN + "-----------------------------------------");
        System.out.println(BLUE + "Have a glance at Reward Categories below:" + RESET);
    }

    public void msg2()
    {
        System.out.println( "Enter which" + YELLOW + " Reward category" + RESET + ", " +
                "you want to explore: ");
    }

    public void msg3(int points)
    {
        System.out.println("You can " + YELLOW + "redeem" + RESET +
                " any item by using points available: " + BLUE + points + RESET);
    }

    public void msg4(String name, String couponCode)
    {
        System.out.println(GREEN + "Successfully you have redeemed item: " + name);
        System.out.println(PURPLE + "Gift Card Coupon code: " + couponCode);
        System.out.println(CYAN + "-------------------------------------------" + RESET);
    }

    public void msg5()
    {
        System.out.println(RED + "Redeem failed due to insufficient points.");
        System.out.println(CYAN + "----------------------------------------" +RESET);
    }

}
