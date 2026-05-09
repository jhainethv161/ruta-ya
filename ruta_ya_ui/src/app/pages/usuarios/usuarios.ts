import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

import { PageTitleComponent }  from '../../components/atoms/page-title/page-title';
import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';

@Component({
  selector: 'app-usuario',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgClass,
    PageTitleComponent,
    ButtonComponent,
    IconButtonComponent,
    FormFieldComponent,
  ],
  templateUrl: './usuarios.html',
})
export class Usuario {

  usuarios = [
    { id: 1, nombre: 'Ana Gómez',    correo: 'ana@rutaya.com',    telefono: '3001111111', rol: 'Pasajero'  },
    { id: 2, nombre: 'Carlos Ríos',  correo: 'carlos@rutaya.com', telefono: '3112222222', rol: 'Conductor' },
    { id: 3, nombre: 'Laura Torres', correo: 'laura@rutaya.com',  telefono: '3203333333', rol: 'Pasajero'  },
  ];

  modalAbierto = false;
  modoEdicion  = false;
  idEditando: number | null = null;
  private nextId = 4;

  form = new FormGroup({
    nombre:   new FormControl(''),
    correo:   new FormControl(''),
    telefono: new FormControl(''),
    rol:      new FormControl('Pasajero'),
  });

  abrirModal() {
    this.modoEdicion = false;
    this.idEditando  = null;
    this.form.reset({ rol: 'Pasajero' });
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
  }

  editar(u: any) {
    this.modoEdicion = true;
    this.idEditando  = u.id;
    this.form.setValue({ nombre: u.nombre, correo: u.correo, telefono: u.telefono, rol: u.rol });
    this.modalAbierto = true;
  }

  guardar() {
  const v = this.form.value;
  if (this.modoEdicion) {
    this.usuarios = this.usuarios.map(u =>
      u.id === this.idEditando
        ? { ...u, nombre: v.nombre!, correo: v.correo!, telefono: v.telefono!, rol: v.rol! }
        : u
    );
  } else {
    this.usuarios = [...this.usuarios, {
      id: this.nextId++,
      nombre:   v.nombre!,
      correo:   v.correo!,
      telefono: v.telefono!,
      rol:      v.rol!,
    }];
  }
  this.cerrarModal();
}

  eliminar(id: number) {
    this.usuarios = this.usuarios.filter(u => u.id !== id);
  }
}