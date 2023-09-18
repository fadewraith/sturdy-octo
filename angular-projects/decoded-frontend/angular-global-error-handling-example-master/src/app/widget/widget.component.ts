import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { Observable, catchError, map, of, tap } from 'rxjs';
import { Task } from '../task.model';
import { WidgetDataService } from './widget-data.service';
import { WidgetErrorComponent } from './widget-error/widget-error.component';

@Component({
  selector: 'app-widget',
  standalone: true,
  imports: [MatIconModule, CommonModule, MatDividerModule, MatButtonModule, WidgetErrorComponent],
  templateUrl: './widget.component.html',
  styleUrls: ['./widget.component.scss']
})
export class WidgetComponent implements OnInit {

  tasks$!: Observable<Task[]>;
  error: Error | null = null;

  constructor(private widgetData: WidgetDataService) { }

  ngOnInit(): void {
    this.tasks$ = this.widgetData.load();
    // this.tasks$ = this.widgetData.load().subscribe({
    //   //  this wont work, because we have used async pipe in html file
    //   error: err => console.error(err)
    // });
    this.tasks$ = this.widgetData.load().pipe(
      // in this case we could use tap operator to provide the configuration object and we can define the callback incase of an error
      //  now we can see the widget with error
      /**
       * but we want to handle the error silently - 
       * we wanto send an empty array or object, so that UI can show some placeholder error message, for that we have to use the catch and replace strategy, and it boils down to the following things - 
       * inside the widget-data service file
       * and if we want to show both the error and UI placeholder, then in that case we will use catchError here like this
       */
      map(data => {
        console.warn('Data transformation');
        return data.map(data => data);
      }),
      tap({
        error: (error) => {
          // console.warn(error);
          this.error = error;
          console.warn(`Update component's 'error' property showing the error banner`);
        }
      }),
      catchError((err) => {
        console.warn(`Replacing the failed observable with an empty array`);
        return of([]);
      }) // if we comment this, then we can the error msg which is handled globally and we cannot see any UI placeholder
    );
  }

  addTask() {
    // unreliable method
    // this.widgetData.addTaskSync({ id: 0, title: 'New Task' });
    // ({} as any).someMethod(); // this is the custom error created
    /**
     * try catch doesnt works with asynchronous code
     */
    try {
      // this.widgetData.addTaskSync({ id: 0, title: 'New Task' }); // this is creating an error
      setTimeout(() => {
        // try/catch only works for synchronous code
        // but here, the error will be handled globally, but not by try/catch
        // in this case ngZone pushes the error to onError subscribe and it is handled their and change detection cycle will not run
        /**
         * asynchronous stuff that usually causes the change detection run,
         * inside this callback when we run outside of the angular,
         * those kinds of async events will be ignored and change detection,
         * cycle will not be scheduled.
         * So to fix the bug, we have to run the code inside our handle error method inside angular again and we can do so by going to the,
         * custom-error-handler service file and will inject NgZone
         */
        // in video, it was shown that it was working inside setTimeout, but from the other branch, setTimeout was not used
        // this.widgetData.addTaskSync({ id: 0, title: 'New Task' });
      });
      this.widgetData.addTaskSync({ id: 0, title: 'New Task' });
    } catch (error) {
      console.warn(error);
      if (error instanceof Error) {
        console.warn(error);
        this.error = error;
        /**
         * if we are using try catch and not throwing the error,
         * in that case error will be handled locally and it will not be handled by,
         * global error handler method.
         * but if we throw error like this - 
         * throw error; // code
         * exception will be propagated to next try catch block, which in our case,
         * is global try catch block used by angular internals and,
         * global catch operator will catch this error and will involve our custom,
         * global error handler and pass this exception to the handle error method.
         * Its like throwing the error from one catch to its parent, and continues,
         * to do so, until some catch operator will silently handle this exception,
         * or until it will be thrown away from the latest catch block and then it will become as an uncalled or 'uncaught' exception
         */
        throw error;
      }
    }
  }
}
