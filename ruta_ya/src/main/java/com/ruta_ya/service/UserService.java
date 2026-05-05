package com.ruta_ya.service;

import com.ruta_ya.dto.CreateUserRequest;
import com.ruta_ya.dto.UserResponse;
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
            return userRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios");
        }
    }
}
