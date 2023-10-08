import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ApiService } from './api.service';
import { UserModel } from './models/user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn$.asObservable();
  private readonly TOKEN_NAME: string = 'auth';
  user!: UserModel;

  get token(): any {
    return localStorage.getItem(this.TOKEN_NAME);
  }

  constructor(private apiService: ApiService) {
    // const token = localStorage.getItem('auth');
    this._isLoggedIn$.next(!!this.token);
    if(this.token) {
      this.user = this._getUser(this.token);
    }
  }

  hasRole(role: string) {
    return this.user.roles?.includes(role);
  }

  login(username: string, password: string) {
    return this.apiService.login(username, password).pipe(
      tap((response: any) => {
        this._isLoggedIn$.next(true);
        localStorage.setItem(this.TOKEN_NAME, response.token);
        this.user = this._getUser(response.token);
      })
    );
  }

  /**
   * 1. UserRole guard
   * 2. UserModel interface
   * 3. _getUser method
   * 4. this.user = this._getUser(response.token); - in constructor also
   * 5. HasRoleGuard
   * 6. in app-routing.module.ts -  data: {
      role: 'Admin'
    },
   *
   */
  private _getUser(token: string): UserModel {
    return JSON.parse(atob(token.split('.')[1])) as UserModel;
  }
}
