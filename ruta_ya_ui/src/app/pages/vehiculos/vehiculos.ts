import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { VehiculoApi, VehiculoService } from '../../services/vehiculo.service';

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

const ID_MARCA: Record<string, number> = {
  'Chevrolet': 1, 'Renault': 2, 'Honda': 3, 'Toyota': 4, 'Mazda': 5, 'Kia': 6, 'AKT': 7,
};

const ID_TIPO: Record<string, number> = {
  'Sedán': 1, 'Moto': 2, 'Campero': 3, 'Camioneta': 4,
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

  vehiculos    = signal<Vehiculo[]>([]);
  modalAbierto = signal(false);
  modoEdicion  = signal(false);
  guardando    = signal(false);

  private placaEditando = '';

  form = new FormGroup({
    placa:  new FormControl(''),
    modelo: new FormControl(''),
    marca:  new FormControl('Chevrolet'),
    tipo:   new FormControl('Sedán'),
  });

  constructor(private vehiculoService: VehiculoService) {}

  ngOnInit() {
    this.cargar();
  }

  abrirModal() {
    this.modoEdicion.set(false);
    this.placaEditando = '';
    this.form.reset({ marca: 'Chevrolet', tipo: 'Sedán' });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(v: Vehiculo) {
    this.modoEdicion.set(true);
    this.placaEditando = v.placa;
    this.form.setValue({ placa: v.placa, modelo: v.modelo, marca: v.marca, tipo: v.tipo });
    this.modalAbierto.set(true);
  }

  guardar():void {
    if (this.guardando()) return;

    const formulario = this.form.value;
    const payload: VehiculoApi = {
      placa:   this.modoEdicion() ? this.placaEditando : formulario.placa!,
      modelo:  formulario.modelo!,
      idMarca: ID_MARCA[formulario.marca!],
      idTipo:  ID_TIPO[formulario.tipo!],
    };

    this.guardando.set(true);
    this.vehiculoService.crearVehiculo(payload).subscribe({
      next: () => {
        this.guardando.set(false);
        this.cerrarModal();
        this.cargar();
      },
      error: (err) => {
        this.guardando.set(false);
        console.error('Error al guardar vehículo', err);
      },
    });
  }

  eliminar(placa: string) {
    this.vehiculos.update(list => list.filter(x => x.placa !== placa));
  }

  private cargar() {
    this.vehiculoService.getVehiculos().subscribe({
      next: data => {
        this.vehiculos.set(data.map(v => ({
          placa:  v.placa,
          modelo: v.modelo,
          marca:  MARCAS[v.idMarca] ?? `Marca ${v.idMarca}`,
          tipo:   TIPOS[v.idTipo]   ?? `Tipo ${v.idTipo}`,
        })));
      },
      error: err => console.error('Error al cargar vehículos', err),
    });
  }
}
