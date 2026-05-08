import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home';
import { UsuariosPageComponent } from './pages/usuarios/usuarios';
import { VehiculosPageComponent } from './pages/vehiculos/vehiculos';
import { SuscripcionesPageComponent } from './pages/suscripciones/suscripciones';
import { ViajesPageComponent } from './pages/viajes/viajes';

export const routes: Routes = [
  { path: '',            component: HomeComponent           },
  { path: 'usuarios',    component: UsuariosPageComponent   },
  { path: 'vehiculos',   component: VehiculosPageComponent  },
  { path: 'suscripciones', component: SuscripcionesPageComponent },
  { path: 'viajes',      component: ViajesPageComponent     },
  { path: '**',          redirectTo: ''                     }
];
