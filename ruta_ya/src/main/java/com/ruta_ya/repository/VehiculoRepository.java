package com.ruta_ya.repository;

import com.ruta_ya.dto.request.CreateVehiculoRequest;
import com.ruta_ya.dto.request.UpdateVehiculoRequest;
import com.ruta_ya.dto.response.VehiculoResponse;
import com.ruta_ya.model.Vehiculo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO VEHICULO (placa, modelo, id_marca, id_tipo)
        VALUES (
            :#{#req.placa},
            :#{#req.modelo},
            :#{#req.idMarca},
            :#{#req.idTipo}
        )
    """, nativeQuery = true)
    int create(@Param("req") CreateVehiculoRequest req);

    @Query(value = "SELECT placa, modelo, id_marca AS idMarca, id_tipo AS idTipo FROM VEHICULO", nativeQuery = true)
    List<VehiculoResponse> getAll();

    @Query(value = "SELECT placa, modelo, id_marca AS idMarca, id_tipo AS idTipo FROM VEHICULO WHERE placa = :placa", nativeQuery = true)
    VehiculoResponse findByPlaca(@Param("placa") String placa);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE VEHICULO
        SET modelo = :#{#req.modelo},
            id_marca = :#{#req.idMarca},
            id_tipo = :#{#req.idTipo}
        WHERE placa = :placa
    """, nativeQuery = true)
    int update(@Param("placa") String placa, @Param("req") UpdateVehiculoRequest req);
}
