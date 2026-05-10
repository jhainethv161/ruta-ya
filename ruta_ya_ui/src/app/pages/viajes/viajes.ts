import { Component, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';

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
export class Viajes {

  viajes = signal<Viaje[]>([
    { codigo: 1, fechaHora: '2025-04-10 08:15', valorEstimado: 12500, estado: 'Completado', cedulaUsuario: '1001234567', cedulaConductor: '2001111111', placaVehiculo: 'ABC123' },
    { codigo: 2, fechaHora: '2025-04-11 17:30', valorEstimado: 18000, estado: 'En curso',   cedulaUsuario: '1002345678', cedulaConductor: '2002222222', placaVehiculo: 'DEF456' },
    { codigo: 3, fechaHora: '2025-04-12 09:00', valorEstimado: 9500,  estado: 'En curso',   cedulaUsuario: '1003456789', cedulaConductor: '2003333333', placaVehiculo: 'GHI789' },
    { codigo: 4, fechaHora: '2025-04-13 14:45', valorEstimado: 22000, estado: 'En curso',   cedulaUsuario: '1004567890', cedulaConductor: '2004444444', placaVehiculo: 'JKL012' },
    { codigo: 5, fechaHora: '2025-04-14 07:20', valorEstimado: 35000, estado: 'En curso',   cedulaUsuario: '1006789012', cedulaConductor: '2005555555', placaVehiculo: 'MNO345' },
    { codigo: 6, fechaHora: '2025-04-15 11:00', valorEstimado: 14000, estado: 'Completado', cedulaUsuario: '1001234567', cedulaConductor: '2001111111', placaVehiculo: 'ABC123' },
    { codigo: 7, fechaHora: '2025-04-16 19:00', valorEstimado: 27000, estado: 'En curso',   cedulaUsuario: '1008901234', cedulaConductor: '2002222222', placaVehiculo: 'DEF456' },
    { codigo: 8, fechaHora: '2025-04-17 06:30', valorEstimado: 11000, estado: 'Cancelado',  cedulaUsuario: '1002345678', cedulaConductor: '2001111111', placaVehiculo: 'ABC123' },
    { codigo: 9, fechaHora: '2026-05-06 18:17', valorEstimado: 15000, estado: 'Pendiente',  cedulaUsuario: '1001234567', cedulaConductor: '2001111111', placaVehiculo: 'ABC123' },
  ]);

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private codigoEditando = 0;
  private nextCodigo     = 10;

  form = new FormGroup({
    fechaHora:       new FormControl(''),
    valorEstimado:   new FormControl(''),
    estado:          new FormControl('Pendiente'),
    cedulaUsuario:   new FormControl(''),
    cedulaConductor: new FormControl(''),
    placaVehiculo:   new FormControl(''),
  });

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
}
