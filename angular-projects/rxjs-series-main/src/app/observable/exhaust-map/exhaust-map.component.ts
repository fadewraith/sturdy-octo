import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { concatMap, exhaustMap, fromEvent, tap } from 'rxjs';
import { DesignUtilityService } from 'src/app/appServices/design-utility.service';

@Component({
  selector: 'app-exhaust-map',
  templateUrl: './exhaust-map.component.html',
  styleUrls: ['./exhaust-map.component.scss']
})
export class ExhaustMapComponent implements OnInit, AfterViewInit {

  num = 0;
  saveRequest!: any;
  fetching: boolean = false;

  @ViewChild('btn') btn!: ElementRef;

  constructor(
    private http: HttpClient,
    private _du: DesignUtilityService
  ) { }

  url = 'https://global-1bb0f.firebaseio.com/exhaustMap.json'

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    // whenever we work with DOM element we work in ngafterviewinit lifecycle hook
    // takes 2 arguments - target and event
    fromEvent(this.btn.nativeElement, 'click').pipe(
      tap(() => this.fetching = true),
      // concatMap(() => this.onSave(this.num++))
      exhaustMap(() => this.onSave(this.num++))
    )
      .subscribe(res => {
      console.warn(res);
      // calling here after subscribing the request
        this.onFetch();
        this.fetching = false;
    })

  }

  onSave(changes: any) {
    return this.http.put(this.url, { data: changes });
  }

  onFetch() {
    this.http.get<any>(this.url).subscribe(res => {
      console.warn(res.data);
      this.saveRequest = res.data;
    })
  }


  // btnClick() {
  //   this.onSave(this.num++).subscribe(res => {
  //     console.warn(res);

  //   });
  // }


}
