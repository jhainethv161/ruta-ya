import { Component } from '@angular/core';
import { PageTitleComponent } from '../../components/atoms/page-title/page-title';

@Component({
  selector: 'app-usuarios-page',
  standalone: true,
  imports: [PageTitleComponent],
  templateUrl: './usuarios.html',
})
export class UsuariosPageComponent {}
