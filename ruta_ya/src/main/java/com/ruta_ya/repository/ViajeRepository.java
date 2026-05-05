package com.ruta_ya.repository;

import com.ruta_ya.dto.ViajeResponse;
import com.ruta_ya.model.Viaje;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    @Query(value = """
        SELECT codigo,
               fecha_hora          AS fechaHora,
               valor_estimado      AS valorEstimado,
               id_estado           AS idEstado,
               id_direccion_origen AS idDireccionOrigen,
               id_direccion_destino AS idDireccionDestino,
               cedula_usuario      AS cedulaUsuario,
               cedula_conductor    AS cedulaConductor,
               placa_vehiculo      AS placaVehiculo
        FROM VIAJE
    """, nativeQuery = true)
    List<ViajeResponse> getAll();

    @Query(value = """
        SELECT codigo,
               fecha_hora           AS fechaHora,
               valor_estimado       AS valorEstimado,
               id_estado            AS idEstado,
               id_direccion_origen  AS idDireccionOrigen,
               id_direccion_destino AS idDireccionDestino,
               cedula_usuario       AS cedulaUsuario,
               cedula_conductor     AS cedulaConductor,
               placa_vehiculo       AS placaVehiculo
        FROM VIAJE
        WHERE codigo = :codigo
    """, nativeQuery = true)
    ViajeResponse findByCodigo(@Param("codigo") Integer codigo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE VIAJE SET id_estado = 5 WHERE codigo = :codigo", nativeQuery = true)
    int cancelar(@Param("codigo") Integer codigo);
}
