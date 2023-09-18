import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { map, Observable, pluck } from 'rxjs';
import { UserLoaderService } from './user-loader.service';

@Injectable({
  providedIn: 'root'
})
export class UserTitleResolverService implements Resolve<string> {

  constructor(private userLoader: UserLoaderService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): string | Observable<string> | Promise<string> {
    /**
     * step - 6 - steps 1- 5 - read README.md file
     * route.paramMap.get('id') - this will return whole object,
     * but we want only 'name' from the object, so we're using the 'pluck' operator,
     * to get the 'name' from the object.
     * '!' - it tells ts, that dont worry, there will be some value, its not a good practice.
     * 
     */
    this.userLoader.loadUser(route.paramMap.get('id')!).subscribe((d) => {
      console.warn(d);
    });
    return this.userLoader.loadUser(route.paramMap.get('id')!).pipe(
      pluck('name'),
      map(name => `User - ${name}`)
    )
  }
}
