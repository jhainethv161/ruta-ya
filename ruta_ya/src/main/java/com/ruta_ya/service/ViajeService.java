package com.ruta_ya.service;

import com.ruta_ya.dto.request.CreateViajeRequest;
import com.ruta_ya.dto.request.UpdateViajeRequest;
import com.ruta_ya.dto.response.ViajeResponse;
import com.ruta_ya.repository.DireccionRepository;
import com.ruta_ya.repository.ViajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ViajeService {

    private final ViajeRepository viajeRepository;
    private final DireccionRepository direccionRepository;

    public List<ViajeResponse> getAllViajes() {
        try {
            return viajeRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los viajes");
        }
    }

    public ViajeResponse getViajeByCodigo(Integer codigo) {
        try {
            return viajeRepository.findByCodigo(codigo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener el viaje");
        }
    }

    @Transactional
    public boolean createViaje(CreateViajeRequest request) {
        try {
            direccionRepository.insert(
                    request.getDireccionOrigen().getDireccion(),
                    request.getDireccionOrigen().getDescripcion(),
                    request.getDireccionOrigen().getCodigoCiudad()
            );
            Integer idOrigen = direccionRepository.lastInsertId();

            direccionRepository.insert(
                    request.getDireccionDestino().getDireccion(),
                    request.getDireccionDestino().getDescripcion(),
                    request.getDireccionDestino().getCodigoCiudad()
            );
            Integer idDestino = direccionRepository.lastInsertId();

            int rows = viajeRepository.create(
                    Instant.now(),
                    request.getValorEstimado(),
                    request.getIdEstado(),
                    idOrigen,
                    idDestino,
                    request.getCedulaUsuario(),
                    request.getCedulaConductor(),
                    request.getPlacaVehiculo()
            );
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al crear el viaje");
        }
    }

    @Transactional
    public boolean updateViaje(Integer codigo, UpdateViajeRequest request) {
        try {
            ViajeResponse viaje = viajeRepository.findByCodigo(codigo);
            if (viaje == null) return false;

            direccionRepository.update(
                    viaje.getIdDireccionOrigen(),
                    request.getDireccionOrigen().getDireccion(),
                    request.getDireccionOrigen().getDescripcion(),
                    request.getDireccionOrigen().getCodigoCiudad()
            );

            direccionRepository.update(
                    viaje.getIdDireccionDestino(),
                    request.getDireccionDestino().getDireccion(),
                    request.getDireccionDestino().getDescripcion(),
                    request.getDireccionDestino().getCodigoCiudad()
            );

            int rows = viajeRepository.update(
                    codigo,
                    request.getValorEstimado(),
                    request.getIdEstado(),
                    request.getCedulaUsuario(),
                    request.getCedulaConductor(),
                    request.getPlacaVehiculo()
            );
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al actualizar el viaje");
        }
    }

    public boolean cancelarViaje(Integer codigo) {
        try {
            int rows = viajeRepository.cancelar(codigo);
            return rows > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al cancelar el viaje");
        }
    }
}
