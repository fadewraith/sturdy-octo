import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AngularMaterialModule } from './angular-material/angular-material.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FakeBackendProvider } from './fake-backend.interceptor';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NavigationComponent } from './navigation/navigation.component';
import { ProductsComponent } from './products/products.component';
import { MatDividerModule } from '@angular/material/divider';
import { AuthInterceptorProvider } from './auth.interceptor';
import { HasRoleDirective } from './has-role.directive';

@NgModule({
  declarations: [AppComponent, LoginComponent, DashboardComponent, NavigationComponent, ProductsComponent, HasRoleDirective],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    AngularMaterialModule,
    MatDividerModule,
    HttpClientModule,
  ],
  providers: [AuthInterceptorProvider, FakeBackendProvider],
  bootstrap: [AppComponent],
})
export class AppModule {}
