import { Directive, ElementRef, HostListener, OnChanges } from '@angular/core';

@Directive({
  selector: '[appPasswordLength]'
})
export class PasswordLengthDirective implements OnChanges {

  constructor(private _el: ElementRef) {
    console.warn(_el.nativeElement.value);
   }

   // Listen for keyup event and change background color
  @HostListener("window:keyup") ngOnChanges(event: any) {

    let count = this._el.nativeElement.value.length
    console.log(this._el.nativeElement.value.length);
    if(count < 5) {
        this._el.nativeElement.style.backgroundColor = 'red'
    } else if(count >= 5 && count <= 10) {
        this._el.nativeElement.style.backgroundColor = 'green'
    } else if(count > 10) {
        this._el.nativeElement.style.backgroundColor = 'purple'
    }

  }

}
