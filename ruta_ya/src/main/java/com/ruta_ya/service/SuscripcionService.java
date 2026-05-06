package com.ruta_ya.service;

import com.ruta_ya.dto.request.CreateSuscripcionRequest;
import com.ruta_ya.dto.request.UpdateSuscripcionRequest;
import com.ruta_ya.dto.response.SuscripcionResponse;
import com.ruta_ya.repository.SuscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<SuscripcionResponse> getAllSuscripciones() {
        try {
            return suscripcionRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las suscripciones");
        }
    }

    public List<SuscripcionResponse> getSuscripcionesByCedula(String cedula) {
        try {
            return suscripcionRepository.findByCedulaUsuario(cedula);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las suscripciones del usuario");
        }
    }

    public boolean updateSuscripcion(Integer codigo, UpdateSuscripcionRequest request) {
        try {
            int rows = suscripcionRepository.update(codigo, request);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al actualizar la suscripción");
        }
    }

    public boolean deleteSuscripcion(Integer codigo) {
        try {
            int rows = suscripcionRepository.delete(codigo);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al eliminar la suscripción");
        }
    }
}
