import { Component, OnInit } from '@angular/core';
import { delay, from, map, mergeAll, mergeMap, of, switchAll, switchMap } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-switchmap',
  templateUrl: './switchmap.component.html',
  styleUrls: ['./switchmap.component.scss']
})
export class SwitchmapComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }
  getData(data: any) {
    // after using of this data became an observable
    return of(data + ' video uploaded').pipe(delay(1000))
  }

  ngOnInit(): void {
    const source = from(['Tech', 'Comedy', 'News']);

    // Ex - 01 | Map

    source.pipe(
      map(data => this.getData(data))
    )
      .subscribe(res => {
        console.warn(res);
        this._du.print(res, 'elContainer')
      })



    // Ex - 02 | Map + SwitchAll
    // this will give only the last request send to the server and here in this case is the 'news' and will cancel the previous 2 requests send to the server

    source.pipe(
      map(data => this.getData(data)),
      switchAll()
    )
      .subscribe(res => {
        console.warn(res);
        this._du.print(res, 'elContainer2')
      })


    // Ex - 03 | SwitchMap

    source.pipe(
      switchMap(data => this.getData(data))
    )
      .subscribe(res => {
        console.warn(res);
        this._du.print(res, 'elContainer')
      })

  }

}
