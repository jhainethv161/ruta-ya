import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { forkJoin } from 'rxjs';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import {
  CatalogoItemDescripcion,
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

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private codigoEditando = 0;
  private nextCodigo     = 1;

  form = new FormGroup({
    fechaHora:     new FormControl(''),
    valorEstimado: new FormControl(''),
    estado:        new FormControl(''),
    cedulaUsuario: new FormControl(''),
    asignacion:    new FormControl<VehiculoConductorApi | null>(null),
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
    });
    this.modalAbierto.set(true);
  }

  guardar() {
    const v = this.form.value;
    const cedulaConductor = v.asignacion?.cedulaConductor ?? '';
    const placaVehiculo   = v.asignacion?.placaVehiculo   ?? '';

    if (this.modoEdicion()) {
      this.viajes.update(list => list.map(x =>
        x.codigo === this.codigoEditando
          ? {
              ...x,
              fechaHora:       v.fechaHora!,
              valorEstimado:   Number(v.valorEstimado),
              estado:          v.estado!,
              cedulaUsuario:   v.cedulaUsuario!,
              cedulaConductor,
              placaVehiculo,
            }
          : x
      ));
    } else {
      this.viajes.update(list => [...list, {
        codigo:          this.nextCodigo++,
        fechaHora:       v.fechaHora!,
        valorEstimado:   Number(v.valorEstimado),
        estado:          v.estado!,
        cedulaUsuario:   v.cedulaUsuario!,
        cedulaConductor,
        placaVehiculo,
      }]);
    }
    this.cerrarModal();
  }

  eliminar(codigo: number) {
    this.viajes.update(list => list.filter(x => x.codigo !== codigo));
  }

  private cargarCatalogos() {
    forkJoin({
      estados:      this.viajeService.getEstados(),
      asignaciones: this.viajeService.getVehiculoConductor(),
    }).subscribe({
      next: ({ estados, asignaciones }) => {
        this.estados.set(estados);
        this.asignaciones.set(asignaciones);
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
        this.nextCodigo = data.reduce((max, v) => Math.max(max, v.codigo), 0) + 1;
      },
      error: err => console.error('Error al cargar viajes', err),
    });
  }
}
