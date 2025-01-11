import { Dialog } from '@angular/cdk/dialog';
import {
  Component,
  ContentChild,
  Input,
  TemplateRef,
  inject,
} from '@angular/core';

@Component({
  standalone: true,
  selector: 'app-modal',
  template: `<div></div>`,
})
export class ModalComponent {
  // The general idea is that to use a Dialog from the Angular CDK we can inject it and then we call its open method:
  dialog = inject(Dialog);

  @Input() set isOpen(value: boolean) {
    if (value) {
      this.dialog.open(this.template, { panelClass: 'dialog-container' });
    } else {
      this.dialog.closeAll();
    }
  }

  // The general idea with @ContentChild is that it allows us to access the content that is supplied within the component’s tags like this when it is being used (as opposed to the template of the app-modal component itself which is what @ViewChild is for).
  // We can get a reference to a TemplateRef (this is what an <ng-template> is) that is supplied inside of the <app-modal> selector. This means that the template class member we are setting up with @ContentChild here will be whatever template we supplied inside of <app-modal>:

  @ContentChild(TemplateRef, { static: false }) template!: TemplateRef<any>;
}
