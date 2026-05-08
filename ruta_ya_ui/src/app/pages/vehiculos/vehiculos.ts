import { Component } from '@angular/core';
import { PageTitleComponent } from '../../components/atoms/page-title/page-title';

@Component({
  selector: 'app-vehiculos-page',
  standalone: true,
  imports: [PageTitleComponent],
  templateUrl: './vehiculos.html',
})
export class VehiculosPageComponent {}
