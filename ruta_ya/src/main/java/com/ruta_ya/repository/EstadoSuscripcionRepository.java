package com.ruta_ya.repository;

import com.ruta_ya.dto.EstadoSuscripcionResponse;
import com.ruta_ya.model.EstadoSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoSuscripcionRepository extends JpaRepository<EstadoSuscripcion, Integer> {

    @Query(value = "SELECT id, nombre, descripcion FROM ESTADO_SUSCRIPCION", nativeQuery = true)
    List<EstadoSuscripcionResponse> getAll();
}
