import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { combineLatest, fromEvent, map, pluck, withLatestFrom } from 'rxjs';

@Component({
  selector: 'app-combine-latest',
  templateUrl: './combine-latest.component.html',
  styleUrls: ['./combine-latest.component.scss']
})
export class CombineLatestComponent implements OnInit, AfterViewInit {

  constructor() { }

  // sources

  nameSource = ['hello', 'world', 'john', 'doe', 'alex']
  colorSource = ['red', 'green', 'blue', 'yellow', 'violet', 'purple']

  ngOnInit(): void {
  }

  // template references
  @ViewChild('name') name!: ElementRef;
  @ViewChild('color') color!: ElementRef;

  ngAfterViewInit() {
    // here its select element and change will work here
    // nameobs and colorobs are observables here
    const nameObs = fromEvent(this.name.nativeElement, 'change').pipe(
      map((event: any) => event.target.value)
    )
    // we have to make it observable to so we are removing subscribe from here
    // .subscribe(res => {
    //   console.warn(res);
    // })

    // for color
    const colorObs = fromEvent(this.color.nativeElement, 'change').pipe(
      // map((event: any) => event.target.value)
      pluck('target', 'value')
    )
    // .subscribe(res => {
    //   console.warn(res);
    // })

    // ex - 01 - combine latest
    combineLatest(nameObs, colorObs).subscribe(([name, color]) => {
      // console.warn(name, color);
      this.createBox(name, color, 'elContainer')
    })

    // ex - 02 withlatestform
    // mastere is nameobs
    // slave colorobs
    // this will only work when there will be change ins the master class

    nameObs.pipe(withLatestFrom(colorObs)).subscribe(([name, color]) => {
      console.warn(name, color);
      this.createBox(name, color, 'elContainer2')
    })


  }

  createBox(name: any, color: any, containerId: any) {
    let el = document.createElement('div');
    el.innerText = name;
    el.setAttribute("class", color);
    document.getElementById(containerId)?.appendChild(el);
  }













}
