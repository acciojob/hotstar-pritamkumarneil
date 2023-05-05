package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import com.driver.transformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo

        int productionHouseId=webSeriesEntryDto.getProductionHouseId();
        ProductionHouse productionHouse=null;

        if(productionHouseRepository.findById(productionHouseId).isPresent()){
            productionHouse=productionHouseRepository.findById(productionHouseId).get();
        }
        else{
            throw new Exception();
        }
        // checking if webseries is already present or not
        if(webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName())!=null){
            throw new Exception("Series is already present");
        }
        WebSeries webSeries= Transformer.webSeriesEntryDtoToWebseries(webSeriesEntryDto);
        webSeries.setProductionHouse(productionHouse);
        productionHouse.getWebSeriesList().add(webSeries);
        productionHouseRepository.save(productionHouse);

        return 1;
    }

}
