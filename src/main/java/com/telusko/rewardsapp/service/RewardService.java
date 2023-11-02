package com.telusko.rewardsapp.service;

import com.telusko.rewardsapp.main.RewardsApp;
import com.telusko.rewardsapp.beans.Category;
import com.telusko.rewardsapp.beans.GiftCard;
import com.telusko.rewardsapp.beans.Rewards;
import com.telusko.rewardsapp.beans.User;
import com.telusko.rewardsapp.repository.RewardsRepo;
import com.telusko.rewardsapp.repository.UserRepo;
import com.telusko.rewardsapp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static com.telusko.rewardsapp.util.Constants.*;

@Service
public class RewardService {


    @Autowired
    Util util;

    @Autowired
    RewardsRepo rewardsRepo;

    @Autowired
    UserRepo userRepo;

    List<GiftCard> giftCards = new ArrayList<>();
    public void accessRewards(int userId)
    {
        boolean flag = true;
        while (flag)
        {
            List<User> users;
            try
            {
                //fetch all Rewards
                List<Rewards> rewards = rewardsRepo.fetchRewards();

                //Show Rewards to the user
                users = userRepo.fetchUsers();
                displayRewards(rewards, users, userId);
                flag= false;
            }
            catch (Exception e)
            {
               System.out.println(e.getMessage());
               break;
            }
        }
    }

    private void displayRewards(List<Rewards> rewards, List<User> users, int userId)
    {
        User user = util.getUser(users, userId);
        if(user != null)
        {
            //show Rewards
            util.msg1();
            rewards.forEach(r -> System.out.println(r.getId() + ". " + r.getName()));
            util.msg2();

            Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
            try
            {
                if (util.validateId(id))
                {
                    //Show Rewards Categories
                    try
                    {
                        displayCat(user, id);
                    }
                    catch(Exception e)
                    {
                        System.out.println(RED + e.getMessage() + RESET);
                        displayCat(user, id);
                    }
                    redeemItems(rewards, users, user, userId);
                }
                else
                {
                    throw new IllegalArgumentException("input is only valid if 1,2 or 3");
                }
            }
            catch (Exception e)
            {
                System.out.println(RED + e.getMessage() + RESET);
                displayRewards(rewards, users, userId);
            }
        }
        else
        {
            System.out.println(PURPLE + "No Transactions made till now by thread, try re login." + RESET);
            //Creating users, Background Transaction thread again when user didn't get chance first time
            try
            {
                RewardsApp.main(null);
            }
            catch (Exception e)
            {
                System.out.println(RED + e.getMessage() + RESET);
            }
        }

    }

    private void displayCat(User user, int id)
    {
        List<Category> categories = rewardsRepo.getCategories(id);
        System.out.println("Number \tCategory \tPoints");

        categories.forEach(category -> System.out.println(category.getId() + "  \t\t" + category.getName()
                + " \t" + category.getPoints()));

        util.msg3(user.getPoints());
        System.out.println("Enter Which " + YELLOW + "category" + RESET + " you want to redeem: ");

        Scanner sc = new Scanner(System.in);
        int catId = sc.nextInt();
        if (util.validateId(catId))
        {
            Category category = categories.get(catId - 1);
            if (user.getPoints() >= category.getPoints())
            {
                //Generating Coupon Code when item redeemed
                GiftCard giftCard = new GiftCard();
                giftCard.setCouponCode(util.generateCouponCode());
                giftCard.setName(category.getName());
                giftCard.setPoints(category.getPoints());
                giftCard.setUserId(user.getId());

                int insertCount = rewardsRepo.saveGiftCards(giftCard);
                if(insertCount > 0)
                {
                    util.msg4(category.getName(), giftCard.getCouponCode());
                    user.setPoints(user.getPoints() - category.getPoints());
                    userRepo.updateUser(user);
                }

                giftCards.add(giftCard);
                user.setGiftCards(giftCards);
            }
            else
            {
                util.msg5();
            }
        }
        else
        {
            throw new IllegalArgumentException("input is only valid if 1,2 or 3");
        }
    }

    private void redeemItems(List<Rewards> rewards, List<User> users, User user, int userId)
    {
        System.out.println("Do you want redeem items again: " + YELLOW +  "Y/N" + RESET);
        Scanner sc = new Scanner(System.in);
        String status = sc.nextLine().trim();

        if(status.equalsIgnoreCase("Y"))
        {
            displayRewards(rewards, users, userId);
        }
        else
        {
            System.out.println(CYAN + "*************************************" +
                    "*********************************" + RESET);
            System.out.println(BLUE + "Summary of all redeemed Rewards:" + RESET);
            if(user.getGiftCards() != null && user.getGiftCards().size() >=1)
            {
                displayGiftCards(user);
            }
            else{
                System.out.println(YELLOW + "Sorry!! No Gift Cards Redeemed." + RESET);
                System.out.println(CYAN + "*************************************" +
                        "*********************************" + RESET);
            }
        }
    }

    private void displayGiftCards(User user)
    {
        List<GiftCard> giftCards1 = rewardsRepo.getGiftCards(user);

        AtomicInteger id = new AtomicInteger();
        giftCards1.forEach(giftCard -> System.out.println(YELLOW + id.incrementAndGet() + "." + giftCard.getName() + " Coupon code "
                + giftCard.getCouponCode() + " worth of "+ giftCard.getPoints()  +" points"+  RESET));

        System.out.println(PURPLE + "Use codes at www.teluskorewards.com website to purchase them.");
        System.out.println("Thanks for visiting Rewards App, see you again." + RESET);
        System.out.println(CYAN + "*****************************************" +
                "***************************" + RESET);
    }
}
