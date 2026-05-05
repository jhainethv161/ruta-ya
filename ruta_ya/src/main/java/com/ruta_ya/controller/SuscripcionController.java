package com.ruta_ya.controller;

import com.ruta_ya.dto.CreateSuscripcionRequest;
import com.ruta_ya.dto.UpdateSuscripcionRequest;
import com.ruta_ya.dto.SuscripcionResponse;
import com.ruta_ya.service.SuscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    @PostMapping
    public ResponseEntity<String> createSuscripcion(@RequestBody CreateSuscripcionRequest request) {
        try {
            boolean created = suscripcionService.createSuscripcion(request);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Suscripción creada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear la suscripción");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllSuscripciones() {
        try {
            List<SuscripcionResponse> suscripciones = suscripcionService.getAllSuscripciones();
            return ResponseEntity.ok(suscripciones);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<String> updateSuscripcion(@PathVariable Integer codigo, @RequestBody UpdateSuscripcionRequest request) {
        try {
            boolean updated = suscripcionService.updateSuscripcion(codigo, request);
            if (updated) {
                return ResponseEntity.ok("Suscripción actualizada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suscripción no encontrada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> deleteSuscripcion(@PathVariable Integer codigo) {
        try {
            boolean deleted = suscripcionService.deleteSuscripcion(codigo);
            if (deleted) {
                return ResponseEntity.ok("Suscripción eliminada exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suscripción no encontrada");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{cedula}")
    public ResponseEntity<?> getSuscripcionesByCedula(@PathVariable String cedula) {
        try {
            List<SuscripcionResponse> suscripciones = suscripcionService.getSuscripcionesByCedula(cedula);
            return ResponseEntity.ok(suscripciones);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
