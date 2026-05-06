package com.ruta_ya.repository;

import com.ruta_ya.dto.EstadoViajeResponse;
import com.ruta_ya.model.EstadoViaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoViajeRepository extends JpaRepository<EstadoViaje, Integer> {

    @Query(value = "SELECT id, nombre, descripcion FROM ESTADO_VIAJE", nativeQuery = true)
    List<EstadoViajeResponse> getAll();
}
