import { Component } from '@angular/core';
import { PageTitleComponent } from '../../components/atoms/page-title/page-title';

@Component({
  selector: 'app-viajes-page',
  standalone: true,
  imports: [PageTitleComponent],
  templateUrl: './viajes.html',
})
export class ViajesPageComponent {}
