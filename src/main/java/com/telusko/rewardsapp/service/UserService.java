package com.telusko.rewardsapp.service;

import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.repository.UserRepo;
import com.telusko.rewardsapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.telusko.rewardsapp.util.Constants.PASSWORD;

@Service
public class UserService
{
    Util util = new Util();
    List<User> users = new ArrayList<>();
    String[] names = new String[]{"Afsari", "Hari", "Jay", "Janani", "JayRam", "Kalyan",
            "Ooha", "Sai", "Srinath", "Vyankatesh"};

    @Autowired
    UserRepo userRepo;

    //one time activity
    public List<User> createUsers()
    {
        for(int i=0; i<names.length; i++)
        {
            User user = new User();
            user.setId(i+1);
            user.setName(names[i]);
            user.setPassword(util.encryptPwd(PASSWORD));
            userRepo.insertUsers(user);
            users.add(user);
        }
        return users;
    }

    public List<User> fetchUsers()
    {
        return userRepo.fetchUsers();
    }

}