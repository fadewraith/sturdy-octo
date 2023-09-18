import { ErrorHandler, Injectable, NgZone } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

// @Injectable({
//   providedIn: 'root'
// })
@Injectable()
// export class CustomErrorHandlerService implements ErrorHandler {
export class CustomErrorHandler implements ErrorHandler {

  constructor(private _snackbar: MatSnackBar, private _zone: NgZone) { }
  // we will use type as unknown rather than any, because it is more safe than any
  // unknown will force us to narrow the type before it can be used like did in widget component ts file, checking the instance of error
  handleError(error: unknown): void {
    this._zone.run(() => {
      this._snackbar.open(
        'Error was detected!',
        'Close',
        {
          duration: 2000
        }
      );
    });
    // this._snackbar.open(
    //   'Error was detected!',
    //   'Close',
    //   {
    //     duration: 2000
    //   }
    // );
    console.warn(`Caught by custom error handler: `,error);
  }
}
