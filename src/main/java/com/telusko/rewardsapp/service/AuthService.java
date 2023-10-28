package com.telusko.rewardsapp.service;

import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.exception.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.telusko.rewardsapp.util.Constants.*;

@Service
public class AuthService
{
    @Autowired
    UserService userObj;

    @Autowired
    LoginService loginService;

    @Autowired
    RewardService rewardService;

    public void process()
    {

        List<User> users = userObj.fetchUsers();

        //Background Thread for Transactions
        TransThread transThread = new TransThread(users);
        Thread thread = new Thread(transThread);
        thread.start();

        try
        {
            int userId = loginService.authentication(users);
            rewardService.accessRewards(userId);
        }
        catch (AuthException e)
        {
            System.out.println(RED + e.getMessage() + RESET);
            //Allowing User for Second Time Login
            try
            {
                int userId =  loginService.authentication(users);
                rewardService.accessRewards(userId);
            }
            catch (AuthException ex)
            {
                System.out.println(RED + e.getMessage() + RESET);
                System.out.println(PURPLE + "you have exceeded the maximum number" +
                        " of attempts, please try again after some time.");
            }
        }
    }
}