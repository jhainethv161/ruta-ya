package com.ruta_ya.service;

import com.ruta_ya.dto.response.catalogo.EstadoUsuarioResponse;
import com.ruta_ya.dto.response.catalogo.MarcaResponse;
import com.ruta_ya.dto.response.catalogo.MetodoPagoResponse;
import com.ruta_ya.dto.response.catalogo.CiudadResponse;
import com.ruta_ya.dto.response.catalogo.EstadoSuscripcionResponse;
import com.ruta_ya.dto.response.catalogo.EstadoViajeResponse;
import com.ruta_ya.dto.response.catalogo.TipoSuscripcionResponse;
import com.ruta_ya.dto.response.catalogo.TipoVehiculoResponse;
import com.ruta_ya.repository.catalogo.CiudadRepository;
import com.ruta_ya.repository.catalogo.EstadoSuscripcionRepository;
import com.ruta_ya.repository.catalogo.EstadoViajeRepository;
import com.ruta_ya.repository.catalogo.EstadoUsuarioRepository;
import com.ruta_ya.repository.catalogo.MarcaRepository;
import com.ruta_ya.repository.catalogo.MetodoPagoRepository;
import com.ruta_ya.repository.catalogo.TipoSuscripcionRepository;
import com.ruta_ya.repository.catalogo.TipoVehiculoRepository;
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
