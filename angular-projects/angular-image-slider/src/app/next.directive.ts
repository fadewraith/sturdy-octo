import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appNext]'
})
export class NextDirective {

  constructor(private _el: ElementRef) {
    console.warn(this._el.nativeElement);
  }

  @HostListener('click')
  nextFunction() {
    // we will get the parent button with class name button-area
    let element = this._el.nativeElement.parentElement.parentElement.children[0];
    let item = element.getElementsByClassName("item");
    // console.warn(element);
    element.append(item[0]);
    // console.warn(item);
  }

}
