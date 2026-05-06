package com.ruta_ya.controller;

import com.ruta_ya.dto.request.CreateViajeRequest;
import com.ruta_ya.dto.request.UpdateViajeRequest;
import com.ruta_ya.dto.response.ViajeResponse;
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

    @PostMapping
    public ResponseEntity<String> createViaje(@RequestBody CreateViajeRequest request) {
        try {
            boolean created = viajeService.createViaje(request);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Viaje creado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el viaje");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<String> updateViaje(@PathVariable Integer codigo, @RequestBody UpdateViajeRequest request) {
        try {
            boolean updated = viajeService.updateViaje(codigo, request);
            if (updated) {
                return ResponseEntity.ok("Viaje actualizado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viaje no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> cancelarViaje(@PathVariable Integer codigo) {
        try {
            boolean cancelado = viajeService.cancelarViaje(codigo);
            if (cancelado) {
                return ResponseEntity.ok("Viaje cancelado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viaje no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> getViajeByCodigo(@PathVariable Integer codigo) {
        try {
            ViajeResponse viaje = viajeService.getViajeByCodigo(codigo);
            if (viaje != null) {
                return ResponseEntity.ok(viaje);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viaje no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

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
