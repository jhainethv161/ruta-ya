package com.ruta_ya.repository;

import com.ruta_ya.dto.TipoVehiculoResponse;
import com.ruta_ya.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Integer> {

    @Query(value = "SELECT id, nombre FROM TIPO_VEHICULO", nativeQuery = true)
    List<TipoVehiculoResponse> getAll();
}
