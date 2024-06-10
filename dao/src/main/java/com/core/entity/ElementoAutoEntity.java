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

@Entity
@Table(name = "elementos_auto")
@Data
public class ElementoAutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_elemento")
    private Long idElemento;

    @Column(name = "elemento")
    private String elemento;
    @OneToMany(mappedBy = "elementoAuto",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TemporalCarInpectionEntity> temporalCarInpections;
}
