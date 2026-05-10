package com.ruta_ya.service;

import com.ruta_ya.dto.response.VehiculoConductorResponse;
import com.ruta_ya.repository.VehiculoConductorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculoConductorService {

    private final VehiculoConductorRepository vehiculoConductorRepository;

    public List<VehiculoConductorResponse> getAllVehiculosConductores() {
        try {
            return vehiculoConductorRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los vehículos-conductores");
        }
    }
}
