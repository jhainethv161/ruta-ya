import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

import { ButtonComponent }     from '../../components/atoms/button/button';
import { IconButtonComponent } from '../../components/atoms/icon-button/icon-button';
import { FormFieldComponent }  from '../../components/molecules/form-field/form-field';

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
export class Suscripciones {

  suscripciones = [
    { codigo: 1, tipo: 'Estándar',  estado: 'Activo',     cedulaUsuario: '1001234567' },
    { codigo: 2, tipo: 'Básico',    estado: 'Activo',     cedulaUsuario: '1002345678' },
    { codigo: 3, tipo: 'Estándar',  estado: 'Suspendido', cedulaUsuario: '1003456789' },
    { codigo: 4, tipo: 'Básico',    estado: 'Activo',     cedulaUsuario: '1004567890' },
    { codigo: 5, tipo: 'Premium',   estado: 'Cancelado',  cedulaUsuario: '1005678901' },
    { codigo: 6, tipo: 'Básico',    estado: 'Activo',     cedulaUsuario: '1006789012' },
    { codigo: 7, tipo: 'Estándar',  estado: 'Activo',     cedulaUsuario: '1008901234' },
  ];

  modalAbierto   = false;
  modoEdicion    = false;
  codigoEditando = 0;
  private nextCodigo = 8;

  form = new FormGroup({
    cedulaUsuario: new FormControl(''),
    tipo:          new FormControl('Básico'),
    estado:        new FormControl('Activo'),
  });

  abrirModal() {
    this.modoEdicion    = false;
    this.codigoEditando = 0;
    this.form.reset({ tipo: 'Básico', estado: 'Activo' });
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
  }

  editar(s: any) {
    this.modoEdicion    = true;
    this.codigoEditando = s.codigo;
    this.form.setValue({
      cedulaUsuario: s.cedulaUsuario,
      tipo:          s.tipo,
      estado:        s.estado,
    });
    this.modalAbierto = true;
  }

  guardar() {
    const v = this.form.value;
    if (this.modoEdicion) {
      this.suscripciones = this.suscripciones.map(s =>
        s.codigo === this.codigoEditando
          ? { ...s, cedulaUsuario: v.cedulaUsuario!, tipo: v.tipo!, estado: v.estado! }
          : s
      );
    } else {
      this.suscripciones = [...this.suscripciones, {
        codigo:        this.nextCodigo++,
        cedulaUsuario: v.cedulaUsuario!,
        tipo:          v.tipo!,
        estado:        v.estado!,
      }];
    }
    this.cerrarModal();
  }

  eliminar(codigo: number) {
    this.suscripciones = this.suscripciones.filter(s => s.codigo !== codigo);
  }
}
