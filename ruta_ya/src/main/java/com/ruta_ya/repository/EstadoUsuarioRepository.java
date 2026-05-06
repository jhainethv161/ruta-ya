package com.ruta_ya.repository;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.model.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoUsuarioRepository extends JpaRepository<EstadoUsuario, Integer> {

    @Query(value = "SELECT id, nombre, descripcion FROM ESTADO_USUARIO", nativeQuery = true)
    List<EstadoUsuarioResponse> getAll();
}
