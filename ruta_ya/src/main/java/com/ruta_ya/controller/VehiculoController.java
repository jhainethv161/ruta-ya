package com.ruta_ya.controller;

import com.ruta_ya.dto.request.CreateVehiculoRequest;
import com.ruta_ya.dto.request.UpdateVehiculoRequest;
import com.ruta_ya.dto.response.VehiculoResponse;
import com.ruta_ya.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<?> getAllVehiculos() {
        try {
            List<VehiculoResponse> vehiculos = vehiculoService.getAllVehiculos();
            return ResponseEntity.ok(vehiculos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{placa}")
    public ResponseEntity<?> getVehiculoByPlaca(@PathVariable String placa) {
        try {
            VehiculoResponse vehiculo = vehiculoService.getVehiculoByPlaca(placa);
            if (vehiculo != null) {
                return ResponseEntity.ok(vehiculo);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehículo no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{placa}")
    public ResponseEntity<String> updateVehiculo(@PathVariable String placa, @RequestBody UpdateVehiculoRequest request) {
        try {
            boolean updated = vehiculoService.updateVehiculo(placa, request);
            if (updated) {
                return ResponseEntity.ok("Vehículo actualizado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehículo no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

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
