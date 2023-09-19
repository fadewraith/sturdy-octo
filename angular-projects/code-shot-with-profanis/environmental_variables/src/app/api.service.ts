import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private endpoint = 'users';
  private domain: string | undefined; // s1

  constructor(private httpClient: HttpClient) {
    this.domain = environment.domain; // s2
  }

  get() {
    return this.httpClient.get(`${this.domain}${this.endpoint}`);
  }
}
