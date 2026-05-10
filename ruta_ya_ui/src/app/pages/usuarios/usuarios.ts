import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';
import { forkJoin } from 'rxjs';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';
import { ActualizarUsuarioApi, CatalogoItemDescripcion, CrearUsuarioApi, UsuarioService } from '../../services/usuario.service';

interface UsuarioItem {
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

  usuarios = signal<UsuarioItem[]>([]);

  modalAbierto = signal(false);
  modoEdicion  = signal(false);

  private idEditando: string | null = null;
  private nextId = 4;

  busqueda = new FormControl('');

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
    this.cargar();
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
        this.cargar();
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
    this.form.controls['cedula'].enable();
    this.form.reset({ idEstado: 1, idMetodoPagoPref: 1 });
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  editar(u: UsuarioItem) {
    this.modoEdicion.set(true);
    this.idEditando  = u.cedula;
    this.form.controls['cedula'].disable();
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

    if (this.modoEdicion() && this.idEditando) {
      const payload: ActualizarUsuarioApi = {
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

      this.usuarioService.actualizarUsuario(this.idEditando, payload).subscribe({
        next: () => {
          this.cerrarModal();
          this.cargar();
        },
        error: err => console.error('Error al actualizar usuario', err),
      });
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
        this.cerrarModal();
        this.cargar();
      },
      error: err => console.error('Error al crear usuario', err),
    });
  }

  private cargar() {
    this.usuarioService.getUsuarios().subscribe({
      next: data => {
        this.usuarios.set(data.map(u => ({
          cedula:           u.cedula,
          primerNombre:     u.primerNombre,
          segundoNombre:    u.segundoNombre,
          primerApellido:   u.primerApellido,
          segundoApellido:  u.segundoApellido,
          correo:           u.correo,
          fechaNacimiento:  u.fechaNacimiento,
          idEstado:         u.idEstado,
          idMetodoPagoPref: u.idMetodoPagoPref,
        })));
      },
      error: err => console.error('Error al cargar usuarios', err),
    });
  }

  eliminar(cedula: string) {
    this.usuarioService.eliminarUsuario(cedula).subscribe({
      next: () => this.cargar(),
      error: err => console.error('Error al eliminar usuario', err),
    });
  }

  buscar() {
    const cedula = this.busqueda.value?.trim();
    if (!cedula) {
      this.cargar();
      return;
    }
    this.usuarioService.getUsuarioPorCedula(cedula).subscribe({
      next: u => this.usuarios.set([{
        cedula:           u.cedula,
        primerNombre:     u.primerNombre,
        segundoNombre:    u.segundoNombre,
        primerApellido:   u.primerApellido,
        segundoApellido:  u.segundoApellido,
        correo:           u.correo,
        fechaNacimiento:  u.fechaNacimiento,
        idEstado:         u.idEstado,
        idMetodoPagoPref: u.idMetodoPagoPref,
      }]),
      error: err => {
        console.error('Usuario no encontrado', err);
        this.usuarios.set([]);
      },
    });
  }

  limpiarBusqueda() {
    this.busqueda.setValue('');
    this.cargar();
  }
}
