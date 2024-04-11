import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UltimateFormsComponent } from './ultimate-forms/ultimate-forms.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [AppComponent, UltimateFormsComponent],
  imports: [FormsModule, BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
