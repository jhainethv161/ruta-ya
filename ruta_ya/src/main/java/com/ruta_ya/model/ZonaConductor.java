package com.ruta_ya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "zona_conductor")
public class ZonaConductor {
    @EmbeddedId
    private ZonaConductorId id;

    @MapsId("cedulaConductor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cedula_conductor", nullable = false)
    private Conductor cedulaConductor;

    @MapsId("idZona")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_zona", nullable = false)
    private ZonaServicio idZona;


}