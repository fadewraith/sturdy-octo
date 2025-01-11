import { Routes } from '@angular/router';

export const routes: Routes = [
  // since we are using default k/w, no need to use like this
  // {
  //   path: 'home',
  //   loadComponent: () => import('./home/home.component').then((m) => m.HomeComponent),
  // },
  {
    path: 'home',
    loadComponent: () => import('./home/home.component'),
  },
  {
    path: 'detail/:id',
    loadComponent: () => import('./detail/detail.component'),
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
];
