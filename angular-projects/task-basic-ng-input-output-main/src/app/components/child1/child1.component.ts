import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-child1',
  templateUrl: './child1.component.html',
  styleUrls: ['./child1.component.css']
})
export class Child1Component implements OnInit {

  @Input() emp: any;

  constructor() { }

  ngOnInit(): void {
  }

  colorChange(designation: string): any {
    if (designation === 'BA') 
      return 'bg-red-500';
    else if (designation === 'QA')
      return 'bg-blue-400';
    else if (designation === 'HR')
      return 'bg-green-400';
    else if (designation === 'Developer')
      return 'bg-orange-400';
  }

  onClick() {
    
  }

}
