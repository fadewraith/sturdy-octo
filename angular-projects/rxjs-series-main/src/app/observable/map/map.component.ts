import { Component, OnInit } from '@angular/core';
import { from, interval, map, Subscription } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  sub1!: Subscription;
  sub2!: Subscription;

  // messages
  msg1: any;
  msg2: any;

  constructor(private _designUtility: DesignUtilityService) { }

  ngOnInit(): void {


    const broadcastVideos = interval(1000);

    // ex - 01


    // we will use .pipe for map operator
    this.sub1 = broadcastVideos.pipe(
      // map((data: any) => console.warn('mapData => ', 'Video ',data))
      map(data => 'Video ' + data)
    )
      .subscribe((res: any) => {
        // console.warn(res);
        this.msg1 = res;
        // console.log(interval(1000));

      })


    setTimeout(() => {
      this.sub1.unsubscribe();
    }, 10000);



    // ex - 02

    this.sub2 = broadcastVideos.pipe(map(data => data * 10))
      .subscribe(res => {
        // console.warn(res);
        this.msg2 = res;
      })

    setTimeout(() => {
      this.sub2.unsubscribe();
    }, 10000);


    // ex - 03

    const members = from([
      {
        id: 1,
        name: 'hello world',
      },
      {
        id: 2,
        name: 'john doe',
      },
      {
        id: 3,
        name: 'mac brew',
      },
      {
        id: 4,
        name: 'mclaren',
      },
      {
        id: 5,
        name: 'angular 14',
      },
    ]);

    // with from we convert an array to an observable stream
    // now we are creating observable
    // import from rxjs member
    // other way is to use from where we declare or initialise an array
    // let memObs = from(members);

    members.pipe(
      map(data => data.name)
    )
      .subscribe(response => {
        // we will get this data as an observable
        console.warn(response);
        this._designUtility.print(response, 'elContainer');
      })


  }

}
