package com.ruta_ya.service;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.dto.MetodoPagoResponse;
import com.ruta_ya.repository.EstadoUsuarioRepository;
import com.ruta_ya.repository.MetodoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogoService {

    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    public List<EstadoUsuarioResponse> getAllEstadosUsuario() {
        try {
            return estadoUsuarioRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los estados de usuario");
        }
    }

    public List<MetodoPagoResponse> getAllMetodosPago() {
        try {
            return metodoPagoRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los métodos de pago");
        }
    }
}
