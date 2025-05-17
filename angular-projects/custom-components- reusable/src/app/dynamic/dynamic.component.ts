import { Component, ComponentRef, Input, ViewChild, ViewContainerRef } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dynamic',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dynamic-container">
      <ng-container #dynamicHost></ng-container>
    </div>
  `,
  styles: [`
    .dynamic-container {
      padding: 1rem;
    }
  `]
})
export class DynamicComponent {
  @ViewChild('dynamicHost', { read: ViewContainerRef, static: true })
  dynamicHost!: ViewContainerRef;

  private componentRef: ComponentRef<any> | null = null;

  @Input() set component(component: any) {
    this.loadComponent(component);
  }

  @Input() set componentData(data: any) {
    if (this.componentRef && data) {
      Object.assign(this.componentRef.instance, data);
    }
  }

  loadComponent(component: any) {
    this.dynamicHost.clear();
    if (component) {
      this.componentRef = this.dynamicHost.createComponent(component);
    }
  }

  ngOnDestroy() {
    if (this.componentRef) {
      this.componentRef.destroy();
    }
  }
}