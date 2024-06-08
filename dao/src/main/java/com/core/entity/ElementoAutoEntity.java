package com.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "car_brand")
@Data
public class CarBrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_brand_id")
    private Long carBrandId;

    @Column(name = "brand_name")
    private String brandName;

    @JsonIgnore
    @OneToMany(mappedBy = "carBrand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CarModelEntity> carModels;
}
