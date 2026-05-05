package com.ruta_ya.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class VehiculoConductorId implements Serializable {
    private static final long serialVersionUID = 4791622413080482747L;
    @Column(name = "cedula_conductor", nullable = false, length = 20)
    private String cedulaConductor;

    @Column(name = "placa_vehiculo", nullable = false, length = 20)
    private String placaVehiculo;


}