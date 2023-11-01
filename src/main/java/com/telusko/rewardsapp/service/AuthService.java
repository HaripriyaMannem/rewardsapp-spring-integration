package com.telusko.rewardsapp.service;

import com.telusko.rewardsapp.beans.User;
//import com.telusko.rewardsapp.exception.AuthException;
import com.telusko.rewardsapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.telusko.rewardsapp.util.Constants.*;

@Service
public class AuthService
{

    @Autowired
    UserRepo userRepo;
    @Autowired
    RewardService rewardService;
    @Autowired
    LoginService loginService;
    @Autowired
    TransThread transThread;

    public void process()
    {
        List<User> users = userRepo.fetchUsers();
        System.out.println(users);
        //Background Thread for Transactions
        Thread thread = new Thread(transThread);
        thread.start();

        try
        {
            int userId = loginService.authentication(users);
            rewardService.accessRewards(userId);
        }
        catch (Exception e)
        {
            System.out.println(RED + e.getMessage() + RESET);
            //Allowing User for Second Time Login
            try
            {
                int userId =  loginService.authentication(users);
                rewardService.accessRewards(userId);
            }
            catch (Exception ex)
            {
                System.out.println(RED + e.getMessage() + RESET);
                System.out.println(PURPLE + "you have exceeded the maximum number" +
                        " of attempts, please try again after some time.");
            }
        }
    }
}