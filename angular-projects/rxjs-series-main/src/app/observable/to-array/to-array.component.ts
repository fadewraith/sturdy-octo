import { Component, OnInit } from '@angular/core';
import { from, interval, of, Subscription, take, toArray } from 'rxjs';

@Component({
  selector: 'app-to-array',
  templateUrl: './to-array.component.html',
  styleUrls: ['./to-array.component.scss']
})
export class ToArrayComponent implements OnInit {

  users = [
    { name: 'John', skill: 'Developer'},
    { name: 'Doe', skill: 'Engineer'},
    { name: 'Chris', skill: 'Analyst'},
    { name: 'Fizz', skill: 'Lead'},
  ]

  constructor() { }

  sourceSub!: Subscription;

  ngOnInit(): void {
    // transform works before subscription
    // there are some pipable operators that's why we use pipe here

    // Ex - 01
    const source = interval(1000);
    this.sourceSub = source.pipe(take(5), toArray()).subscribe(res => {
      // console.warn(res);

      // if (res >= 8) {
      //   this.sourceSub.unsubscribe();
      // }
      
    })

    // Ex - 02


    const source2 = from(this.users);

    source2.pipe(toArray()).subscribe(res => {
      // console.warn(res);
      
    })

    // Ex - 03

    const source3 = of('hello', 'world', 'john', 'doe');

    source3.pipe(toArray()).subscribe(res => {
      console.warn(res);
      
    })

    
  }



}
