package com.pfizer.sacchonapi;

import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;

import javax.persistence.EntityManager;

public class AddMediData {

    public static void main(String[] args) {
        EntityManager em   = JpaUtil.getEntityManager();
        MediDataRepository mediDataRepository = new MediDataRepository(em);

        MediData m = new MediData();
        m.setCarb(1);
        m.setGlucose(1.5);
        mediDataRepository.save(m);
    }
}
