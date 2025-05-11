import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ChatResponse } from '../models/chat-response.model';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly apiUrl = 'http://localhost:8080/api/qna/ask';

  constructor(private http: HttpClient) {}

  fetchChatResponse(question: string): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(this.apiUrl, { question }).pipe(
      catchError((error) => {
        console.error(error);
        return throwError(() => error);
      })
    );
  }
}
