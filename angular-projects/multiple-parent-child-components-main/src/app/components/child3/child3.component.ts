import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-child3',
  templateUrl: './child3.component.html',
  styleUrls: ['./child3.component.css']
})
export class Child3Component implements OnInit {
  @Input() data: any;
  @Output() dataChildFour = new EventEmitter<any>();
  @Output() backC2 = new EventEmitter<any>();

  constructor() { }

  ngOnInit(): void {
    console.log(this.data);

  }


dataCFour() {

  this.dataChildFour.emit('thankYou');
}



backToC2() {
  this.backC2.emit('hello');
}

}
