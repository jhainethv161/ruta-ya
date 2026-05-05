package com.ruta_ya.repository;

import com.ruta_ya.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private static final String INSERT_USER = """
            INSERT INTO USUARIO (cedula, primer_nombre, segundo_nombre, primer_apellido,
                                 segundo_apellido, correo, contrasena, fecha_nacimiento,
                                 id_estado, id_metodo_pago_pref)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    public int save(CreateUserRequest request) {
        return jdbcTemplate.update(INSERT_USER,
                request.getCedula(),
                request.getPrimerNombre(),
                request.getSegundoNombre(),
                request.getPrimerApellido(),
                request.getSegundoApellido(),
                request.getCorreo(),
                request.getContrasena(),
                request.getFechaNacimiento(),
                request.getIdEstado(),
                request.getIdMetodoPagoPref()
        );
    }
}
