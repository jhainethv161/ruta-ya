package com.ruta_ya.service;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.repository.EstadoUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstadoUsuarioService {

    private final EstadoUsuarioRepository estadoUsuarioRepository;

    public List<EstadoUsuarioResponse> getAllEstados() {
        try {
            return estadoUsuarioRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los estados de usuario");
        }
    }
}
