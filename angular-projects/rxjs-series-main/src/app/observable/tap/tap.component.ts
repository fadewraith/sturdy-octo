import { Component, OnInit } from '@angular/core';
import { interval, map, Subscription, tap, toArray } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-tap',
  templateUrl: './tap.component.html',
  styleUrls: ['./tap.component.scss']
})
export class TapComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  myColor: string = '';


  // with the help of tap operator, we can - 
  // console.log (date)
  // console.log (response)
  // change property value
  // emit value in observable
  // perform any other action
  
  ngOnInit(): void {
    
    const source = interval(1500);
    // ex - 01

    const arr = [ 'Anup', 'Shekhar', 'Sharma', 'Hello', 'World', 'John', 'Alex', 'Robert' ];


    let obsSubscription: Subscription;

    obsSubscription = source.pipe(
      tap(res => {
        this.myColor = colors[res];
        // console.warn('tap before ',res);
        if(res == 4) {
          obsSubscription.unsubscribe();
        }
      }),
      map(res =>
        arr[res]
      ),
      // tap(res => console.warn('tap after ', res)),
      // toArray(),
    )
    .subscribe(res => {
      // console.warn(res);
      this._du.print(res, 'elContainer')
    })


    // ex - 02

    const colors = [ 'red', 'blue', 'green', 'yellow', 'orange', 'black' ]


    let obsSubscription2: Subscription;

    obsSubscription2 = source.pipe(
      tap(res => {
        console.warn('tap ',res);
        if(res == 7) {
          obsSubscription2.unsubscribe();
        }
      }),
      map(res =>
        colors[res]
      ),
      
      // toArray(),
    )
    .subscribe(res => {
      // console.warn(res);
      this._du.print(res, 'elContainer2')
    })



  }

}
