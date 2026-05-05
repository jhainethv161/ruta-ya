package com.ruta_ya.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "conductor")
public class Conductor {
    @Id
    @Column(name = "cedula", nullable = false, length = 20)
    private String cedula;

    @Column(name = "ubicacion_actual")
    private String ubicacionActual;


}