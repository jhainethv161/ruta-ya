package com.ruta_ya.service;

import com.ruta_ya.dto.request.CreateUserRequest;
import com.ruta_ya.dto.request.UpdateUserRequest;
import com.ruta_ya.dto.response.UserResponse;
import com.ruta_ya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public boolean createUser(CreateUserRequest request) {
        try {
            int rows = userRepository.save(request);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al crear el usuario");
        }
    }

    public List<UserResponse> getAllUsers() {
        try {
            return userRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios");
        }
    }

    public UserResponse getUserByCedula(String cedula) {
        try {
            return userRepository.findByCedula(cedula);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el usuario");
        }
    }

    public boolean deactivateUser(String cedula) {
        try {
            int rows = userRepository.deactivate(cedula);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al eliminar el usuario");
        }
    }

    public boolean updateUser(String cedula, UpdateUserRequest request) {
        try {
            int rows = userRepository.update(cedula, request);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al actualizar el usuario");
        }
    }
}
