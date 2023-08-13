import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss'],
})
export class ModalComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  @Input() modalTitle!: string;
  @Input() modalText!: string;
  @Output() closeModal: EventEmitter<any> = new EventEmitter<any>();

  close(event: any) {
    this.closeModal.emit(event);
  }
}
