package com.ruta_ya.controller;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.dto.MarcaResponse;
import com.ruta_ya.dto.MetodoPagoResponse;
import com.ruta_ya.dto.EstadoSuscripcionResponse;
import com.ruta_ya.dto.TipoSuscripcionResponse;
import com.ruta_ya.dto.TipoVehiculoResponse;
import com.ruta_ya.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping("/estados-usuario")
    public ResponseEntity<?> getAllEstadosUsuario() {
        try {
            List<EstadoUsuarioResponse> estados = catalogoService.getAllEstadosUsuario();
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/metodos-pago")
    public ResponseEntity<?> getAllMetodosPago() {
        try {
            List<MetodoPagoResponse> metodos = catalogoService.getAllMetodosPago();
            return ResponseEntity.ok(metodos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/marcas")
    public ResponseEntity<?> getAllMarcas() {
        try {
            List<MarcaResponse> marcas = catalogoService.getAllMarcas();
            return ResponseEntity.ok(marcas);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/tipos-vehiculo")
    public ResponseEntity<?> getAllTiposVehiculo() {
        try {
            List<TipoVehiculoResponse> tipos = catalogoService.getAllTiposVehiculo();
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/tipos-suscripcion")
    public ResponseEntity<?> getAllTiposSuscripcion() {
        try {
            List<TipoSuscripcionResponse> tipos = catalogoService.getAllTiposSuscripcion();
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/estados-suscripcion")
    public ResponseEntity<?> getAllEstadosSuscripcion() {
        try {
            List<EstadoSuscripcionResponse> estados = catalogoService.getAllEstadosSuscripcion();
            return ResponseEntity.ok(estados);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
