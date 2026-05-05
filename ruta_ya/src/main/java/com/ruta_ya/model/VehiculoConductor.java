package com.ruta_ya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "vehiculo_conductor")
public class VehiculoConductor {
    @EmbeddedId
    private VehiculoConductorId id;

    @MapsId("cedulaConductor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cedula_conductor", nullable = false)
    private Conductor cedulaConductor;

    @MapsId("placaVehiculo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "placa_vehiculo", nullable = false)
    private Vehiculo placaVehiculo;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;


}