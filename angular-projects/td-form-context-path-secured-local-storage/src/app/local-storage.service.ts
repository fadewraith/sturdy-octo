import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {

  samples = [
    'DWghS6maZgZPh4FICE2w/n/jkHtClx9J5azBRLfYtX8sErCqOelV8S8aGY57qNcf',
    'AtIhj0SdDWrc1FZyEZVRAaSj/Gc0CXIcPRiXupgZKfxX/cGrSOyN2M0AIxpeLeSd',
    'R6jrnOYr0pboGD3xuoqiZ8PwF61k0Q3y1CxFHz+zANAe0h/PjkPaLepJJRwQBfcG',
    'far7f6aJPzz+0oRRR2tHhSZqBVx352eOLy77cVPjGyh+ieZq2EEkJyGWLCI5sd7f',
    'L15T/qKKRXPNLeu0FRC0heFC8xo2sFpzy05/fOJ4oo24J1Ihtw9oZs3JAFAMj5qE',
  ];

  constructor() {}

  setData(key: string, value: any): void {
    let data = JSON.stringify(value);
    const encryptedData = CryptoJS.AES.encrypt(
      data,
      this.getRandomKey(key)
    ).toString();
    localStorage.setItem(btoa(key), encryptedData);
  }

  getData(key: string): any {
    try {
      const encryptedData = localStorage.getItem(btoa(key));
      if (encryptedData) {
        const bytes = CryptoJS.AES.decrypt(
          encryptedData,
          this.getRandomKey(key)
        );
        return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
      }
    } catch (error) {
      throw new Error(`Caught an exception: ` + error);
    }
    // return null;
  }

  getRandomKey(str: string): string {
    // use key
    // get data from samples
    // key = 'abc'
    // in set and get always index should be same
    let i =
      new TextEncoder().encode(str).reduce((a, b) => a + b, 0) %
      this.samples.length;
    return this.samples[i];
  }
}
