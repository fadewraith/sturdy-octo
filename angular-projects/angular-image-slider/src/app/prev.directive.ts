import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appPrev]'
})
export class PrevDirective {

  constructor(private _el: ElementRef) {
    console.warn(this._el.nativeElement);
  }

  @HostListener('click')
  prevFunction() {
    // we will get the parent button with class name button-area
    let element = this._el.nativeElement.parentElement.parentElement.children[0];
    let item = element.getElementsByClassName("item");
    // console.warn(element);
    element.prepend(item[item.length - 1]);
    // console.warn(item);
  }

}
