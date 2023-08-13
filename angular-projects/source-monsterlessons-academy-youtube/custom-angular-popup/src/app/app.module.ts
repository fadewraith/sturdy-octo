import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DialogComponent } from './shared/components/dialog/dialog.component';
import { ModalComponent } from './modal/modal.component';
import { ImageSliderComponent } from './components/image-slider/image-slider.component';

@NgModule({
  declarations: [
    AppComponent,
    DialogComponent,
    ModalComponent,
    ImageSliderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
