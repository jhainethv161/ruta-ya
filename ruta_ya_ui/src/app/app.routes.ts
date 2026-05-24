import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { Usuario } from './pages/usuarios/usuarios';
import { Vehiculos } from './pages/vehiculos/vehiculos';
import { Suscripciones } from './pages/suscripciones/suscripciones';
import { Viajes } from './pages/viajes/viajes';
import { Reportes } from './pages/reportes/reportes';
import { LoginComponent } from './pages/login/login';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login',          component: LoginComponent },
  { path: '',               component: HomeComponent,    canActivate: [authGuard] },
  { path: 'usuarios',       component: Usuario,          canActivate: [authGuard] },
  { path: 'vehiculos',      component: Vehiculos,        canActivate: [authGuard] },
  { path: 'suscripciones',  component: Suscripciones,    canActivate: [authGuard] },
  { path: 'viajes',         component: Viajes,            canActivate: [authGuard] },
  { path: 'reportes',       component: Reportes,          canActivate: [authGuard] },
  { path: '**',             redirectTo: '' },
];
