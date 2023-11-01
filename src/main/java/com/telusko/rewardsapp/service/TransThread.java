package com.telusko.rewardsapp.service;

import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TransThread implements Runnable
{

    @Autowired
    UserRepo userRepo;

    @Override
    public void run()
    {

        try
        {
            List<User> users = userRepo.fetchUsers();

            while(!Thread.currentThread().isInterrupted())
            {

                int max = 100000;
                int min = 50000;

                Random rand = new Random();
                User user = users.get(rand.nextInt(users.size()));

                int randomAmt = (rand.nextInt(max-min + 1) + min);
                user.setTransAmount(user.getTransAmount()+ randomAmt);

                int points = randomAmt/100;
                user.setPoints(user.getPoints() + points);

                if(!userRepo.updateUser(user)){
                    Thread.sleep(3000);
                }
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
