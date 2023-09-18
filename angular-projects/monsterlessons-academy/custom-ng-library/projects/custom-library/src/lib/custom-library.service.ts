import { Injectable } from '@angular/core';
import { UserInterface } from './types/user.interface';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomLibraryService {

  constructor() { }
  
   getUsers(): Observable<UserInterface[]> {
    return of([
      { id: '1', name: 'Foo' },
      { id: '2', name: 'Bar' },
    ]);
  }
}
