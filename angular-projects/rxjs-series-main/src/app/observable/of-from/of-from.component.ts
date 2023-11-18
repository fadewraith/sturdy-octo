import { Component, OnInit } from '@angular/core';
import { from, of } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-of-from',
  templateUrl: './of-from.component.html',
  styleUrls: ['./of-from.component.scss']
})
export class OfFromComponent implements OnInit {

  constructor(private _designUtility: DesignUtilityService) { }
  obsMsg!: any;

  ngOnInit(): void {
    // of

    const obs1 = of('Call of Duty: Mobile', 'Apex Legends Mobile', 'Battlefield Mobile', 'Diablo');

    obs1.subscribe(res => {
      console.warn(res);
      this._designUtility.print(res, 'elContainer');
    });

    const obs2 = of({ a: 'Call of Duty: Mobile', b: 'Apex Legends Mobile', c: 'Battlefield Mobile', d: 'Diablo' });

    obs2.subscribe(res => {
      this.obsMsg = res;
      console.warn('obsMsg => ', res);
      // this._designUtility.print(res, 'elContainer');
    });

    // from array

    const obs3 = from(['Call of Duty: Mobile', 'Apex Legends Mobile', 'Battlefield Mobile', 'Diablo']);

    obs3.subscribe(res => {
      console.warn(res);
      this._designUtility.print(res, 'elContainer3');
    });

    // from promise

    const promise = new Promise(resolve => {
      setTimeout(() => {
        resolve('promise resolved')
      }, 3000);
    })

    promise.then(res => {
      console.warn(res);

    })

    const obs4 = from(promise);

    obs4.subscribe(res => {
      console.warn('from promise => ', res);
      this._designUtility.print(res, 'elContainer4');
    });


    // from string

    const obs5 = from('hello world');

    obs5.subscribe(res => {
      console.warn('from string => ', res);
      this._designUtility.print(res, 'elContainer5');
    });



  }


}
