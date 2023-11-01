package com.telusko.rewardsapp.repository;



import com.telusko.rewardsapp.beans.Category;
import com.telusko.rewardsapp.beans.GiftCard;
import com.telusko.rewardsapp.beans.Rewards;
import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.config.JdbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    Connection connect=null;
    PreparedStatement pstmnt=null;
    Statement stmt=null;
    ResultSet result=null;


    public List<Rewards> fetchRewards()
    {
        List<Rewards> rewards = new ArrayList<>();
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                stmt=connect.createStatement();
                String query = "select * from rewards";
                ResultSet result = stmt.executeQuery(query);

                while (result.next())
                {
                    reward.setId(result.getInt(1));
                    reward.setName(result.getString(2));
                    rewards.add(reward);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                JdbcConfig.closeResource(result, pstmnt, connect);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return rewards;
    }

    public List<Category> getCategories(int id)
    {
        List<Category> categories = new ArrayList<>();
        try
        {
            connect=JdbcConfig.getDbConnection();

            if(connect!=null)
            {

                String query="select cid, name, points from category where rid=?";
                pstmnt=connect.prepareStatement(query);
                pstmnt.setInt(1, id);
                ResultSet result = pstmnt.executeQuery();

                while (result.next())
                {
                    category.setId(result.getInt(1));
                    category.setName(result.getString(2));
                    category.setPoints(result.getInt(3));
                    categories.add(category);
                }

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                JdbcConfig.closeResource(result, pstmnt, connect);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public int saveGiftCards(GiftCard giftCard, int id)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="INSERT INTO giftcard (userId, name, points, couponCode) VALUES(?,?,?,?)";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, id);
                pstmnt.setString(2, giftCard.getName());
                pstmnt.setInt(3, giftCard.getPoints());
                pstmnt.setString(4, giftCard.getCouponCode());

                return pstmnt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                JdbcConfig.closeResource(result, pstmnt, connect);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void updateUserPoints(User user)
    {
        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                String sql="UPDATE users set redeemPoints=? where userId=?";
                pstmnt=connect.prepareStatement(sql);
                pstmnt.setInt(1, user.getPoints());
                pstmnt.setInt(2, user.getId());
                pstmnt.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                JdbcConfig.closeResource(result, pstmnt, connect);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<GiftCard> getGiftCards(User user)
    {
        List<GiftCard> giftCards = new ArrayList<>();
        try
        {
            connect=JdbcConfig.getDbConnection();

            if(connect!=null)
            {
                String query="select name, points, couponCode from giftcard where userId=?";
                pstmnt=connect.prepareStatement(query);
                pstmnt.setInt(1, user.getId());
                ResultSet result = pstmnt.executeQuery();

                while (result.next())
                {
                    giftCard.setName(result.getString(1));
                    giftCard.setPoints(result.getInt(2));
                    giftCard.setCouponCode(result.getString(3));
                    giftCards.add(giftCard);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                JdbcConfig.closeResource(result, pstmnt, connect);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return giftCards;
    }
}
