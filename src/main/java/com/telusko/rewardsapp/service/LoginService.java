package com.telusko.rewardsapp.service;

import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.exception.AuthException;
import com.telusko.rewardsapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

import static com.telusko.rewardsapp.util.Constants.*;


@Service
public class LoginService
{
    @Autowired
    Util util;

    public LoginService()
    {
        System.out.println(CYAN + "**********************************");
        System.out.println("Welcome to Rewards Application!!!");
        System.out.println("**********************************" + RESET);
    }

    public int authentication(List<User> users) throws AuthException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your Name:");
        String name = sc.nextLine().trim();

        System.out.println("Please enter your Password:");
        String pwd = sc.nextLine().trim();
        String excMsg = "";

        //Validating user
        int userId = 0;
        for (User user : users)
        {
            String decPwd = util.decryptPwd(user.getPassword());

            if (user.getName().equalsIgnoreCase(name) && decPwd.equals(pwd))
            {
                System.out.println(GREEN + "Successfully logged in!!!" + RESET);
                userId = user.getId();
                excMsg = "";
                break;
            }
            else
            {
                excMsg = "Invalid credentials";
            }
        }
        if(!excMsg.isEmpty())
        {
            throw new AuthException(excMsg);
        }
        return userId;
    }
}
