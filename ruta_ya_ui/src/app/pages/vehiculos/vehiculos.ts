import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { VehiculoService }     from '../../services/vehiculo.service';

interface Vehiculo {
  placa:  string;
  modelo: string;
  marca:  string;
  tipo:   string;
}

const MARCAS: Record<number, string> = {
  1: 'Chevrolet',
  2: 'Renault',
  3: 'Honda',
  4: 'Toyota',
  5: 'Mazda',
  6: 'Kia',
  7: 'AKT',
};

const TIPOS: Record<number, string> = {
  1: 'Sedán',
  2: 'Moto',
  3: 'Campero',
  4: 'Camioneta',
};

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
export class Vehiculos implements OnInit {

  vehiculos = signal<Vehiculo[]>([]);

  modalAbierto  = false;
  modoEdicion   = false;
  placaEditando = '';

  form = new FormGroup({
    placa:  new FormControl(''),
    modelo: new FormControl(''),
    marca:  new FormControl('Chevrolet'),
    tipo:   new FormControl('Sedán'),
  });

  constructor(private vehiculoService: VehiculoService) {}

  ngOnInit() {
    this.vehiculoService.getVehiculos().subscribe(data => {
      this.vehiculos.set(data.map(v => ({
        placa:  v.placa,
        modelo: v.modelo,
        marca:  MARCAS[v.idMarca] ?? `Marca ${v.idMarca}`,
        tipo:   TIPOS[v.idTipo]   ?? `Tipo ${v.idTipo}`,
      })));
    });
  }

  abrirModal() {
    this.modoEdicion   = false;
    this.placaEditando = '';
    this.form.reset({ marca: 'Chevrolet', tipo: 'Sedán' });
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
  }

  editar(v: Vehiculo) {
    this.modoEdicion   = true;
    this.placaEditando = v.placa;
    this.form.setValue({ placa: v.placa, modelo: v.modelo, marca: v.marca, tipo: v.tipo });
    this.modalAbierto = true;
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion) {
      this.vehiculos.update(list => list.map(x =>
        x.placa === this.placaEditando
          ? { placa: v.placa!, modelo: v.modelo!, marca: v.marca!, tipo: v.tipo! }
          : x
      ));
    } else {
      this.vehiculos.update(list => [...list, {
        placa:  v.placa!,
        modelo: v.modelo!,
        marca:  v.marca!,
        tipo:   v.tipo!,
      }]);
    }
    this.cerrarModal();
  }

  eliminar(placa: string) {
    this.vehiculos.update(list => list.filter(x => x.placa !== placa));
  }
}
