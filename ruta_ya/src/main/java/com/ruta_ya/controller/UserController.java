package com.ruta_ya.controller;

import com.ruta_ya.dto.request.CreateUserRequest;
import com.ruta_ya.dto.request.UpdateUserRequest;
import com.ruta_ya.dto.response.UserResponse;
import com.ruta_ya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        try {
            boolean created = userService.createUser(request);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo crear el usuario");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{cedula}")
    public ResponseEntity<String> updateUser(@PathVariable String cedula, @RequestBody UpdateUserRequest request) {
        try {
            boolean updated = userService.updateUser(cedula, request);
            if (updated) {
                return ResponseEntity.ok("Usuario actualizado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cedula}")
    public ResponseEntity<String> deleteUser(@PathVariable String cedula) {
        try {
            boolean deactivated = userService.deactivateUser(cedula);
            if (deactivated) {
                return ResponseEntity.ok("Usuario eliminado exitosamente");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<?> getUserByCedula(@PathVariable String cedula) {
        try {
            UserResponse user = userService.getUserByCedula(cedula);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
