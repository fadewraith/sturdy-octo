import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, catchError, retry, throwError, timer } from 'rxjs';

@Injectable()
export class GlobalHttpErrorHandlerInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      // retry({
      //   count: 3,
      //   delay: 1000
      // })
      // progressive retry strategy
      // then this error will be propagated to the widget-data service and then it will rethrown from there and caught again by the catch error operator in the widget component ts file
      retry({
        count: 3,
        delay: (_, retryCount) => timer(retryCount * 1000), // there will delay of increasing seconds
      }),
      catchError(err => {
        console.warn('Error handled by HTTP interceptor...');
        return throwError(() => {
          console.warn('Error rethrown by HTTP interceptor...');
          return err;
        });
      })
    );
  }
}
