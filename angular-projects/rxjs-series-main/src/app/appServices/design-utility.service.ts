import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AsyncSubject, BehaviorSubject, catchError, Observable, ReplaySubject, Subject } from 'rxjs';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class DesignUtilityService {

  exclusive = new Subject<boolean>();
  // userName = new Subject<boolean>();
  // previously in subject component and comp1/2/3 ts file, we have manually set the default value of userName, now to get rid of that, we will use behavioursubject
  userName = new BehaviorSubject<string>('World');

  // example for replay subject
  // 3 is last 3 emitted data stored and 5000  5 sec time
  videoEmit = new ReplaySubject<string>(3, 5000);

  // example of async subject
  asyncVideoEmit = new AsyncSubject();

  constructor(private http: HttpClient, private _errService: ErrorService) { }

  print(value: any, containerId: any) {
    let el = document.createElement('li');
    // el.innerText = 'hello world';
    el.innerText = value;
    document.getElementById(containerId)?.appendChild(el);
  }

  // method for concatmap2

  print2(value: any, containerId: any) {
    let el = document.createElement('div');
    el.setAttribute('class', 'item')
    // el.innerText = 'hello world';
    el.innerHTML = value;
    document.getElementById(containerId)?.prepend(el);
  }

  getProducts(): Observable<any> {
    return this.http.get<any>('https://test-products-b05fe.firebaseio.com/products.json').pipe(
      // we have to give error otherwise it wont return an error
      catchError(this._errService.handleError)
    );
  }
}
