package com.ruta_ya.repository.catalogo;

import com.ruta_ya.dto.response.catalogo.MetodoPagoResponse;
import com.ruta_ya.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {

    @Query(value = "SELECT id, nombre, descripcion FROM METODO_PAGO", nativeQuery = true)
    List<MetodoPagoResponse> getAll();
}
