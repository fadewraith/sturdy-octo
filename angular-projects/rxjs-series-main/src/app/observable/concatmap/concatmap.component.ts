import { Component, OnInit } from '@angular/core';
import { concatAll, concatMap, delay, from, map, of } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-concatmap',
  templateUrl: './concatmap.component.html',
  styleUrls: ['./concatmap.component.scss']
})
export class ConcatmapComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  getData(data: any) {
    // after using of this data became an observable
    return of(data + ' video uploaded').pipe(delay(2000));
  }

  ngOnInit(): void {

    const source = from(['Tech', 'Comedy', 'News']);

    // Ex - 01 | Map
    source.pipe(
      map(res => this.getData(res))
    )
    .subscribe(res => {
      // console.warn(res);
      this._du.print(res, 'elContainer')
    })

    // Ex - 02 | Map + ConcatAll
    source.pipe(
      map(res => this.getData(res)),
      concatAll()
    )
    .subscribe(res => {
      // console.warn(res);
      this._du.print(res, 'elContainer2')
    })

    // Ex - 02 | concatmap
    source.pipe(
      concatMap(res => this.getData(res)),
    )
    .subscribe(res => {
      // console.warn(res);
      this._du.print(res, 'elContainer3')
    })


  }

}
