package com.ruta_ya.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "suscripcion")
public class Suscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoSuscripcion idTipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoSuscripcion idEstado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cedula_usuario", nullable = false)
    private Usuario cedulaUsuario;


}