import { Component, OnInit } from '@angular/core';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-comp2',
  templateUrl: './comp2.component.html',
  styleUrls: ['./comp2.component.scss']
})
export class Comp2Component implements OnInit {

  userName: any;


  constructor(private _du: DesignUtilityService) { 
    // this is written here to change the value in this component html file after clicking the button and it will change at every place wherever we have subscribe
    this._du.userName.subscribe((res: any) => {
      this.userName = res;
    })
  }

  ngOnInit(): void {
  }

  onChange(uname: any) {
    console.warn(uname.value); 
    this._du.userName.next(uname.value);
  }

}
