package com.ruta_ya.service;

import com.ruta_ya.dto.CreateSuscripcionRequest;
import com.ruta_ya.repository.SuscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;

    public boolean createSuscripcion(CreateSuscripcionRequest request) {
        try {
            int rows = suscripcionRepository.create(request);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al crear la suscripción");
        }
    }
}
