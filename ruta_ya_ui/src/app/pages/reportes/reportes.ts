import { Component, OnInit, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonComponent } from '../../components/atoms/button/button';
import { CatalogoItemDescripcion, UsuarioService } from '../../services/usuario.service';
import { ReporteService } from '../../services/reporte.service';

type FiltroTipo = 'date' | 'number' | 'catalog';

interface CatalogoItem {
  id: number;
  nombre: string;
}

interface FiltroConfig {
  key: string;
  label: string;
  tipo: FiltroTipo;
  catalogo?: string;
  step?: string;
  min?: number;
  placeholder?: string;
  hint?: string;
}

interface ReporteConfig {
  id: string;
  nombre: string;
  descripcion: string;
  filtros: FiltroConfig[];
}

const FILTROS_FECHA: FiltroConfig[] = [
  { key: 'fechaInicio', label: 'Fecha de inicio', tipo: 'date' },
  { key: 'fechaFin',    label: 'Fecha de fin',    tipo: 'date' },
];

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [ReactiveFormsModule, ButtonComponent],
  templateUrl: './reportes.html',
})
export class Reportes implements OnInit {

  readonly reportes: ReporteConfig[] = [
    {
      id: 'usuarios-por-estado',
      nombre: 'Usuarios por estado',
      descripcion: 'Lista la cédula, nombres y apellidos de los usuarios cuyo estado coincida con el parámetro seleccionado.',
      filtros: [
        { key: 'estado', label: 'Estado del usuario', tipo: 'catalog', catalogo: 'estadosUsuario' },
      ],
    },
    {
      id: 'pagos-comision-mayor',
      nombre: 'Pagos con comisión superior a un porcentaje',
      descripcion: 'Obtiene los pagos cuya comisión de plataforma sea mayor o igual al porcentaje indicado del monto total.',
      filtros: [
        { key: 'porcentaje', label: 'Porcentaje', tipo: 'number', step: '0.01', min: 0, placeholder: 'Ej: 0.15', hint: 'Valor decimal entre 0 y 1 (0.15 equivale al 15%).' },
      ],
    },
    {
      id: 'viajes-rango-fechas',
      nombre: 'Viajes por rango de fechas',
      descripcion: 'Lista los viajes realizados en el rango de fechas indicado y los clasifica como Económico, Intermedio o Costoso según el valor estimado.',
      filtros: [...FILTROS_FECHA],
    },
    {
      id: 'viajes-conductor-vehiculo',
      nombre: 'Viajes por conductor y vehículo',
      descripcion: 'Lista la cantidad de viajes realizados por cada conductor en cada vehículo, filtrando aquellos con al menos la cantidad mínima de viajes indicada.',
      filtros: [
        ...FILTROS_FECHA,
        { key: 'cantidadViajes', label: 'Cantidad mínima de viajes', tipo: 'number', step: '1', min: 1, placeholder: 'Ej: 5' },
      ],
    },
    {
      id: 'recaudo-metodo-pago',
      nombre: 'Recaudo total por método de pago',
      descripcion: 'Obtiene el total recaudado y la cantidad de pagos agrupados por método de pago en el rango de fechas indicado.',
      filtros: [...FILTROS_FECHA],
    },
    {
      id: 'usuarios-viajes-mayores-valor',
      nombre: 'Usuarios con viajes de alto valor',
      descripcion: 'Lista los usuarios que realizaron al menos un viaje cuyo monto total supera el valor indicado.',
      filtros: [
        { key: 'valor', label: 'Valor mínimo del viaje', tipo: 'number', step: '0.01', min: 0, placeholder: 'Ej: 30000' },
      ],
    },
    {
      id: 'conductores-mas-viajes-promedio',
      nombre: 'Conductores con más viajes que el promedio',
      descripcion: 'Lista los conductores cuya cantidad de viajes en el rango de fechas supera el promedio general de viajes por conductor.',
      filtros: [...FILTROS_FECHA],
    },
    {
      id: 'usuarios-pago-mayor-promedio',
      nombre: 'Usuarios con pagos sobre el promedio',
      descripcion: 'Lista los usuarios cuya suma de pagos en el rango de fechas supera el promedio de pagos por usuario.',
      filtros: [...FILTROS_FECHA],
    },
    {
      id: 'metodos-pago-menos-usados',
      nombre: 'Métodos de pago menos utilizados',
      descripcion: 'Devuelve el o los métodos de pago con la menor cantidad de usos en el rango de fechas indicado.',
      filtros: [...FILTROS_FECHA],
    },
  ];

  estadosUsuario = signal<CatalogoItem[]>([]);

  selectorControl = new FormControl('');
  modalAbierto    = signal(false);
  cargando        = signal(false);
  form            = new FormGroup<Record<string, FormControl>>({});

  constructor(
    private usuarioService: UsuarioService,
    private reporteService: ReporteService,
  ) {}

  ngOnInit() {
    this.usuarioService.getEstados().subscribe({
      next: (estados: CatalogoItemDescripcion[]) => {
        this.estadosUsuario.set(estados.map(e => ({ id: e.id, nombre: e.nombre })));
      },
      error: err => console.error('Error al cargar estados de usuario', err),
    });
  }

  get reporteActual(): ReporteConfig | null {
    return this.reportes.find(r => r.id === this.selectorControl.value) ?? null;
  }

  getCatalogo(key?: string): CatalogoItem[] {
    if (!key) return [];
    switch (key) {
      case 'estadosUsuario': return this.estadosUsuario();
      default: return [];
    }
  }

  generar() {
    const reporte = this.reporteActual;
    if (!reporte) return;

    const controls: Record<string, FormControl> = {};
    for (const filtro of reporte.filtros) {
      controls[filtro.key] = new FormControl('', { validators: [Validators.required] });
    }
    this.form = new FormGroup(controls);
    this.modalAbierto.set(true);
  }

  cerrarModal() {
    this.modalAbierto.set(false);
  }

  aceptar() {
    const reporte = this.reporteActual;
    if (!reporte || this.form.invalid) return;

    const params = this.form.value as Record<string, string | number>;
    this.cargando.set(true);

    this.reporteService.descargarExcel(reporte.id, params).subscribe({
      next: response => {
        const blob = response.body;
        if (!blob) {
          this.cargando.set(false);
          return;
        }
        const filename = this.extraerNombreArchivo(response.headers.get('Content-Disposition'))
          ?? `${reporte.id}.xlsx`;
        this.descargarBlob(blob, filename);
        this.cargando.set(false);
        this.cerrarModal();
      },
      error: err => {
        console.error('Error al generar el reporte', err);
        this.cargando.set(false);
      },
    });
  }

  private extraerNombreArchivo(contentDisposition: string | null): string | null {
    if (!contentDisposition) return null;
    const match = contentDisposition.match(/filename\*?=(?:UTF-8'')?"?([^";]+)"?/i);
    return match ? decodeURIComponent(match[1]) : null;
  }

  private descargarBlob(blob: Blob, filename: string) {
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
  }
}
