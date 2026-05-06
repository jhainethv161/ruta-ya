package com.ruta_ya.service;

import com.ruta_ya.dto.MetodoPagoResponse;
import com.ruta_ya.repository.MetodoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPagoResponse> getAllMetodosPago() {
        try {
            return metodoPagoRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los métodos de pago");
        }
    }
}
