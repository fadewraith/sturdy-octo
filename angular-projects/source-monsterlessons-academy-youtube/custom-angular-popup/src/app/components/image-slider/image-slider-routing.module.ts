import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ImageSliderComponent } from './image-slider.component';

const routes: Routes = [{ path: '', component: ImageSliderComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ImageSliderRoutingModule { }
