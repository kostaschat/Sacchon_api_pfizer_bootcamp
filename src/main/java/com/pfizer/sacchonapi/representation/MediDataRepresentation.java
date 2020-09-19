package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.MediData;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jetty.util.thread.strategy.ProduceConsume;

import java.util.Date;

@Data
@NoArgsConstructor
public class MediDataRepresentation {

    private double glucose;
    private double carb;
    private Date measuredDate;

    private String uri;

    public MediDataRepresentation(MediData mediData)
    {
        if(mediData != null)
        {
            glucose = mediData.getGlucose();
            carb = mediData.getCarb();
            measuredDate = mediData.getMeasuredDate();
            uri = "http://localhost:9000/v1/medidata/" + mediData.getId();
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
