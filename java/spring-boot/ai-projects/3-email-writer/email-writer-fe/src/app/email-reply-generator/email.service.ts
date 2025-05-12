import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface EmailRequest {
  emailContent: string;
  tone: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  private apiUrl = 'http://localhost:8080/api/email/generate';

  constructor(private http: HttpClient) {}

  generateEmailReply(request: EmailRequest): Observable<any> {
    return this.http.post(this.apiUrl, request, { responseType: 'text' });
  }
}
