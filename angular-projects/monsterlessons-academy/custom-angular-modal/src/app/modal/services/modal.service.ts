import {
  Injectable,
  TemplateRef,
  ComponentFactoryResolver,
  Injector,
  Inject,
} from '@angular/core';
import { ModalComponent } from '../components/modal/modal.component';
import { DOCUMENT } from '@angular/common';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ModalService {
  private _modalNotifier?: Subject<string>;
  constructor(
    private _resolver: ComponentFactoryResolver,
    private _injector: Injector,
    @Inject(DOCUMENT) private _document: Document
  ) {}
  open(
    content: TemplateRef<any>,
    options?: { size?: string; title?: string } // removed the type string to any because it was giving error
  ) {
    // here idea is to create the modal component and then inject it inside body and render
    // additionally to it, we must inject inside our component inside ng content, the content that we provided here inside our open function
    // and for doing that, we must do several things inside the constructor
    // its a factory that knows how to create a modal component, and we must provide inside our modal and out modal is ModalComponent
    const modalComponentFactory =
      this._resolver.resolveComponentFactory(ModalComponent);
    // content view reference and this is what we provide inisde and we are providing null inside
    const contentViewRef = content.createEmbeddedView(null);
    // we want to create our modal component
    // we also want to provide inside ng content, the content that we passes inside our open function, this is why as the second parameter, we must provide an array and inside it will be contentviewRef.rootNodes
    // contentViewRef is exactly what we created from our content that we provided inside, this is eactly what will inject our  template inside modal inside modal body after this we want to call detect changes
    const modalComponent = modalComponentFactory.create(this._injector, [
      contentViewRef.rootNodes,
    ]);

    modalComponent.instance.size = options?.size;
    modalComponent.instance.title = options?.title;
    modalComponent.instance.closeEvent.subscribe(() => this.closeModal());
    modalComponent.instance.submitEvent.subscribe(() => this.submitModal());


    // calling detect changes
    modalComponent.hostView.detectChanges();
    // and after this we want to inject this dom element inside our body, inorder to do that, we must access body here inside constructor, and we are doing it with INJECT k/w inside the contructor
    // this will attach our dom element inside body
    this._document.body.appendChild(modalComponent.location.nativeElement);
    this._modalNotifier = new Subject();
    return this._modalNotifier.asObservable();


  }

  closeModal() {
    this._modalNotifier?.complete();
  }

  submitModal() {
    this._modalNotifier?.next('confirm');
    this.closeModal();
  }
}
