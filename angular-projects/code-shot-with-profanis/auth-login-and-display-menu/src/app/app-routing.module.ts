import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { ProductsComponent } from './products/products.component';
import { IsAuthenticatedGuard } from './is-authenticated.guard';
import { HasRoleGuard } from './has-role.guard';

const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [IsAuthenticatedGuard]
  },
  {
    path: 'products',
    component: ProductsComponent,
    canActivate: [IsAuthenticatedGuard, HasRoleGuard],
    data: {
      role: 'Admin'
      // role: 'Creator'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
