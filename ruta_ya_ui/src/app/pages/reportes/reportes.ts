import { Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { ButtonComponent } from '../../components/atoms/button/button';

interface ReporteConfig {
  id: string;
  nombre: string;
  descripcion: string;
}

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonComponent],
  templateUrl: './reportes.html',
})
export class Reportes {

  readonly reportes: ReporteConfig[] = [
    {
      id: 'usuarios-por-estado',
      nombre: 'Usuarios por estado',
      descripcion: 'Lista la cédula, nombres y apellidos de los usuarios cuyo estado coincida con el parámetro seleccionado.',
    },
    {
      id: 'pagos-comision-mayor',
      nombre: 'Pagos con comisión superior a un porcentaje',
      descripcion: 'Obtiene los pagos cuya comisión de plataforma sea mayor o igual al porcentaje indicado del monto total.',
    },
    {
      id: 'viajes-rango-fechas',
      nombre: 'Viajes por rango de fechas',
      descripcion: 'Lista los viajes realizados en el rango de fechas indicado y los clasifica como Económico, Intermedio o Costoso según el valor estimado.',
    },
    {
      id: 'viajes-conductor-vehiculo',
      nombre: 'Viajes por conductor y vehículo',
      descripcion: 'Lista la cantidad de viajes realizados por cada conductor en cada vehículo, filtrando aquellos con al menos la cantidad mínima de viajes indicada.',
    },
    {
      id: 'recaudo-metodo-pago',
      nombre: 'Recaudo total por método de pago',
      descripcion: 'Obtiene el total recaudado y la cantidad de pagos agrupados por método de pago en el rango de fechas indicado.',
    },
    {
      id: 'usuarios-viajes-mayores-valor',
      nombre: 'Usuarios con viajes de alto valor',
      descripcion: 'Lista los usuarios que realizaron al menos un viaje cuyo monto total supera el valor indicado.',
    },
    {
      id: 'conductores-mas-viajes-promedio',
      nombre: 'Conductores con más viajes que el promedio',
      descripcion: 'Lista los conductores cuya cantidad de viajes en el rango de fechas supera el promedio general de viajes por conductor.',
    },
    {
      id: 'usuarios-pago-mayor-promedio',
      nombre: 'Usuarios con pagos sobre el promedio',
      descripcion: 'Lista los usuarios cuya suma de pagos en el rango de fechas supera el promedio de pagos por usuario.',
    },
    {
      id: 'metodos-pago-menos-usados',
      nombre: 'Métodos de pago menos utilizados',
      descripcion: 'Devuelve el o los métodos de pago con la menor cantidad de usos en el rango de fechas indicado.',
    },
  ];

  selectorControl = new FormControl('');

  get reporteActual(): ReporteConfig | null {
    return this.reportes.find(r => r.id === this.selectorControl.value) ?? null;
  }

  generar() {
    // pendiente
  }
}
