import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-promise',
  templateUrl: './promise.component.html',
  styleUrls: ['./promise.component.scss']
})
export class PromiseComponent implements OnInit {

  constructor() { }

  dellAvailable() {
    // return true;
    return false;
  }

  hpAvailable() {
    return false;
    // return true; 
  }

  promiseValue!: any;

  dell = {
    brand: 'Dell',
    hardDisk: '2 TB',
    color: 'Black'
  }

  hp = {
    brand: 'HP',
    hardDisk: '1 TB',
    color: 'Silver'
  }

  notAvailable = {
    brand: 'not available',
    status: 'failed',
  }

  ngOnInit(): void {
    // promise takes function 
    // let buyLaptop = new Promise(function (resolve, reject) {
    //   resolve('promise is resolved');
    // });
    // other way of writing promise
    let buyLaptop = new Promise((resolve, reject) => {
      // resolve('promise is resolved');
      // reject('promise is rejected');
      if (this.dellAvailable()) {
        return setTimeout(() => {
          // resolve('dell is purchased');
          resolve(this.dell);
        }, 3000);
      } else if(this.hpAvailable()) {
        return setTimeout(() => {
          // resolve('hp is purchased');
          resolve(this.hp);
        }, 3000);
      } else {
        return setTimeout(() => {
          // reject('laptop is not available');
          resolve(this.notAvailable);
        }, 3000);
      }
    });

    // then is used when promise is resolved and catch is used when it fails
    buyLaptop.then(res => {
      // console.warn("success");
      console.warn("then code => ", res);
      this.promiseValue = res;
      
    }).catch(res => {
      console.warn('catch code => ', res);
      this.promiseValue = res;
    })
  }

  myFunction() {
    console.warn('myfunction called');

  }

}
