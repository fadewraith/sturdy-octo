import { Component, OnInit } from '@angular/core';
import { from, map, mergeAll, mergeMap, of } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-merge-map',
  templateUrl: './merge-map.component.html',
  styleUrls: ['./merge-map.component.scss']
})
export class MergeMapComponent implements OnInit {

  constructor(private _du: DesignUtilityService) { }

  getData(data: any) {
    // after using of this data became an observable
    return of(data + ' video uploaded')
  }

  ngOnInit(): void {

    const source = from(['Tech', 'Comedy', 'News']);

    // Ex - 01 | Map

    source.pipe(
      map(res => this.getData(res))
    )
      // will give the output of object only
      .subscribe(res => {
        console.log(res);
        this._du.print(res, 'elContainer');
      })
    // .subscribe(res => res.subscribe(res2 => {
    //   console.warn(res2);
    //   this._du.print(res2, 'elContainer')
    // }))


    // Ex - 02 | Map + MergeAll

    source.pipe(
      map(res => this.getData(res)),
      mergeAll()
    ).subscribe(res => {
      // console.log(res);
      this._du.print(res, 'elContainer2');
    })

    // Ex - 03 | mergeMap

    source.pipe(
      mergeMap(res => this.getData(res))
    ).subscribe(res => {
      console.log(res);
      this._du.print(res, 'elContainer3');
    })


  }



}
