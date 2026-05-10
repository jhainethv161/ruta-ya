import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { CatalogoItem, VehiculoService } from '../../services/vehiculo.service';

interface Vehiculo {
  placa:   string;
  modelo:  string;
  marca:   string;
  tipo:    string;
  idMarca: number;
  idTipo:  number;
}

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
  marcas       = signal<CatalogoItem[]>([]);
  tipos        = signal<CatalogoItem[]>([]);
  modalAbierto = signal(false);
  modoEdicion  = signal(false);
  guardando    = signal(false);

  private placaEditando = '';

  form = new FormGroup({
    placa:   new FormControl(''),
    modelo:  new FormControl(''),
    idMarca: new FormControl<number | null>(null),
    idTipo:  new FormControl<number | null>(null),
  });

  constructor(private vehiculoService: VehiculoService) {}

  ngOnInit() {
    this.cargarCatalogos();
  }

  abrirModal() {
    this.modoEdicion.set(false);
    this.placaEditando = '';
    this.form.reset({
      placa:   '',
      modelo:  '',
      idMarca: this.marcas()[0]?.id ?? null,
      idTipo:  this.tipos()[0]?.id  ?? null,
    });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(v: Vehiculo) {
    this.modoEdicion.set(true);
    this.placaEditando = v.placa;
    this.form.setValue({
      placa:   v.placa,
      modelo:  v.modelo,
      idMarca: v.idMarca,
      idTipo:  v.idTipo,
    });
    this.modalAbierto.set(true);
  }

  guardar(): void {
    if (this.guardando()) return;

    const formulario = this.form.value;
    const enEdicion  = this.modoEdicion();

    const peticion$ = enEdicion
      ? this.vehiculoService.actualizarVehiculo(this.placaEditando, {
          modelo:  formulario.modelo!,
          idMarca: formulario.idMarca!,
          idTipo:  formulario.idTipo!,
        })
      : this.vehiculoService.crearVehiculo({
          placa:   formulario.placa!,
          modelo:  formulario.modelo!,
          idMarca: formulario.idMarca!,
          idTipo:  formulario.idTipo!,
        });

    this.guardando.set(true);
    peticion$.subscribe({
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

  private cargar() {
    const marcas = this.marcas();
    const tipos  = this.tipos();
    this.vehiculoService.getVehiculos().subscribe({
      next: data => {
        this.vehiculos.set(data.map(v => ({
          placa:   v.placa,
          modelo:  v.modelo,
          marca:   marcas.find(m => m.id === v.idMarca)?.nombre ?? `Marca ${v.idMarca}`,
          tipo:    tipos.find(t => t.id === v.idTipo)?.nombre   ?? `Tipo ${v.idTipo}`,
          idMarca: v.idMarca,
          idTipo:  v.idTipo,
        })));
      },
      error: err => console.error('Error al cargar vehículos', err),
    });
  }

  private cargarCatalogos() {
    forkJoin({
      marcas: this.vehiculoService.getMarcas(),
      tipos:  this.vehiculoService.getTipos(),
    }).subscribe({
      next: ({ marcas, tipos }) => {
        this.marcas.set(marcas);
        this.tipos.set(tipos);
        this.cargar();
      },
      error: err => console.error('Error al cargar catálogos', err),
    });
  }
}
