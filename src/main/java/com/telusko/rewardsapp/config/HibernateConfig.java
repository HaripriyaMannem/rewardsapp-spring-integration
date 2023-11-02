package com.telusko.rewardsapp.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
@Configuration
public class HibernateConfig
{
    public Session getSession()
    {
        SessionFactory sessionFactory= new org.hibernate.cfg.Configuration().configure("/hibernate.config.xml").buildSessionFactory();
       return sessionFactory.openSession();
    }
}
