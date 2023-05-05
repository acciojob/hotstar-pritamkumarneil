package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import com.driver.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        User user=userRepository.findById(subscriptionEntryDto.getUserId()).get();

        Subscription subscription= Transformer.subscriptionEntryDtoToSubscription(subscriptionEntryDto);

        subscription.setUser(user);
        user.setSubscription(subscription);
        userRepository.save(user);

        return user.getId();
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        User user=userRepository.findById(userId).get();
        Subscription subscription=user.getSubscription();
        SubscriptionType subscriptionType=subscription.getSubscriptionType();
        SubscriptionType newSubscriptionType=null;
        int currAmount=subscription.getTotalAmountPaid();
        int newAmount=0;
        if(subscriptionType.toString().equals("ELITE")){
            throw new Exception("Already the best Subscription");
        }
        else if(subscriptionType.toString().equals("PRO")){
            newAmount=1000+ 350*subscription.getNoOfScreensSubscribed();
            newSubscriptionType=SubscriptionType.ELITE;
        }else{
          newAmount=800+250*subscription.getNoOfScreensSubscribed();
          newSubscriptionType=SubscriptionType.PRO;
        }
        subscription.setSubscriptionType(newSubscriptionType);
        subscription.setTotalAmountPaid(newAmount);
        subscriptionRepository.save(subscription);
        return newAmount-currAmount;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<Subscription> subscriptions=subscriptionRepository.findAll();
        int revenue=0;
        for(Subscription subscription: subscriptions){
            revenue+=subscription.getTotalAmountPaid();
        }
        return revenue;
    }

}
