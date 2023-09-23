import {
  Directive,
  ElementRef,
  HostBinding,
  HostListener,
  Input,
  OnInit,
  Renderer2,
} from '@angular/core';

@Directive({
  selector: '[appHighlight]',
})
export class HighlightDirective implements OnInit {
  @Input() defaultColor!: string; // default text color
  @Input() highlightedColor: string = 'green'; // highlighted color
  // marks a DOM property or an element class, style or attribute to supply metadata
  @HostBinding('style.backgroundColor') backgroundColor!: string;

  /**
   * Renderer acts on the DOM and ElementRef is a reference to an element in the DOM the Renderer acts on.
   */
  constructor(private _el: ElementRef, private _renderer: Renderer2) {}

  ngOnInit(): void {
    this.backgroundColor = this.defaultColor;
  }

  @HostListener('mouseenter') mouseover(e: Event) {
    this.backgroundColor = this.highlightedColor;
  }

  @HostListener('mouseleave') mouseleave(e: Event) {
    this.backgroundColor = this.defaultColor;
  }
}
