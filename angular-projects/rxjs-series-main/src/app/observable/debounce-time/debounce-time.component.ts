import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';

@Component({
  selector: 'app-debounce-time',
  templateUrl: './debounce-time.component.html',
  styleUrls: ['./debounce-time.component.scss']
})
export class DebounceTimeComponent implements AfterViewInit {

  // creating myinput property whose type will be ElementRef
  // and now here we can get the value of that input text with the help of this template reference variable
  // 'myinput will render when our DOM will load and for that we have to use another lifecycle hook i.e. afterviewinit, coz the code in ngonint will be executed first and DOM element til that time wont have been used and it will produce an error
  @ViewChild('myInput') myInput!: ElementRef;
  reqData = null;
  // ex - 02
  @ViewChild('myInput2') myInput2!: ElementRef;
  reqData2 = null;

  constructor() { }

  // ngOnInit(): void {

  //   // passing 2 params, i.e. target and event name
  //   // here we use nativelement to get its data
  //   const searchTerm = fromEvent(this.myInput.nativeElement, 'keyup')
  //   searchTerm.subscribe(res => {
  //     console.warn(res);

  //   })
  // }

  // currently here we dont need ngonint, so we have commented ngoninit
  ngAfterViewInit(): void {
    // here we are using pipe to get only values we type
    // ex - 01 debounce time
    const searchTerm = fromEvent(this.myInput.nativeElement, 'keyup').pipe(
      // map((event: any) => {
      map((event: any) => event.target.value),
      debounceTime(1000)
    )
    searchTerm.subscribe(res => {
      console.warn(res);
      this.reqData = res;


      setTimeout(() => {
        this.reqData = null;
      }, 2000);
    })

    // ex - 02 distinct until changed
    const searchTerm2 = fromEvent(this.myInput2.nativeElement, 'keyup').pipe(
      // map((event: any) => {
        debounceTime(1000),
      map((event: any) => event.target.value),
      // what it doe actually is - write hello and hellos, so if we remove hellos, it wont send request after we remove s fromm 'hellos' because of the below rxjs operator
      distinctUntilChanged()
    )

    searchTerm2.subscribe(res => {
      console.warn(res);
      this.reqData2 = res;


      setTimeout(() => {
        this.reqData2 = null;
      }, 2000);
    })


  }



}
