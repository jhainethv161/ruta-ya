package com.ruta_ya.service;

import com.ruta_ya.dto.ViajeResponse;
import com.ruta_ya.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;

    public List<ViajeResponse> getAllViajes() {
        try {
            return viajeRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los viajes");
        }
    }

    public ViajeResponse getViajeByCodigo(Integer codigo) {
        try {
            return viajeRepository.findByCodigo(codigo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el viaje");
        }
    }

    public boolean cancelarViaje(Integer codigo) {
        try {
            int rows = viajeRepository.cancelar(codigo);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al cancelar el viaje");
        }
    }
}
