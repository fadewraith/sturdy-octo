import { Component, TemplateRef } from '@angular/core';
import { ModalService } from './modal/services/modal.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  // title = 'custom-angular-modal';
  openModal(modalTemplate: TemplateRef<any>) {
    // type is any, because we dont know what is the type of the component
    // here wewill call our modal service, which we can inject anywhere and it must first create a modal
    // we need subscribe here, because we want to know that when our modal will be closed or submitted
    // we dont need to inject the modal component here, this is flexible
    // we just create inside HTML the templatethat we want, we provided inside openModal and we are good to go
    this.modalService
      .open(modalTemplate, { size: 'lg', title: 'Foo' })
      .subscribe((action) => {
        console.warn('modalAction => ', action);
      });
  }

  constructor(private modalService: ModalService) {}


}
