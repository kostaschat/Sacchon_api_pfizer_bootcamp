package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.MediData;
import org.eclipse.jetty.util.thread.strategy.ProduceConsume;

import java.util.Date;

public class MediDataRepresentation {

    private double glucose;
    private double carb;
    private Date measuredDate;

    //The URL of the resource
    private String url;

    public MediDataRepresentation(MediData mediData)
    {

        if(mediData != null)
        {
            glucose = mediData.getGlucose();
            carb = mediData.getCarb();
            measuredDate = mediData.getMeasuredDate();
            //url = "http://localhost:9000/user/viewMediData" + "?id='userId'";
        }

    }


    public MediData createMediData(){

        MediData mediData = new MediData();
        mediData.setGlucose(glucose);
        mediData.setCarb(carb);
        mediData.setMeasuredDate(measuredDate);
        return mediData;
    }
}
