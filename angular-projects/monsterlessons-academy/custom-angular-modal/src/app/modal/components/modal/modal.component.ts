import { Component, OnInit, Input, Output, EventEmitter, ElementRef } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent implements OnInit {

  constructor(private elementRef: ElementRef) {}

  ngOnInit(): void {
  }

  @Input() size? = 'md'; // ? means optional
  @Input() title? = 'Modal title';

  @Output() closeEvent  = new EventEmitter();
  @Output() submitEvent  = new EventEmitter();

  close(): void {
    this.elementRef.nativeElement.remove(); // removing the dom element, this is plain javascript
    this.closeEvent.emit();
  }

  submit(): void {
    this.elementRef.nativeElement.remove();
    this.submitEvent.emit();
  }



}
