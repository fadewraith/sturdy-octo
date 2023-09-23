import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HighlightDirective } from './directives/highlight.directive';
import { PermissionsDirective } from './directives/permissions.directive';
import { PasswordLengthDirective } from './directives/password-length.directive';

@NgModule({
  declarations: [
    AppComponent,
    HighlightDirective,
    PermissionsDirective,
    PasswordLengthDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
