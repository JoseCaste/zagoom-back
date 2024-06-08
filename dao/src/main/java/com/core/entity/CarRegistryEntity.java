package com.core.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "car_registry_aux")
@Data
public class CarRegistryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carRegistryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_fk")
    private Usuario usuario;
    private String noMotor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_fk")
    private CarModelEntity carModel;
    private String color;
    private String anio;
    @Column(name = "id_status")
    private int status;
}
