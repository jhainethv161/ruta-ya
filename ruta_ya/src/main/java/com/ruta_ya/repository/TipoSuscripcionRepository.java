package com.ruta_ya.repository;

import com.ruta_ya.dto.TipoSuscripcionResponse;
import com.ruta_ya.model.TipoSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoSuscripcionRepository extends JpaRepository<TipoSuscripcion, Integer> {

    @Query(value = "SELECT id, nombre, descripcion FROM TIPO_SUSCRIPCION", nativeQuery = true)
    List<TipoSuscripcionResponse> getAll();
}
