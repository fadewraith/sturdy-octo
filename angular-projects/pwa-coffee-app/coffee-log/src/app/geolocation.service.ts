import { Injectable } from '@angular/core';
import { PlaceLocation } from './logic/PlaceLocation';

@Injectable({
  providedIn: 'root'
})
export class GeolocationService {

  requestLocation(callback: Function) {
    // W3C Geolocation API
    // takes 2 arguments, which are callback functions
    navigator.geolocation.getCurrentPosition(
      position => {
        callback(position.coords)
      },
      error => {
        // TODO: log the error in the system
        callback(null);
      }
    );
  }

  // this will open the map in the native apps, if open in android it will open google map app
  getMapLink(location: PlaceLocation) {
    let query = '';
    if(location.latitude && location.longitude) {
      query = `${location.latitude},${location.longitude}`;
    } else {
      query = `${location.address}, ${location.city}`;
    }
    // if user has google map, it will open google app otherwise it will open website
    if(/iPad|iPhone|iPod/.test(navigator.userAgent)) {
      return `https://maps.apple.com/?q=${query}`;
    } else {
      return `https://maps.google.com/?q=${query}`;
    }
  }
}
