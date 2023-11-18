import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html',
  styleUrls: ['./parent.component.css']
})
export class ParentComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

  }
  childOne: boolean = true;
  childTwo: boolean = false;
  childThree: boolean = false;
  childFour: boolean = false;
  childData: string = '';
  

  childOneData(e: any) {
    this.childData = e;
    this.childOne = false;
    this.childTwo = true;
  }
  data: any;
  childTwoData(e: any) {
    this.data = e;
    this.childTwo = false;
    this.childThree = true;
  }

  childThreeData(e: any) {
    if (e === 'thankYou') {
      this.childThree = false;
      this.childFour = true;
    }
  }

  backToC1(e: any) {
    if (e === 'back2one') {
      this.childOne = true;
    this.childTwo = false;
    } else if (e === 'hello') {
      this.childTwo = true;
      this.childThree=false;
    }
  }

 


}
