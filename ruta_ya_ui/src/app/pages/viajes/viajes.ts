import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { forkJoin } from 'rxjs';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import {
  CatalogoItemDescripcion,
  CiudadApi,
  VehiculoConductorApi,
  ViajeService,
} from '../../services/viaje.service';

interface Viaje {
  codigo:          number;
  fechaHora:       string;
  valorEstimado:   number;
  estado:          string;
  cedulaUsuario:   string;
  cedulaConductor: string;
  placaVehiculo:   string;
}

@Component({
  selector: 'app-viajes',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass,
    ButtonComponent,
    IconButtonComponent,
    FormFieldComponent,
  ],
  templateUrl: './viajes.html',
})
export class Viajes implements OnInit {

  viajes       = signal<Viaje[]>([]);
  estados      = signal<CatalogoItemDescripcion[]>([]);
  asignaciones = signal<VehiculoConductorApi[]>([]);
  ciudades     = signal<CiudadApi[]>([]);

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private codigoEditando = 0;

  busqueda = new FormControl('');

  form = new FormGroup({
    fechaHora:     new FormControl(''),
    valorEstimado: new FormControl(''),
    estado:        new FormControl(''),
    cedulaUsuario: new FormControl(''),
    asignacion:    new FormControl<VehiculoConductorApi | null>(null),
    direccionOrigen: new FormGroup({
      direccion:    new FormControl(''),
      descripcion:  new FormControl(''),
      codigoCiudad: new FormControl(''),
    }),
    direccionDestino: new FormGroup({
      direccion:    new FormControl(''),
      descripcion:  new FormControl(''),
      codigoCiudad: new FormControl(''),
    }),
  });

  constructor(private viajeService: ViajeService) {}

  ngOnInit() {
    this.cargarCatalogos();
    this.cargar();
  }

  abrirModal() {
    this.modoEdicion.set(false);
    this.codigoEditando = 0;
    this.form.reset({
      fechaHora:     '',
      valorEstimado: '',
      estado:        this.estados()[0]?.nombre ?? '',
      cedulaUsuario: '',
      asignacion:    this.asignaciones()[0] ?? null,
      direccionOrigen:  { direccion: '', descripcion: '', codigoCiudad: '' },
      direccionDestino: { direccion: '', descripcion: '', codigoCiudad: '' },
    });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(v: Viaje) {
    this.modoEdicion.set(true);
    this.codigoEditando = v.codigo;
    const asignacion = this.asignaciones().find(a =>
      a.cedulaConductor === v.cedulaConductor && a.placaVehiculo === v.placaVehiculo
    ) ?? null;
    this.form.setValue({
      fechaHora:     v.fechaHora,
      valorEstimado: String(v.valorEstimado),
      estado:        v.estado,
      cedulaUsuario: v.cedulaUsuario,
      asignacion,
      direccionOrigen:  { direccion: '', descripcion: '', codigoCiudad: '' },
      direccionDestino: { direccion: '', descripcion: '', codigoCiudad: '' },
    });
    this.modalAbierto.set(true);
  }

  guardar() {
    const v = this.form.value;
    const cedulaConductor = v.asignacion?.cedulaConductor ?? '';
    const placaVehiculo   = v.asignacion?.placaVehiculo   ?? '';
    const idEstado        = this.estados().find(e => e.nombre === v.estado)?.id;

    if (idEstado == null || !cedulaConductor || !placaVehiculo) {
      console.error('Faltan datos para guardar el viaje', v);
      return;
    }

    const payload = {
      fechaHora:     v.fechaHora ?? '',
      valorEstimado: Number(v.valorEstimado),
      idEstado,
      cedulaUsuario: v.cedulaUsuario!,
      cedulaConductor,
      placaVehiculo,
      direccionOrigen: {
        direccion:    v.direccionOrigen?.direccion    ?? '',
        descripcion:  v.direccionOrigen?.descripcion  ?? '',
        codigoCiudad: v.direccionOrigen?.codigoCiudad ?? '',
      },
      direccionDestino: {
        direccion:    v.direccionDestino?.direccion    ?? '',
        descripcion:  v.direccionDestino?.descripcion  ?? '',
        codigoCiudad: v.direccionDestino?.codigoCiudad ?? '',
      },
    };

    const peticion$ = this.modoEdicion()
      ? this.viajeService.actualizarViaje(this.codigoEditando, payload)
      : this.viajeService.crearViaje(payload);

    peticion$.subscribe({
      next: () => {
        this.cerrarModal();
        this.cargar();
      },
      error: err => console.error('Error al guardar viaje', err),
    });
  }

  eliminar(codigo: number) {
    this.viajeService.eliminarViaje(codigo).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al eliminar viaje', err),
    });
  }

  buscar() {
    const codigo = Number(this.busqueda.value);
    if (!codigo || Number.isNaN(codigo)) {
      this.cargar();
      return;
    }
    const estados = this.estados();
    this.viajeService.getViajePorCodigo(codigo).subscribe({
      next: v => this.viajes.set([{
        codigo:          v.codigo,
        fechaHora:       v.fechaHora,
        valorEstimado:   v.valorEstimado,
        estado:          estados.find(e => e.id === v.idEstado)?.nombre ?? `Estado ${v.idEstado}`,
        cedulaUsuario:   v.cedulaUsuario,
        cedulaConductor: v.cedulaConductor,
        placaVehiculo:   v.placaVehiculo,
      }]),
      error: err => {
        console.error('Viaje no encontrado', err);
        this.viajes.set([]);
      },
    });
  }

  limpiarBusqueda() {
    this.busqueda.setValue('');
    this.cargar();
  }

  private cargarCatalogos() {
    forkJoin({
      estados:      this.viajeService.getEstados(),
      asignaciones: this.viajeService.getVehiculoConductor(),
      ciudades:     this.viajeService.getCiudades(),
    }).subscribe({
      next: ({ estados, asignaciones, ciudades }) => {
        this.estados.set(estados);
        this.asignaciones.set(asignaciones);
        this.ciudades.set(ciudades);
        this.cargar();
      },
      error: err => console.error('Error al cargar catálogos', err),
    });
  }

  private cargar() {
    const estados = this.estados();
    this.viajeService.getViajes().subscribe({
      next: data => {
        this.viajes.set(data.map(v => ({
          codigo:          v.codigo,
          fechaHora:       v.fechaHora,
          valorEstimado:   v.valorEstimado,
          estado:          estados.find(e => e.id === v.idEstado)?.nombre ?? `Estado ${v.idEstado}`,
          cedulaUsuario:   v.cedulaUsuario,
          cedulaConductor: v.cedulaConductor,
          placaVehiculo:   v.placaVehiculo,
        })));
      },
      error: err => console.error('Error al cargar viajes', err),
    });
  }
}
