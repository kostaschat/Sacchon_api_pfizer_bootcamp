package com.pfizer.sacchonapi;

import com.pfizer.sacchonapi.model.MediData;
import com.pfizer.sacchonapi.model.Patient;
import com.pfizer.sacchonapi.repository.MediDataRepository;
import com.pfizer.sacchonapi.repository.PatientRepository;
import com.pfizer.sacchonapi.repository.util.JpaUtil;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMediData {

    public static void main(String[] args) {
        EntityManager em   = JpaUtil.getEntityManager();
        MediDataRepository mediDataRepository = new MediDataRepository(em);

        MediData m = new MediData();
        m.setCarb(1);
        m.setGlucose(1.5);
        mediDataRepository.save(m);

        PatientRepository pr = new PatientRepository(em);
        Patient p = new Patient();
//        p.setFirstName("Kosmas");
//        p.setLastName("Aitwlos");

//        p.setUsername("k_aitwlos");
//        p.setEmail("kaitwlos@gmail.com");
//        p.setPassword("a76d7a7");
//        p.setAddress("Iaswnidou 27");
//        p.setCity("Thessaloniki");
//        p.setZipCode("55167");
//        p.setPhoneNumber("6973878372");
//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//
        Date d1 = null;
//        try {
//            d1 = sdf.parse("1960-11-10");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        p.setDob(d1);
//
//        Date d2 = null;
//        try {
//            d2 = sdf.parse("2020-09-17");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        p.setCreationDate(d2);
//
//        p.setActive(true);
//        p.setHasConsultation(false);
//        p.setConsultationPending(true);
//        p.setHasDoctor(true);
//
//        pr.save(p);


        Patient p1 = new Patient();
        p1.setFirstName("Anestis");
        p1.setLastName("Aitwlos");
        p1.setUsername("k_aitwlos");
        p1.setEmail("kaitwlos@gmail.com");
        p1.setPassword("a76d7a7");
        p1.setAddress("Iaswnidou 27");
        p1.setCity("Thessaloniki");
        p1.setZipCode("55167");
        p1.setPhoneNumber("6973878372");

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-mm-dd");

        Date d3 = null;
        try {
            d3 = sdf.parse("1953-11-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        p1.setDob(d1);

        Date d4 = null;
        try {
            d4 = sdf.parse("2020-04-17");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        p1.setCreationDate(d1);

        p1.setActive(true);
        p1.setHasConsultation(false);
        p1.setConsultationPending(true);
        p1.setHasDoctor(true);

        pr.save(p1);

    }
}
