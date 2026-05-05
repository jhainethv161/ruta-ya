package com.ruta_ya.repository;

import com.ruta_ya.dto.CreateVehiculoRequest;
import com.ruta_ya.model.Vehiculo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
