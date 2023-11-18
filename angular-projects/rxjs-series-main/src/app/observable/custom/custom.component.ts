import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-custom',
  templateUrl: './custom.component.html',
  styleUrls: ['./custom.component.scss']
})
export class CustomComponent implements OnInit, OnDestroy {


  techStatus!: any;
  techStatus2!: any;
  names!: any;
  nameStatus!: any;

  subs2!: Subscription;

  constructor(private _designUtility: DesignUtilityService) { }

  ngOnInit(): void {

    // ex - 01 (manual)
    // to create custom observable
    const cusObs1 = Observable.create((observer: any) => {
      // here we will get 3 things,
      // observer.next() - we can emit our data
      // observer.error() - we can emit the error
      // observer.complete() - we use to complete our observer

      setTimeout(() => {
        observer.next('Angular');
      }, 1000)
      setTimeout(() => {
        observer.next('Typescript');
      }, 2000)
      setTimeout(() => {
        observer.next('HTML and CSS');
        observer.complete();
      }, 3000)
      // here our method stops because of set timeout, so we will use error in subscribe below
      setTimeout(() => {
        observer.next('Javascript');
        // observer.error(new Error('limit exceed'));
      }, 4000)
      setTimeout(() => {
        observer.next('jQuery');
        // observer.complete();

      }, 5000)
    })

    // now we will subscribe the custom observable
    // subscribe(data, error, completed)
    cusObs1.subscribe((res: any) => {
      // console.warn(res);
      this._designUtility.print(res, 'elContainer');

    },
      (err: any) => {
        this.techStatus = 'error';
      },
      () => {
        this.techStatus = 'completed';
      }
    )

    // ex - 02 (custom interval)

    const arr2 = ['Angular', 'Javascript', 'HTML', 'CSS', 'Typescript'];

    const cusObs2 = Observable.create((observer: any) => {
      let count = 0;
      setInterval(() => {
        // observer.next('data emit '+count);
        observer.next(arr2[count]);


        if (count >= 2) {
          observer.error('error emit');
        }
        if (count >= 5) {
          observer.complete();
        }
        count++;
      }, 1000)
    })

    this.subs2 = cusObs2.subscribe((res: any) => {
      // console.warn(res);
      this._designUtility.print(res, 'elContainer2');
    },
      (err: any) => {
        this.techStatus2 = 'error';
      },
      () => {
        this.techStatus2 = 'completed';
      }
    )

    // ex - 03 (custom interval)

    const arr3 = ['Call of Duty: Mobile', 'Apex Legends Mobile', 'Battlefield Mobile', 'Diablo', 'Rainbow 6: Seige'];

    const cusObs3 = new Observable(((observer: any) => {
      let count = 0;
      setInterval(() => {
        observer.next(arr3[count]);

        count++;
        if (count >= 3) {
          observer.error('error emit');
        }

        if (count >= 5) {
          observer.complete();
        }
      }, 1000)
    }))

    cusObs3.subscribe((res: any) => {
      console.warn(res);
      this.names = res;
    },
      (err: any) => {
        this.nameStatus = 'error';
      },
      () => {
        this.nameStatus = 'completed';
      }
    )

  }


  ngOnDestroy(): void {
    this.subs2.unsubscribe();
  }

}
