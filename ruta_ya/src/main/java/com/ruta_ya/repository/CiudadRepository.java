package com.ruta_ya.repository;

import com.ruta_ya.dto.CiudadResponse;
import com.ruta_ya.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, String> {

    @Query(value = "SELECT codigo, nombre, codigo_departamento AS codigoDepartamento FROM CIUDAD", nativeQuery = true)
    List<CiudadResponse> getAll();
}
