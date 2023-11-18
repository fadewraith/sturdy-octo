import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http'
import { environment } from "src/environments/environment";
// import { Subject } from "rxjs";

@Injectable({ providedIn: 'root' })
export class ApiService {

  constructor(private http: HttpClient) { }
  // error = new Subject<string>();
  sign_up = '/api/v1/authentication/signup';
  // log_in = '/api/v1/authentication/login';
  log_in = '/api/v1/authentication/get-all-recruiters-list';

  createUser (url: string, data: any) {
    return this.http.post(environment.base_url + url, data);
  }

  login () {

  }

}
