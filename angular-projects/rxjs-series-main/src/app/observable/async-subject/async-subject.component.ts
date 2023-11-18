import { Component, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-async-subject',
  templateUrl: './async-subject.component.html',
  styleUrls: ['./async-subject.component.scss']
})
export class AsyncSubjectComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  asyncVideoEmit: any;


  ngOnInit(): void {
   this._du.asyncVideoEmit.subscribe(res => {
    this.asyncVideoEmit = res;
   })
  }

  // video add method
  onVideoAdd(video: any) {
    console.warn(video);
    this._du.asyncVideoEmit.next(video);
  }

  // oncomplete method
  // we will only get last emitted value
  onComplete() {
    this._du.asyncVideoEmit.complete();
  }



}
