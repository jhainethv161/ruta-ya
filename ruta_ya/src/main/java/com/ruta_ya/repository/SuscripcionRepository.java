package com.ruta_ya.repository;

import com.ruta_ya.dto.CreateSuscripcionRequest;
import com.ruta_ya.model.Suscripcion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuscripcionRepository extends JpaRepository<Suscripcion, Integer> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO SUSCRIPCION (id_tipo, id_estado, cedula_usuario)
        VALUES (
            :#{#req.idTipo},
            :#{#req.idEstado},
            :#{#req.cedulaUsuario}
        )
    """, nativeQuery = true)
    int create(@Param("req") CreateSuscripcionRequest req);
}
