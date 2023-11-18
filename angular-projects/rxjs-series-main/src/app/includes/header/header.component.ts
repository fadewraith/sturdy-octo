import { Component, OnInit } from '@angular/core';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';
// import {  Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  navOpen: boolean = false;

  // we have to make this property true only when we come to this specific page and for that we will make a subject and for that we will make in service file
  exclusive: boolean = false;

  constructor(private _du: DesignUtilityService) { }

  ngOnInit(): void {
    this._du.exclusive.subscribe(res => {
      this.exclusive = res;
    })
  }

  onNavToggle() {
    // this.route.navigate(['/promise']);
    this.navOpen = !this.navOpen;
  }


}
