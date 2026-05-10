import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { ViajeService } from '../../services/viaje.service';

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

  viajes = signal<Viaje[]>([]);

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private codigoEditando = 0;
  private nextCodigo     = 1;

  form = new FormGroup({
    fechaHora:       new FormControl(''),
    valorEstimado:   new FormControl(''),
    estado:          new FormControl('Pendiente'),
    cedulaUsuario:   new FormControl(''),
    cedulaConductor: new FormControl(''),
    placaVehiculo:   new FormControl(''),
  });

  constructor(private viajeService: ViajeService) {}

  ngOnInit() {
    this.cargar();
  }

  abrirModal() {
    this.modoEdicion.set(false);
    this.codigoEditando = 0;
    this.form.reset({ estado: 'Pendiente' });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(v: Viaje) {
    this.modoEdicion.set(true);
    this.codigoEditando = v.codigo;
    this.form.setValue({
      fechaHora:       v.fechaHora,
      valorEstimado:   String(v.valorEstimado),
      estado:          v.estado,
      cedulaUsuario:   v.cedulaUsuario,
      cedulaConductor: v.cedulaConductor,
      placaVehiculo:   v.placaVehiculo,
    });
    this.modalAbierto.set(true);
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion()) {
      this.viajes.update(list => list.map(x =>
        x.codigo === this.codigoEditando
          ? {
              ...x,
              fechaHora:       v.fechaHora!,
              valorEstimado:   Number(v.valorEstimado),
              estado:          v.estado!,
              cedulaUsuario:   v.cedulaUsuario!,
              cedulaConductor: v.cedulaConductor!,
              placaVehiculo:   v.placaVehiculo!,
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
        cedulaConductor: v.cedulaConductor!,
        placaVehiculo:   v.placaVehiculo!,
      }]);
    }
    this.cerrarModal();
  }

  eliminar(codigo: number) {
    this.viajes.update(list => list.filter(x => x.codigo !== codigo));
  }

  private cargar() {
    this.viajeService.getViajes().subscribe({
      next: data => {
        this.viajes.set(data.map(v => ({
          codigo:          v.codigo,
          fechaHora:       v.fechaHora,
          valorEstimado:   v.valorEstimado,
          estado:          `Estado ${v.idEstado}`,
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
