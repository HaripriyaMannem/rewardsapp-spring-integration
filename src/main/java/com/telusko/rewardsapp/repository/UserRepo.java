package com.telusko.rewardsapp.repository;


import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.config.JdbcConfig;
import com.telusko.rewardsapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepo
{

    Connection connect=null;
    PreparedStatement pstmnt=null;
    Statement stmt=null;
    ResultSet result=null;

    public List<User> fetchUsers()
    {
        List<User> users = new ArrayList<>();

        try
        {
            connect = JdbcConfig.getDbConnection();
            if(connect!=null)
            {
                stmt=connect.createStatement();
                String query = "select * from users";
                ResultSet result = stmt.executeQuery(query);

                while (result.next())
                {
                    User user = new User();
                    //User user = applicationContext.getBean(User.class);
                    user.setId(result.getInt(1));
                    user.setName(result.getString(2));
                    user.setPassword(result.getString(3));
                    user.setTransAmount(result.getInt(4));
                    user.setPoints(result.getInt(5));
                    users.add(user);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean updateUser(User user)
    {
        try
        {
            connect=JdbcConfig.getDbConnection();
            if(connect != null)
            {
                String sql="UPDATE users set transAmount=?, redeemPoints=? where userId=?";
                pstmnt=connect.prepareStatement(sql);

                pstmnt.setInt(1, user.getTransAmount());
                pstmnt.setInt(2, user.getPoints());
                pstmnt.setInt(3, user.getId());

                return pstmnt.execute();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return false;
    }
}
