package com.ruta_ya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "viaje")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Integer id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "valor_estimado", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorEstimado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoViaje idEstado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_direccion_origen", nullable = false)
    private Direccion idDireccionOrigen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_direccion_destino", nullable = false)
    private Direccion idDireccionDestino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cedula_usuario", nullable = false)
    private Usuario cedulaUsuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cedula_conductor", nullable = false)
    private Conductor cedulaConductor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "placa_vehiculo", nullable = false)
    private Vehiculo placaVehiculo;


}