package com.ruta_ya.repository;

import com.ruta_ya.dto.response.VehiculoConductorResponse;
import com.ruta_ya.model.VehiculoConductor;
import com.ruta_ya.model.VehiculoConductorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoConductorRepository extends JpaRepository<VehiculoConductor, VehiculoConductorId> {

    @Query(value = "SELECT cedula_conductor AS cedulaConductor, placa_vehiculo AS placaVehiculo, disponible FROM VEHICULO_CONDUCTOR", nativeQuery = true)
    List<VehiculoConductorResponse> getAll();
}
