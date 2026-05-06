package com.ruta_ya.service;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.dto.MarcaResponse;
import com.ruta_ya.dto.MetodoPagoResponse;
import com.ruta_ya.dto.TipoSuscripcionResponse;
import com.ruta_ya.dto.TipoVehiculoResponse;
import com.ruta_ya.repository.EstadoUsuarioRepository;
import com.ruta_ya.repository.MarcaRepository;
import com.ruta_ya.repository.MetodoPagoRepository;
import com.ruta_ya.repository.TipoSuscripcionRepository;
import com.ruta_ya.repository.TipoVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CatalogoService {

    private final EstadoUsuarioRepository estadoUsuarioRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final MarcaRepository marcaRepository;
    private final TipoVehiculoRepository tipoVehiculoRepository;
    private final TipoSuscripcionRepository tipoSuscripcionRepository;

    public List<EstadoUsuarioResponse> getAllEstadosUsuario() {
        try {
            return estadoUsuarioRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los estados de usuario");
        }
    }

    public List<MetodoPagoResponse> getAllMetodosPago() {
        try {
            return metodoPagoRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los métodos de pago");
        }
    }

    public List<MarcaResponse> getAllMarcas() {
        try {
            return marcaRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las marcas");
        }
    }

    public List<TipoVehiculoResponse> getAllTiposVehiculo() {
        try {
            return tipoVehiculoRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los tipos de vehículo");
        }
    }

    public List<TipoSuscripcionResponse> getAllTiposSuscripcion() {
        try {
            return tipoSuscripcionRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los tipos de suscripción");
        }
    }
}
