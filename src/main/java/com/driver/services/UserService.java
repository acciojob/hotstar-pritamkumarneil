package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
        User currUser=userRepository.save(user);
        return currUser.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        int count=0;
        User user=userRepository.findById(userId).get();
        // getting subscription type that this user have subscribed onto
        SubscriptionType subscriptionType=user.getSubscription().getSubscriptionType();
        List<WebSeries> webSeries=webSeriesRepository.findAllBySubscriptionType(subscriptionType.toString());

        for(WebSeries webSeries1:webSeries){
            if(webSeries1.getAgeLimit()<=user.getAge()){
                count++;
            }
        }

        return count;
    }


}
