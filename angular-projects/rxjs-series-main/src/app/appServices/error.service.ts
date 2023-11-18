import { HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { throwError } from 'rxjs';

@Injectable({
    providedIn: 'root'
})

export class ErrorService {

    constructor() { }

    handleError(err: HttpErrorResponse) {
        console.warn(err);

        let errMsg = '';

        if (!err.error || !err.error.error) {
            console.warn('network error');
            errMsg = 'unknown error'
        } else {
            if (err.error.error == 'Permission Denied') {
                errMsg = 'you dont have permission to acecess this content'
            }
        }

        // throwerror is operator and we will use this so that any component can use this service and throw the error
        // the above handle error method will throw the error acc to the condition
        return throwError(errMsg);
    }


}