import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  private apiUrl = 'http://localhost:8080/api/transcribe';

  constructor(private http: HttpClient) {}

  uploadAudio(file: File): Observable<string> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<string>(this.apiUrl, formData).pipe(
      catchError(error => {
        console.error('Error transcribing audio', error);
        throw error;
      })
    );
  }
}
