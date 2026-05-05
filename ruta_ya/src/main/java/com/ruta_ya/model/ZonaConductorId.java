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
public class ZonaConductorId implements Serializable {
    private static final long serialVersionUID = 1101048020791044143L;
    @Column(name = "cedula_conductor", nullable = false, length = 20)
    private String cedulaConductor;

    @Column(name = "id_zona", nullable = false)
    private Integer idZona;


}