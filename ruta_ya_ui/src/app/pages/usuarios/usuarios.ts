import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { forkJoin } from 'rxjs';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { CatalogoItemDescripcion, CrearUsuarioApi, UsuarioService } from '../../services/usuario.service';

interface UsuarioItem {
  id: number;
  cedula: string;
  primerNombre: string;
  segundoNombre: string;
  primerApellido: string;
  segundoApellido: string;
  correo: string;
  fechaNacimiento: string;
  idEstado: number;
  idMetodoPagoPref: number;
}

@Component({
  selector: 'app-usuario',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass,
    ButtonComponent,
    IconButtonComponent,
    FormFieldComponent,
  ],
  templateUrl: './usuarios.html',
})
export class Usuario implements OnInit {

  estados     = signal<CatalogoItemDescripcion[]>([]);
  metodosPago = signal<CatalogoItemDescripcion[]>([]);

  usuarios = signal<UsuarioItem[]>([
    {
      id: 1,
      cedula: '1234567890',
      primerNombre: 'Juan',
      segundoNombre: 'Carlos',
      primerApellido: 'Pérez',
      segundoApellido: 'García',
      correo: 'juan.perez@email.com',
      fechaNacimiento: '1990-05-15',
      idEstado: 1,
      idMetodoPagoPref: 1,
    },
    {
      id: 2,
      cedula: '0987654321',
      primerNombre: 'Laura',
      segundoNombre: 'María',
      primerApellido: 'Torres',
      segundoApellido: 'López',
      correo: 'laura.torres@email.com',
      fechaNacimiento: '1995-08-22',
      idEstado: 1,
      idMetodoPagoPref: 2,
    },
    {
      id: 3,
      cedula: '1122334455',
      primerNombre: 'Carlos',
      segundoNombre: '',
      primerApellido: 'Ríos',
      segundoApellido: 'Mora',
      correo: 'carlos.rios@email.com',
      fechaNacimiento: '1988-11-30',
      idEstado: 2,
      idMetodoPagoPref: 3,
    },
  ]);

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private idEditando: number | null = null;
  private nextId = 4;

  form = new FormGroup({
    cedula:           new FormControl(''),
    primerNombre:     new FormControl(''),
    segundoNombre:    new FormControl(''),
    primerApellido:   new FormControl(''),
    segundoApellido:  new FormControl(''),
    correo:           new FormControl(''),
    contrasena:       new FormControl(''),
    fechaNacimiento:  new FormControl(''),
    idEstado:         new FormControl(1),
    idMetodoPagoPref: new FormControl(1),
  });

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.cargarCatalogos();
  }

  getNombreEstado(id: number): string {
    return this.estados().find(e => e.id === id)?.nombre ?? '';
  }

  getNombreMetodoPago(id: number): string {
    return this.metodosPago().find(m => m.id === id)?.nombre ?? '';
  }

  private cargarCatalogos() {
    forkJoin({
      estados:     this.usuarioService.getEstados(),
      metodosPago: this.usuarioService.getMetodosPago(),
    }).subscribe({
      next: ({ estados, metodosPago }) => {
        this.estados.set(estados);
        this.metodosPago.set(metodosPago);
      },
      error: err => console.error('Error al cargar catálogos', err),
    });
  }

  getNombreCompleto(u: UsuarioItem): string {
    return [u.primerNombre, u.segundoNombre, u.primerApellido, u.segundoApellido]
      .filter(Boolean).join(' ');
  }

  abrirModal() {
    this.modoEdicion.set(false);
    this.idEditando  = null;
    this.form.reset({ idEstado: 1, idMetodoPagoPref: 1 });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(u: UsuarioItem) {
    this.modoEdicion.set(true);
    this.idEditando  = u.id;
    this.form.setValue({
      cedula:           u.cedula,
      primerNombre:     u.primerNombre,
      segundoNombre:    u.segundoNombre,
      primerApellido:   u.primerApellido,
      segundoApellido:  u.segundoApellido,
      correo:           u.correo,
      contrasena:       '',
      fechaNacimiento:  u.fechaNacimiento,
      idEstado:         u.idEstado,
      idMetodoPagoPref: u.idMetodoPagoPref,
    });
    this.modalAbierto.set(true);
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion()) {
      this.usuarios.update(list => list.map(u =>
        u.id === this.idEditando
          ? {
              ...u,
              cedula:           v.cedula!,
              primerNombre:     v.primerNombre!,
              segundoNombre:    v.segundoNombre ?? '',
              primerApellido:   v.primerApellido!,
              segundoApellido:  v.segundoApellido ?? '',
              correo:           v.correo!,
              fechaNacimiento:  v.fechaNacimiento!,
              idEstado:         Number(v.idEstado),
              idMetodoPagoPref: Number(v.idMetodoPagoPref),
            }
          : u
      ));
      this.cerrarModal();
      return;
    }

    const payload: CrearUsuarioApi = {
      cedula:           v.cedula!,
      primerNombre:     v.primerNombre!,
      segundoNombre:    v.segundoNombre ?? '',
      primerApellido:   v.primerApellido!,
      segundoApellido:  v.segundoApellido ?? '',
      correo:           v.correo!,
      contrasena:       v.contrasena!,
      fechaNacimiento:  v.fechaNacimiento!,
      idEstado:         Number(v.idEstado),
      idMetodoPagoPref: Number(v.idMetodoPagoPref),
    };

    this.usuarioService.crearUsuario(payload).subscribe({
      next: () => {
        this.usuarios.update(list => [...list, {
          id:               this.nextId++,
          cedula:           payload.cedula,
          primerNombre:     payload.primerNombre,
          segundoNombre:    payload.segundoNombre,
          primerApellido:   payload.primerApellido,
          segundoApellido:  payload.segundoApellido,
          correo:           payload.correo,
          fechaNacimiento:  payload.fechaNacimiento,
          idEstado:         payload.idEstado,
          idMetodoPagoPref: payload.idMetodoPagoPref,
        }]);
        this.cerrarModal();
      },
      error: err => console.error('Error al crear usuario', err),
    });
  }

  eliminar(id: number) {
    this.usuarios.update(list => list.filter(u => u.id !== id));
  }
}
