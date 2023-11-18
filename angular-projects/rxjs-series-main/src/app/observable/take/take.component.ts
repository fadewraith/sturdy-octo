import { Component, OnInit } from '@angular/core';
import { from, fromEvent, interval, map, take, takeLast, takeUntil, timer } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-take',
  templateUrl: './take.component.html',
  styleUrls: ['./take.component.scss']
})
export class TakeComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  randomNames = ['Call of Duty: Mobile', 'Apex Legends Mobile', 'Battlefield Mobile', 'Diablo', 'Rainbow 6: Seige']

  ngOnInit(): void {
// from is used to convert array data to observable
    const nameSource = from(this.randomNames)
    // ex - 01 | take

    // const source = interval(1000).pipe(
    //   take(5)
    // );

    nameSource.pipe(
      take(5)
    )
    .subscribe(res => {
      console.log(res);
      this._du.print(res, 'elContainer')
    })

    // ex - 02 | takelast

    nameSource.pipe(
      takeLast(3)
    )
    .subscribe(res => {
      console.log(res);
      this._du.print(res, 'elContainer2')
    })


    // ex - 03 | takeuntil(condition)

    const source = interval(1000);

    let condition1 = timer(5000);
    // we want to stop on click, so we are using fromevent and it takes 2 params, i.e. target and event name
    // first is document so that whenever we click on document, it should get initialised and other is click wvent, so that it should get activated
    let condition2 = fromEvent(document, 'click');

    source.pipe(
      map(res => 'Number '+res),
      // takeUntil(condition1)
      takeUntil(condition2)
    )
    .subscribe(res => {
      console.log(res);
      this._du.print(res, 'elContainer3')
    })


  }



}
