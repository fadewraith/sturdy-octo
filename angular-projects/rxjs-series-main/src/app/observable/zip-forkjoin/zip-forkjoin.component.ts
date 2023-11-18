import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { forkJoin, fromEvent, map, take, zip } from 'rxjs';

@Component({
  selector: 'app-zip-forkjoin',
  templateUrl: './zip-forkjoin.component.html',
  styleUrls: ['./zip-forkjoin.component.scss']
})
export class ZipForkjoinComponent implements AfterViewInit {

  // sources

  nameSource = ['hello', 'world', 'john', 'doe', 'alex']
  colorSource = ['red', 'green', 'blue', 'yellow', 'violet', 'purple']

  // template references
  @ViewChild('name') name!: ElementRef;
  @ViewChild('color') color!: ElementRef;

  constructor() { }

  ngAfterViewInit(): void {

    // zip operator works when the change is detected in both of the sources and then the result is generated

    const nameObs = fromEvent<any>(this.name.nativeElement, 'change').pipe(
      map(event => event.target.value),
      take(4)
    )
    // .subscribe(res => {
    // console.warn(res);

    // })
    // 

    const colorObs = fromEvent<any>(this.color.nativeElement, 'change').pipe(
      map(event => event.target.value),
      take(3)
    )
    // .subscribe(res => {
    //   console.warn(res);

    // })

    // ex - 01 zip
    // when both the observables will detect the change, then it will reflect the result
    zip(nameObs, colorObs).subscribe(([name, color]) => {
      console.warn(name, color);
      this.createBox(name, color, 'elContainer')
    })

    // ex - 02 forkjoin
    // when all the observables will be finished then it will be shown in the forkjoin div, it only works with last value, when both streams will be finished then the last values of the both the streams will be show in the forkjoin
    forkJoin(nameObs, colorObs).subscribe(([name, color]) => {
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
