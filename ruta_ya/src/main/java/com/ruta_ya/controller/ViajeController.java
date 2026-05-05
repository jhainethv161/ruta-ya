package com.ruta_ya.controller;

import com.ruta_ya.dto.ViajeResponse;
import com.ruta_ya.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viajes")
@RequiredArgsConstructor
public class ViajeController {

    private final ViajeService viajeService;

    @GetMapping
    public ResponseEntity<?> getAllViajes() {
        try {
            List<ViajeResponse> viajes = viajeService.getAllViajes();
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
