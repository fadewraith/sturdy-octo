import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, ViewContainerRef } from '@angular/core';
import { ModalService } from './modal/modal.service';
import { GeneralService } from './shared/services/general.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'custom-angular-popup';
  slides: any = [
    {url: "", title: 'hello'},
    {url: "", title: 'world'},
    {url: "", title: 'this'},
    {url: "", title: 'is'},
    {url: "", title: 'angular'},
  ]
  showPopup!: boolean;
  constructor(
    public _generalService: GeneralService,
    private _modalService: ModalService,
    private _viewContainerRef: ViewContainerRef,
    @Inject(DOCUMENT) private _document: Document
  ) {}

  ngOnInit(): void {
    this.showPopup = this._generalService?.showDialog;
    document
      .getElementById('theme')
      ?.setAttribute(
        'href',
        '/home/thrymr/Desktop/custom-angular-popup/src/client-a-style.scss'
      ) as any;
  }

  openModal(e: any, modalTitle: any, modalText: any) {
    e.preventDefault();
    this._modalService.setRootViewContainerRef(this._viewContainerRef);
    this._modalService.addDynamicComponent(modalTitle, modalText);
  }

  loadTheme(cssFile: string) {
    /**
     * we can access it directly, but for making it cleaner, we are injecting it into the constructor
     * <head>
     *    <link rel="stylesheet" type="text/css" href="client-a.css">
     *    <link id="client-theme" rel="stylesheet" type="text/css" href="client-a.css"> // if we want to do optimization
     * </head>
     */
    const headEl = this._document.getElementsByTagName('head')[0]; // graabing the instance of the head section

    // getting referenece before adding it -
    const existingLinkEl = this._document.getElementById(
      'client-theme'
    ) as HTMLLinkElement;
    if (existingLinkEl) {
      existingLinkEl.href = cssFile;
    } else {
      const newLinkEl = this._document.createElement('link'); // creating the link section
      newLinkEl.id = 'client-theme';
      newLinkEl.rel = 'stylesheet'; // adding the different kind of property here
      newLinkEl.href = cssFile;

      headEl.appendChild(newLinkEl); // finally adding it to the head element
    }
  }
}
