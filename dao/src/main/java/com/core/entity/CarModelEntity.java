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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_model")
@Data
public class CarModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_model_id")
    private Long carModelId;

    @Column(name = "car_model_name")
    private String carModelName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_brand_id_fk")
    CarBrandEntity carBrand;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<CarRegistryEntity> carRegistries;

    @Override
    public String toString() {
        return "CarModelEntity{" +
                "carModelId=" + carModelId +
                ", carModelName='" + carModelName + '\'' +
                ", carBrand=" + carBrand +
                ", carRegistries=" + carRegistries +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarModelEntity carModel = (CarModelEntity) o;
        return Objects.equals(carModelId, carModel.carModelId) && Objects.equals(carModelName, carModel.carModelName) && Objects.equals(carBrand, carModel.carBrand) && Objects.equals(carRegistries, carModel.carRegistries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carModelId, carModelName, carBrand, carRegistries);
    }
}
