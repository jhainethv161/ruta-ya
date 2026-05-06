package com.ruta_ya.repository;

import com.ruta_ya.dto.MarcaResponse;
import com.ruta_ya.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {

    @Query(value = "SELECT id, nombre FROM MARCA", nativeQuery = true)
    List<MarcaResponse> getAll();
}
