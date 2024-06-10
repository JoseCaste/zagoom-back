package com.core.repository;

import com.core.entity.ElementoAutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElementoAutoRepository extends JpaRepository<ElementoAutoEntity, Long> {
    Optional<ElementoAutoEntity> findByElemento(String claveElemento);
}
