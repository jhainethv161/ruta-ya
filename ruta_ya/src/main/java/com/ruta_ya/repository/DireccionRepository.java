package com.ruta_ya.repository;

import com.ruta_ya.model.Direccion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO DIRECCION (direccion, descripcion, codigo_ciudad)
        VALUES (:direccion, :descripcion, :codigoCiudad)
    """, nativeQuery = true)
    int insert(@Param("direccion") String direccion,
               @Param("descripcion") String descripcion,
               @Param("codigoCiudad") String codigoCiudad);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer lastInsertId();

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE DIRECCION
        SET direccion = :direccion,
            descripcion = :descripcion,
            codigo_ciudad = :codigoCiudad
        WHERE codigo = :codigo
    """, nativeQuery = true)
    int update(@Param("codigo") Integer codigo,
               @Param("direccion") String direccion,
               @Param("descripcion") String descripcion,
               @Param("codigoCiudad") String codigoCiudad);
}
