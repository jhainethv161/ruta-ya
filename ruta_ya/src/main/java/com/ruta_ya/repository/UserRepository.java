package com.ruta_ya.repository;

import com.ruta_ya.dto.CreateUserRequest;
import com.ruta_ya.dto.UpdateUserRequest;
import com.ruta_ya.dto.UserResponse;
import com.ruta_ya.model.Usuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO USUARIO (
            cedula, primer_nombre, segundo_nombre, primer_apellido,
            segundo_apellido, correo, contrasena, fecha_nacimiento,
            id_estado, id_metodo_pago_pref
        ) VALUES (
            :#{#req.cedula},
            :#{#req.primerNombre},
            :#{#req.segundoNombre},
            :#{#req.primerApellido},
            :#{#req.segundoApellido},
            :#{#req.correo},
            :#{#req.contrasena},
            :#{#req.fechaNacimiento},
            :#{#req.idEstado},
            :#{#req.idMetodoPagoPref}
        )
    """, nativeQuery = true)
    int save(@Param("req") CreateUserRequest req);

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE USUARIO
        SET primer_nombre = :#{#req.primerNombre},
            segundo_nombre = :#{#req.segundoNombre},
            primer_apellido = :#{#req.primerApellido},
            segundo_apellido = :#{#req.segundoApellido},
            correo = :#{#req.correo},
            contrasena = :#{#req.contrasena},
            fecha_nacimiento = :#{#req.fechaNacimiento},
            id_estado = :#{#req.idEstado},
            id_metodo_pago_pref = :#{#req.idMetodoPagoPref}
        WHERE cedula = :cedula
    """, nativeQuery = true)
    int update(
            @Param("cedula") String cedula,
            @Param("req") UpdateUserRequest req
    );

    @Modifying
    @Transactional
    @Query(value = "UPDATE USUARIO SET id_estado = 2 WHERE cedula = :cedula", nativeQuery = true)
    int deactivate(@Param("cedula") String cedula);

    @Query(
            nativeQuery = true,
            value = """
        SELECT 
            cedula,
            primer_nombre AS primerNombre,
            segundo_nombre AS segundoNombre,
            primer_apellido AS primerApellido,
            segundo_apellido AS segundoApellido,
            correo,
            fecha_nacimiento AS fechaNacimiento,
            id_estado AS idEstado,
            id_metodo_pago_pref AS idMetodoPagoPref
        FROM USUARIO
    """
    )
    List<UserResponse> getAll();

    @Query(
            nativeQuery = true,
            value = """
        SELECT 
            cedula,
            primer_nombre AS primerNombre,
            segundo_nombre AS segundoNombre,
            primer_apellido AS primerApellido,
            segundo_apellido AS segundoApellido,
            correo,
            fecha_nacimiento AS fechaNacimiento,
            id_estado AS idEstado,
            id_metodo_pago_pref AS idMetodoPagoPref
        FROM USUARIO
        WHERE cedula = :cedula
    """
    )
    UserResponse findByCedula(String cedula);
}
