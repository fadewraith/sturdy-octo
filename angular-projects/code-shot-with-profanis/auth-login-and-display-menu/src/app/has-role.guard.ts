import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HasRoleGuard implements CanActivate {


  constructor(private _authService: AuthService) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // return this._authService.user.roles.includes('Admin');
    // const isAuthorised = this._authService.user.Role?.includes(route.data.role);
    const isAuthorised = this._authService.user.roles?.includes(route.data.role);

    if(!isAuthorised) {
      window.alert('you\'re not authorized');
    }
    return isAuthorised;
  }
  
}
