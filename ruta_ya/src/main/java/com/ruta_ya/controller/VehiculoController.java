package com.ruta_ya.controller;

import com.ruta_ya.dto.CreateVehiculoRequest;
import com.ruta_ya.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<String> createVehiculo(@RequestBody CreateVehiculoRequest request) {
        try {
            boolean created = vehiculoService.createVehiculo(request);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Vehículo creado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el vehículo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
