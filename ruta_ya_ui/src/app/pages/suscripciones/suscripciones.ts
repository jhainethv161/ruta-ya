import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { forkJoin } from 'rxjs';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { CatalogoItemDescripcion, SuscripcionService } from '../../services/suscripcion.service';

interface Suscripcion {
  codigo:        number;
  tipo:          string;
  estado:        string;
  cedulaUsuario: string;
}

@Component({
  selector: 'app-suscripciones',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass,
    ButtonComponent,
    IconButtonComponent,
    FormFieldComponent,
  ],
  templateUrl: './suscripciones.html',
})
export class Suscripciones implements OnInit {

  suscripciones = signal<Suscripcion[]>([]);
  tipos         = signal<CatalogoItemDescripcion[]>([]);
  estados       = signal<CatalogoItemDescripcion[]>([]);

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private codigoEditando = 0;
  private nextCodigo     = 1;

  form = new FormGroup({
    cedulaUsuario: new FormControl(''),
    tipo:          new FormControl('Básico'),
    estado:        new FormControl('Activo'),
  });

  constructor(private suscripcionService: SuscripcionService) {}

  ngOnInit() {
    this.cargarCatalogos();
    this.cargar();
  }

  abrirModal() {
    this.modoEdicion.set(false);
    this.codigoEditando = 0;
    this.form.reset({ tipo: 'Básico', estado: 'Activo' });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(s: Suscripcion) {
    this.modoEdicion.set(true);
    this.codigoEditando = s.codigo;
    this.form.setValue({
      cedulaUsuario: s.cedulaUsuario,
      tipo:          s.tipo,
      estado:        s.estado,
    });
    this.modalAbierto.set(true);
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion()) {
      this.suscripciones.update(list => list.map(s =>
        s.codigo === this.codigoEditando
          ? { ...s, cedulaUsuario: v.cedulaUsuario!, tipo: v.tipo!, estado: v.estado! }
          : s
      ));
    } else {
      this.suscripciones.update(list => [...list, {
        codigo:        this.nextCodigo++,
        cedulaUsuario: v.cedulaUsuario!,
        tipo:          v.tipo!,
        estado:        v.estado!,
      }]);
    }
    this.cerrarModal();
  }

  eliminar(codigo: number) {
    this.suscripciones.update(list => list.filter(s => s.codigo !== codigo));
  }

  private cargarCatalogos() {
    forkJoin({
      tipos:   this.suscripcionService.getTipos(),
      estados: this.suscripcionService.getEstados(),
    }).subscribe({
      next: ({ tipos, estados }) => {
        this.tipos.set(tipos);
        this.estados.set(estados);
        this.cargar();
      },
      error: err => console.error('Error al cargar catálogos', err),
    });
  }

  private cargar() {
    const tipos   = this.tipos();
    const estados = this.estados();
    this.suscripcionService.getSuscripciones().subscribe({
      next: data => {
        this.suscripciones.set(data.map(s => ({
          codigo:        s.codigo,
          tipo:          tipos.find(t => t.id === s.idTipo)?.nombre     ?? `Tipo ${s.idTipo}`,
          estado:        estados.find(e => e.id === s.idEstado)?.nombre ?? `Estado ${s.idEstado}`,
          cedulaUsuario: s.cedulaUsuario,
        })));
        this.nextCodigo = data.reduce((max, s) => Math.max(max, s.codigo), 0) + 1;
      },
      error: err => console.error('Error al cargar suscripciones', err),
    });
  }
}
