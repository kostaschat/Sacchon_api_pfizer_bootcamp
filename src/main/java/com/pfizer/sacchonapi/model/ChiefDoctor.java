package com.pfizer.sacchonapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ChiefDoctor{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
}
