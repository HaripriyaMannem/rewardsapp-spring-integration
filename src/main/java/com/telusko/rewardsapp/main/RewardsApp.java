package com.telusko.rewardsapp.main;

import com.telusko.rewardsapp.service.AuthService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RewardsApp {

    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        AuthService authService = applicationContext.getBean(AuthService.class);
        authService.process();
    }
}
