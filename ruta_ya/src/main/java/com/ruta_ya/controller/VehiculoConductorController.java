package com.ruta_ya.controller;

import com.ruta_ya.dto.response.VehiculoConductorResponse;
import com.ruta_ya.service.VehiculoConductorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehiculo-conductor")
@RequiredArgsConstructor
public class VehiculoConductorController {

    private final VehiculoConductorService vehiculoConductorService;

    @GetMapping
    public ResponseEntity<?> getAllVehiculosConductores() {
        try {
            List<VehiculoConductorResponse> vehiculosConductores = vehiculoConductorService.getAllVehiculosConductores();
            return ResponseEntity.ok(vehiculosConductores);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
