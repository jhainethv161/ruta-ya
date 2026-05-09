import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';

@Component({
  selector: 'app-vehiculos',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ButtonComponent,
    IconButtonComponent,
    FormFieldComponent,
  ],
  templateUrl: './vehiculos.html',
})
export class Vehiculos {

  vehiculos = [
    { placa: 'ABC123', modelo: 'Spark 2022',    marca: 'Chevrolet', tipo: 'Sedán'   },
    { placa: 'DEF456', modelo: 'Logan 2021',    marca: 'Renault',   tipo: 'Sedán'   },
    { placa: 'GHI789', modelo: 'Mazda 3 2023',  marca: 'Mazda',     tipo: 'Sedán'   },
    { placa: 'JKL012', modelo: 'Corolla 2020',  marca: 'Toyota',    tipo: 'Sedán'   },
    { placa: 'MNO345', modelo: 'Sportage 2022', marca: 'Kia',       tipo: 'Campero' },
    { placa: 'XYZ999', modelo: 'Spark GT 2024', marca: 'Chevrolet', tipo: 'Sedán'   },
  ];

  modalAbierto  = false;
  modoEdicion   = false;
  placaEditando = '';

  form = new FormGroup({
    placa:  new FormControl(''),
    modelo: new FormControl(''),
    marca:  new FormControl('Chevrolet'),
    tipo:   new FormControl('Sedán'),
  });

  abrirModal() {
    this.modoEdicion   = false;
    this.placaEditando = '';
    this.form.reset({ marca: 'Chevrolet', tipo: 'Sedán' });
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
  }

  editar(v: any) {
    this.modoEdicion   = true;
    this.placaEditando = v.placa;
    this.form.setValue({ placa: v.placa, modelo: v.modelo, marca: v.marca, tipo: v.tipo });
    this.modalAbierto = true;
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion) {
      this.vehiculos = this.vehiculos.map(x =>
        x.placa === this.placaEditando
          ? { placa: v.placa!, modelo: v.modelo!, marca: v.marca!, tipo: v.tipo! }
          : x
      );
    } else {
      this.vehiculos = [...this.vehiculos, {
        placa:  v.placa!,
        modelo: v.modelo!,
        marca:  v.marca!,
        tipo:   v.tipo!,
      }];
    }
    this.cerrarModal();
  }

  eliminar(placa: string) {
    this.vehiculos = this.vehiculos.filter(x => x.placa !== placa);
  }
}
