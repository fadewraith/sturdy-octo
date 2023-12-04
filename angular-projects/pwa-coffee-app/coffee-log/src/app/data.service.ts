import { Injectable } from '@angular/core';
import { Coffee } from './logic/Coffee';
import { PlaceLocation } from './logic/PlaceLocation';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private _httpClient: HttpClient) {}

  public endpoint = "http://localhost:3000";
  public coffeeEntity = "/coffees";


  getList(callback: Function) {
    // const list = [
    //   new Coffee("1", "Double Espresso", "Cafe Tortoni", new PlaceLocation("Av. de Mayo 600", "Buenos Aires")),
    //   new Coffee("2", "Honey Americano", "Starcoffee", new PlaceLocation("Gran Via 34", "Madrid"))

    // ]
    // // return list;
    // callback(list);
    this._httpClient.get(`${this.endpoint}${this.coffeeEntity}`).subscribe(response => callback(response));
  }

  get(coffeeId: string, callback: Function) {
    this._httpClient.get(`${this.endpoint}${this.coffeeEntity}/${coffeeId}`).subscribe(response => callback(response));
  }

  save(coffee: any, callback: Function) {
    if(coffee._id) {
      // its an update
      // TODO: error checking
      this._httpClient.put(`${this.endpoint}${this.coffeeEntity}/${coffee._id}`, coffee).subscribe(response => callback(true));
    } else {
      // new item
      this._httpClient.post(`${this.endpoint}${this.coffeeEntity}`, coffee).subscribe(response => callback(true));
    }
  }
}
