import { Component, OnInit } from '@angular/core';
import { interval, Subscription, timer } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-interval',
  templateUrl: './interval.component.html',
  styleUrls: ['./interval.component.scss']
})
export class IntervalComponent implements OnInit {

  obsMsg!: any;
  videoSubscription!: Subscription;

  constructor(private _designUtility: DesignUtilityService) { }

  ngOnInit(): void {
    // const broadCastVideos = interval(2000);
    // it takes 2 arguments, i.e. due time, interval
    // timer(delay, interval)
    const broadCastVideos = timer(5000, 1000);


    this.videoSubscription = broadCastVideos.subscribe(res => {
      console.warn(res);
      this.obsMsg = 'Video ' + res;
      this._designUtility.print(this.obsMsg, 'elContainer');
      this._designUtility.print(this.obsMsg, 'elContainer2');
      this._designUtility.print(this.obsMsg, 'elContainer3');

      if(res >= 5) {
        this.videoSubscription.unsubscribe();
      }
    })
  }

}
