package com.ruta_ya.service;

import com.ruta_ya.dto.request.CreateVehiculoRequest;
import com.ruta_ya.dto.request.UpdateVehiculoRequest;
import com.ruta_ya.dto.response.VehiculoResponse;
import com.ruta_ya.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public boolean createVehiculo(CreateVehiculoRequest request) {
        try {
            int rows = vehiculoRepository.create(request);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al crear el vehículo");
        }
    }

    public List<VehiculoResponse> getAllVehiculos() {
        try {
            return vehiculoRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los vehículos");
        }
    }

    public VehiculoResponse getVehiculoByPlaca(String placa) {
        try {
            return vehiculoRepository.findByPlaca(placa);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el vehículo");
        }
    }

    public boolean updateVehiculo(String placa, UpdateVehiculoRequest request) {
        try {
            int rows = vehiculoRepository.update(placa, request);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al actualizar el vehículo");
        }
    }
}
