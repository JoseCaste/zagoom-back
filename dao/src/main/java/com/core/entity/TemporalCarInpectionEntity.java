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
import java.util.Objects;

@Entity
@Table(name = "temporal_car_inspection")
@Data
public class TemporalCarInpectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temporal_car_inspection_id")
    private Long temporalCarInspectionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_registry_fk")
    private CarRegistryEntity carRegistry;
}
