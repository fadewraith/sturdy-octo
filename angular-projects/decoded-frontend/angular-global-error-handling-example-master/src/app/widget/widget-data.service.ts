import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Task } from '../task.model';
import { catchError, of, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WidgetDataService {
  constructor(private http: HttpClient) {}

  load() {
    // return this.http.get<Task[]>(`https://jsonplaceholder.typicode.com/todos?_start=0&_limit=3`);
    // this will create an error and for this error we have unexpected behaviour, the Backlog Widget is empty
    // but if we want to show the erro and placeholder, then in that case we have to use the catchError in ngOnInit in widget component ts file
    return this.http
      .get<Task[]>(
        `https://jsonplaceholder.typicode.com/todosa?_start=0&_limit=3`
      )
      .pipe(catchError(() => {
        console.info('Error handled by widget service');
        // but if we will return the observable, the catchError operator in the component will not be invoked, so likewise with try catch operator, we have to rethrow the error downstream
        // return of([]); // as mentioned above, will not be invoked
        // return throwError(() => err); // rethrowing the error downstream
        return throwError(() => {
          console.warn('Error rethrown by Widget Service...');
          return new Error(`Couldn't load data...`);

        }); // rethrowing the error downstream and this will be propagated downstream tot eh tap operator and eventually caught by the catch
      }));
  }

  addTaskSync(task: Task): Task | never {
    if (task.id === 0) {
      throw Error(`Value zero (0) is not allowed as a task id`);
    }
    return task;
  }
}
