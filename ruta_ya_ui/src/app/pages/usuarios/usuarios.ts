import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';

interface Estado {
  id: number;
  nombre: string;
}

interface MetodoPago {
  id: number;
  nombre: string;
}

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
export class Usuario {

  estados: Estado[] = [
    { id: 1, nombre: 'Activo' },
    { id: 2, nombre: 'Inactivo' },
    { id: 3, nombre: 'Suspendido' },
  ];

  metodosPago: MetodoPago[] = [
    { id: 1, nombre: 'Efectivo' },
    { id: 2, nombre: 'Tarjeta de crédito' },
    { id: 3, nombre: 'Tarjeta débito' },
    { id: 4, nombre: 'Transferencia' },
  ];

  usuarios: UsuarioItem[] = [
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
  ];

  modalAbierto = false;
  modoEdicion  = false;
  idEditando: number | null = null;
  private nextId = 4;

  form = new FormGroup({
    cedula:           new FormControl(''),
    primerNombre:     new FormControl(''),
    segundoNombre:    new FormControl(''),
    primerApellido:   new FormControl(''),
    segundoApellido:  new FormControl(''),
    correo:           new FormControl(''),
    fechaNacimiento:  new FormControl(''),
    idEstado:         new FormControl(1),
    idMetodoPagoPref: new FormControl(1),
  });

  getNombreEstado(id: number): string {
    return this.estados.find(e => e.id === id)?.nombre ?? '';
  }

  getNombreMetodoPago(id: number): string {
    return this.metodosPago.find(m => m.id === id)?.nombre ?? '';
  }

  getNombreCompleto(u: UsuarioItem): string {
    return [u.primerNombre, u.segundoNombre, u.primerApellido, u.segundoApellido]
      .filter(Boolean).join(' ');
  }

  abrirModal() {
    this.modoEdicion = false;
    this.idEditando  = null;
    this.form.reset({ idEstado: 1, idMetodoPagoPref: 1 });
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
  }

  editar(u: UsuarioItem) {
    this.modoEdicion = true;
    this.idEditando  = u.id;
    this.form.setValue({
      cedula:           u.cedula,
      primerNombre:     u.primerNombre,
      segundoNombre:    u.segundoNombre,
      primerApellido:   u.primerApellido,
      segundoApellido:  u.segundoApellido,
      correo:           u.correo,
      fechaNacimiento:  u.fechaNacimiento,
      idEstado:         u.idEstado,
      idMetodoPagoPref: u.idMetodoPagoPref,
    });
    this.modalAbierto = true;
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion) {
      this.usuarios = this.usuarios.map(u =>
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
      );
    } else {
      this.usuarios = [...this.usuarios, {
        id:               this.nextId++,
        cedula:           v.cedula!,
        primerNombre:     v.primerNombre!,
        segundoNombre:    v.segundoNombre ?? '',
        primerApellido:   v.primerApellido!,
        segundoApellido:  v.segundoApellido ?? '',
        correo:           v.correo!,
        fechaNacimiento:  v.fechaNacimiento!,
        idEstado:         Number(v.idEstado),
        idMetodoPagoPref: Number(v.idMetodoPagoPref),
      }];
    }
    this.cerrarModal();
  }

  eliminar(id: number) {
    this.usuarios = this.usuarios.filter(u => u.id !== id);
  }
}
