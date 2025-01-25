import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  set token(token: string) {
    localStorage.setItem('token', token);
  }

  get token() {
    return localStorage.getItem('token') as string;
  }

  set fullName(value: string) {
    localStorage.setItem('fullName', value);
  }

  get fullName() {
    return localStorage.getItem('fullName') as string;
  }

  isTokenNotValid() {
    return !this.isTokenValid();
  }

  private isTokenValid(): boolean {
    const token: string = this.token;
    if (!token) {
      return false;
    }
    // npm i @auth0/angular-jwt
    // decode the token
    const jwtHelper: JwtHelperService = new JwtHelperService();
    // check expiry date
    const isTokenExpired: boolean = jwtHelper.isTokenExpired(token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }
}
