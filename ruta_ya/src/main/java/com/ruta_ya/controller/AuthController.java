package com.ruta_ya.controller;

import com.ruta_ya.dto.request.LoginRequest;
import com.ruta_ya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean authenticated = userService.authenticateAdmin(request.getCorreo(), request.getContrasena());
        if (authenticated) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credenciales inválidas");
    }
}
