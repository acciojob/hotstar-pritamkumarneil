package com.driver.transformer;

import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.WebSeries;
import com.driver.services.SubscriptionService;

public class Transformer {
    public static ProductionHouse productionHouseEntryDtoToProductionHouse(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionHouse=new ProductionHouse();
        productionHouse.setName(productionHouseEntryDto.getName());
        productionHouse.setRatings(0.0);
        return productionHouse;
    }
    public static Subscription subscriptionEntryDtoToSubscription(SubscriptionEntryDto subscriptionEntryDto){
        Subscription subscription=new Subscription();
        SubscriptionType subscriptionType=subscriptionEntryDto.getSubscriptionType();
        subscription.setSubscriptionType(subscriptionType);
        // subscription date is automatically created
        // calculating the total amount of subscription
        int noOfScreen=subscriptionEntryDto.getNoOfScreensRequired();
        subscription.setNoOfScreensSubscribed(noOfScreen);
        int totalAmount=0;
        if(subscriptionType.toString().equals("BASIC")) {
            totalAmount=500+ noOfScreen*200;
        }else if(subscriptionType.toString().equals("PRO")){
            totalAmount=800+ noOfScreen*250;
        }else{
            totalAmount=1000+ noOfScreen*350;
        }
        subscription.setTotalAmountPaid(totalAmount);
        return subscription;
    }
    public static WebSeries webSeriesEntryDtoToWebseries(WebSeriesEntryDto webSeriesEntryDto){
        WebSeries webSeries=new WebSeries();
        webSeries.setSeriesName(webSeriesEntryDto.getSeriesName());
        webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        webSeries.setRating(webSeriesEntryDto.getRating());
        webSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
        return webSeries;
    }
}
