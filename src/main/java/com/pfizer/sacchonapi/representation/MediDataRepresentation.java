package com.pfizer.sacchonapi.representation;

import com.pfizer.sacchonapi.model.MediData;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class MediDataRepresentation {

    private double glucose;
    private double carb;
    private Date measuredDate;
    private long consultation_id;
    private String uri;

    public MediDataRepresentation(MediData mediData)
    {
        if(mediData != null)
        {
            glucose = mediData.getGlucose();
            carb = mediData.getCarb();
            measuredDate = mediData.getMeasuredDate();
            uri = "http://localhost:9000/v1/medidata/" + mediData.getId();
            try {
                consultation_id = mediData.getConsultation().getId();
            }catch (Exception e){
                consultation_id = 0;
            }
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
