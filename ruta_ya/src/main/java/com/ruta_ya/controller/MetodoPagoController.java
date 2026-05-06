package com.ruta_ya.controller;

import com.ruta_ya.dto.MetodoPagoResponse;
import com.ruta_ya.service.MetodoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<?> getAllMetodosPago() {
        try {
            List<MetodoPagoResponse> metodos = metodoPagoService.getAllMetodosPago();
            return ResponseEntity.ok(metodos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
