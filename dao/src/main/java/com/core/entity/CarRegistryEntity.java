package com.core.entity;

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

    @OneToMany(mappedBy = "carRegistry",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TemporalCarInpectionEntity> temporalCarInpections;




    @Override
    public String toString() {
        return "CarRegistryEntity{" +
                "carRegistryId=" + carRegistryId +
                ", usuario=" + usuario +
                ", noMotor='" + noMotor + '\'' +
                ", carModel=" + carModel +
                ", color='" + color + '\'' +
                ", anio='" + anio + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRegistryEntity that = (CarRegistryEntity) o;
        return status == that.status && Objects.equals(carRegistryId, that.carRegistryId) && Objects.equals(usuario, that.usuario) && Objects.equals(noMotor, that.noMotor) && Objects.equals(carModel, that.carModel) && Objects.equals(color, that.color) && Objects.equals(anio, that.anio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carRegistryId, usuario, noMotor, carModel, color, anio, status);
    }
}
