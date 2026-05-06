package com.ruta_ya.controller;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.service.EstadoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados-usuario")
@RequiredArgsConstructor
public class EstadoUsuarioController {

    private final EstadoUsuarioService estadoUsuarioService;

    @GetMapping
    public ResponseEntity<?> getAllEstados() {
        try {
            List<EstadoUsuarioResponse> estados = estadoUsuarioService.getAllEstados();
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
