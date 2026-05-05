package com.ruta_ya.repository;

import com.ruta_ya.dto.CreateUserRequest;
import com.ruta_ya.dto.UpdateUserRequest;
import com.ruta_ya.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private static final String INSERT_USER = """
            INSERT INTO USUARIO (cedula, primer_nombre, segundo_nombre, primer_apellido,
                                 segundo_apellido, correo, contrasena, fecha_nacimiento,
                                 id_estado, id_metodo_pago_pref)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_ALL_USERS = """
            SELECT cedula, primer_nombre, segundo_nombre, primer_apellido,
                   segundo_apellido, correo, fecha_nacimiento,
                   id_estado, id_metodo_pago_pref
            FROM USUARIO
            """;

    private static final String UPDATE_USER = """
            UPDATE USUARIO
            SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?,
                segundo_apellido = ?, correo = ?, contrasena = ?,
                fecha_nacimiento = ?, id_estado = ?, id_metodo_pago_pref = ?
            WHERE cedula = ?
            """;

    private static final String SELECT_USER_BY_CEDULA = """
            SELECT cedula, primer_nombre, segundo_nombre, primer_apellido,
                   segundo_apellido, correo, fecha_nacimiento,
                   id_estado, id_metodo_pago_pref
            FROM USUARIO
            WHERE cedula = ?
            """;

    private static final RowMapper<UserResponse> USER_ROW_MAPPER = (rs, rowNum) -> {
        UserResponse user = new UserResponse();
        user.setCedula(rs.getString("cedula"));
        user.setPrimerNombre(rs.getString("primer_nombre"));
        user.setSegundoNombre(rs.getString("segundo_nombre"));
        user.setPrimerApellido(rs.getString("primer_apellido"));
        user.setSegundoApellido(rs.getString("segundo_apellido"));
        user.setCorreo(rs.getString("correo"));
        user.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        user.setIdEstado(rs.getInt("id_estado"));
        user.setIdMetodoPagoPref(rs.getInt("id_metodo_pago_pref"));
        return user;
    };

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

    public List<UserResponse> findAll() {
        return jdbcTemplate.query(SELECT_ALL_USERS, USER_ROW_MAPPER);
    }

    public Optional<UserResponse> findByCedula(String cedula) {
        List<UserResponse> results = jdbcTemplate.query(SELECT_USER_BY_CEDULA, USER_ROW_MAPPER, cedula);
        return results.stream().findFirst();
    }

    public int update(String cedula, UpdateUserRequest request) {
        return jdbcTemplate.update(UPDATE_USER,
                request.getPrimerNombre(),
                request.getSegundoNombre(),
                request.getPrimerApellido(),
                request.getSegundoApellido(),
                request.getCorreo(),
                request.getContrasena(),
                request.getFechaNacimiento(),
                request.getIdEstado(),
                request.getIdMetodoPagoPref(),
                cedula
        );
    }
}
