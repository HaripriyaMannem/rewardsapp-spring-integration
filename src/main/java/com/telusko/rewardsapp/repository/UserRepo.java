package com.telusko.rewardsapp.repository;


import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepo
{
    @Autowired
    HibernateConfig hibernateConfig;

    public List<User> fetchUsers()
    {
        List<User> users;
        try(Session session = hibernateConfig.getSession();)
        {
            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);

            CriteriaQuery<User> all = cq.select(rootEntry);
            TypedQuery<User> allQuery = session.createQuery(all);
            users =  allQuery.getResultList();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean updateUser(User user)
    {
        try(Session session = hibernateConfig.getSession();)
        {
            Transaction transaction=session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return false;
    }
}
