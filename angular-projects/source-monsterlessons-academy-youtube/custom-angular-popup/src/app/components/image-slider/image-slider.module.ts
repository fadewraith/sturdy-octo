import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ImageSliderRoutingModule } from './image-slider-routing.module';
import { ImageSliderComponent } from './image-slider.component';


@NgModule({
  declarations: [
    ImageSliderComponent
  ],
  imports: [
    CommonModule,
    ImageSliderRoutingModule
  ]
})
export class ImageSliderModule { }
