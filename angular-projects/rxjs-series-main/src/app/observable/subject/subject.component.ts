import { Component, OnDestroy, OnInit } from '@angular/core';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-subject',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent implements OnInit, OnDestroy {

  userName: any;

  constructor(private _du: DesignUtilityService) {
    this._du.userName.subscribe((res: any) => {
      this.userName = res;
    })
   }

  ngOnInit(): void {
    // we have subscribe the subject exclusive in header and we are doing next here
    this._du.exclusive.next(true);
  }

  ngOnDestroy(): void {
    // when we change subject page it will destroy
    this._du.exclusive.next(false);
      
  }

}
