package com.ruta_ya.repository;

import com.ruta_ya.dto.response.ViajeResponse;
import com.ruta_ya.model.Viaje;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO VIAJE (fecha_hora, valor_estimado, id_estado, id_direccion_origen, id_direccion_destino, cedula_usuario, cedula_conductor, placa_vehiculo)
        VALUES (:fechaHora, :valorEstimado, :idEstado, :idDireccionOrigen, :idDireccionDestino, :cedulaUsuario, :cedulaConductor, :placaVehiculo)
    """, nativeQuery = true)
    int create(@Param("fechaHora") LocalDateTime fechaHora,
               @Param("valorEstimado") BigDecimal valorEstimado,
               @Param("idEstado") Integer idEstado,
               @Param("idDireccionOrigen") Integer idDireccionOrigen,
               @Param("idDireccionDestino") Integer idDireccionDestino,
               @Param("cedulaUsuario") String cedulaUsuario,
               @Param("cedulaConductor") String cedulaConductor,
               @Param("placaVehiculo") String placaVehiculo);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE VIAJE
        SET valor_estimado   = :valorEstimado,
            id_estado        = :idEstado,
            cedula_usuario   = :cedulaUsuario,
            cedula_conductor = :cedulaConductor,
            placa_vehiculo   = :placaVehiculo
        WHERE codigo = :codigo
    """, nativeQuery = true)
    int update(@Param("codigo") Integer codigo,
               @Param("valorEstimado") BigDecimal valorEstimado,
               @Param("idEstado") Integer idEstado,
               @Param("cedulaUsuario") String cedulaUsuario,
               @Param("cedulaConductor") String cedulaConductor,
               @Param("placaVehiculo") String placaVehiculo);
}
