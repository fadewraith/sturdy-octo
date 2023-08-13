import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TooltipDirective } from './directives/tooltip.directive';
import { TooltipComponent } from './tooltip/tooltip.component';

@NgModule({
  imports: [CommonModule],
  declarations: [TooltipComponent, TooltipDirective],
  exports: [TooltipDirective],
})
export class TooltipModule {}