import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-child1',
  templateUrl: './child1.component.html',
  styleUrls: ['./child1.component.css']
})
export class Child1Component implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Output() moodArrC1 = new EventEmitter<string>();

  moodarr = [
    { id: 1, mood: "Happy", bgclr: "#FFE900FF", src: "../../assets/img/happy.svg" },
    { id: 2, mood: "Sad", bgclr: "#05C3E285", src: "../../assets/img/sad.svg" },
    { id: 3, mood: "Angry", bgclr: "#EA59595E", src: "../../assets/img/angry.svg" },
    { id: 4, mood: "Disgusted", bgclr: "#5CDD785E", src: "../../assets/img/Disgusted.svg" },
    { id: 5, mood: "Scared", bgclr: "#0003E94D", src: "../../assets/img/scared.svg" },
    { id: 5, mood: "Surprise", bgclr: "#0003E94D", src: "../../assets/img/surprised.svg" },

    { id: 6, mood: "Bored", bgclr: "#FFE900FF", src: "../../assets/img/bored.svg" },
    { id: 7, mood: "Guilty", bgclr: "#05C3E285", src: "../../assets/img/Guilt.svg" },
    { id: 8, mood: "Lonely", bgclr: "#EA59595E", src: "../../assets/img/lonely.svg" },
    { id: 9, mood: "Content", bgclr: "#5CDD785E", src: "../../assets/img/Content.svg" },
    { id: 10, mood: "Anxious", bgclr: "#0003E94D", src: "../../assets/img/anxious.svg" },
    { id: 11, mood: "Negative", bgclr: "#0003E94D", src: "../../assets/img/Negative.svg" },
    { id: 12, mood: "Proud", bgclr: "#0003E94D", src: "../../assets/img/proud.svg" },
  ];

  moodData:string='';
  chooseMood(data: any) {
    this.moodData=data;
    // console.warn(data);
    
  }
  goToNext(){
    this.moodArrC1.emit(this.moodData);
  }

}
