import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { Usuario } from './pages/usuarios/usuarios';
import { Vehiculos } from './pages/vehiculos/vehiculos';
import { Suscripciones } from './pages/suscripciones/suscripciones';
import { Viajes } from './pages/viajes/viajes';
import { Reportes } from './pages/reportes/reportes';
import { LoginComponent } from './pages/login/login';

export const routes: Routes = [
  { path: '',            component: HomeComponent           },
  { path: 'usuarios', component: Usuario },
  { path: 'vehiculos',   component: Vehiculos  },
  { path: 'suscripciones', component: Suscripciones },
  { path: 'viajes', component: Viajes },
  { path: 'reportes', component: Reportes},
  { path: 'login', component: LoginComponent },
  { path: '**',          redirectTo: ''                     }
];
