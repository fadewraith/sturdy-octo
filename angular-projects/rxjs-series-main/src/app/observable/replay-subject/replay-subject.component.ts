import { Component, OnInit } from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-replay-subject',
  templateUrl: './replay-subject.component.html',
  styleUrls: ['./replay-subject.component.scss']
})
export class ReplaySubjectComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  // list data
  user1List = [
    'Angular 1',
    'Angular 2',
  ];
  user2List: any[] = [
    // 'Javascript 1',
    // 'Javascript 2',
  ];
  user3List: any[] = [
    // 'Typescript 1',
    // 'Typescript 2',
  ];

  // subscribe mods
  subscribeMode2: boolean = false;
  subscribeMode3: boolean = false;

  // subscriptions
  subscription2!: Subscription;
  subscription3!: Subscription;

  ngOnInit(): void {
    this._du.videoEmit.subscribe(res => {
      console.warn(res);
      this.user1List.push(res);
    });
  }

  // video add method
  onVideoAdd(video: any) {
    // console.warn(video);
    this._du.videoEmit.next(video)
  }

  // user 2 subscribe button
  user2subscribe() {
    if(this.subscribeMode2) {
      this.subscription2.unsubscribe();
    } else {
      this.subscription2 = this._du.videoEmit.subscribe((res: any) => {
        this.user2List.push(res);
      })
    }
    this.subscribeMode2 = !this.subscribeMode2;
  }

  // user 3 subscribe button

  // user 2 subscribe button
  user3subscribe() {
    if(this.subscribeMode3) {
      this.subscription3.unsubscribe();
    } else {
      this.subscription3 = this._du.videoEmit.subscribe((res: any) => {
        this.user3List.push(res);
      })
    }
    this.subscribeMode3 = !this.subscribeMode3;
  }

  // toggle method
  // toggle properties
  methodInterval: boolean = false;
  intSubscription!: Subscription;
  toggleMethod() {
    const broadCastVideo = interval(1000);

    if(!this.methodInterval) {
      this.intSubscription = broadCastVideo.subscribe(res => {
        this._du.videoEmit.next('video '+res);
      })
    } else {
      this.intSubscription.unsubscribe();
    }
    this.methodInterval = !this.methodInterval;
  }


}
