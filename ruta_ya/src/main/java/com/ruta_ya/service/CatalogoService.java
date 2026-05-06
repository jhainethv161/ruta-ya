package com.ruta_ya.service;

import com.ruta_ya.dto.EstadoUsuarioResponse;
import com.ruta_ya.dto.MarcaResponse;
import com.ruta_ya.dto.MetodoPagoResponse;
import com.ruta_ya.dto.CiudadResponse;
import com.ruta_ya.dto.EstadoSuscripcionResponse;
import com.ruta_ya.dto.EstadoViajeResponse;
import com.ruta_ya.dto.TipoSuscripcionResponse;
import com.ruta_ya.dto.TipoVehiculoResponse;
import com.ruta_ya.repository.CiudadRepository;
import com.ruta_ya.repository.EstadoSuscripcionRepository;
import com.ruta_ya.repository.EstadoViajeRepository;
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
    private final EstadoSuscripcionRepository estadoSuscripcionRepository;
    private final CiudadRepository ciudadRepository;
    private final EstadoViajeRepository estadoViajeRepository;

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

    public List<EstadoSuscripcionResponse> getAllEstadosSuscripcion() {
        try {
            return estadoSuscripcionRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los estados de suscripción");
        }
    }

    public List<CiudadResponse> getAllCiudades() {
        try {
            return ciudadRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener las ciudades");
        }
    }

    public List<EstadoViajeResponse> getAllEstadosViaje() {
        try {
            return estadoViajeRepository.getAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los estados de viaje");
        }
    }
}
