package com.telusko.rewardsapp.repository;



import com.telusko.rewardsapp.beans.Category;
import com.telusko.rewardsapp.beans.GiftCard;
import com.telusko.rewardsapp.beans.Rewards;
import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.config.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RewardsRepo {

    @Autowired
    Rewards reward;
    @Autowired
    GiftCard giftCard;
    @Autowired
    Category category;

    @Autowired
    HibernateConfig hibernateConfig;


    public List<Rewards> fetchRewards()
    {
        List<Rewards> rewards;
        try (Session session = hibernateConfig.getSession())
        {
            CriteriaQuery<Rewards> cq = session.getCriteriaBuilder().createQuery(Rewards.class);
            Root<Rewards> rootEntry = cq.from(Rewards.class);

            CriteriaQuery<Rewards> all = cq.select(rootEntry);
            TypedQuery<Rewards> allQuery = session.createQuery(all);

            rewards = allQuery.getResultList();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return rewards;
    }

    public List<Category> getCategories(int id)
    {
        List<Category> categories = new ArrayList<>();
        try (Session session = hibernateConfig.getSession())
        {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("rid"), id));
            Query<Category> query = session.createQuery(criteriaQuery);
            categories = query.getResultList();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return categories;
    }

    public int saveGiftCards(GiftCard giftCard)
    {
        try(Session session = hibernateConfig.getSession();)
        {
            Transaction transaction=session.beginTransaction();
            session.save(giftCard);
            transaction.commit();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return 1;
    }


    public List<GiftCard> getGiftCards(User user)
    {
        List<GiftCard> giftCards = new ArrayList<>();
        try (Session session = hibernateConfig.getSession())
        {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<GiftCard> criteriaQuery = criteriaBuilder.createQuery(GiftCard.class);
            Root<GiftCard> root = criteriaQuery.from(GiftCard.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("userId"), user.getId()));
            Query<GiftCard> query = session.createQuery(criteriaQuery);
            giftCards = query.getResultList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return giftCards;
    }
}
