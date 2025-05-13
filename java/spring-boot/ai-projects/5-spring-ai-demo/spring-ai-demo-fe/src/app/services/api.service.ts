import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  generateImage(prompt: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/generate-image?prompt=${encodeURIComponent(prompt)}`);
  }

  askAI(prompt: string): Observable<string> {
    return this.http.get(`${this.baseUrl}/ask-ai?prompt=${encodeURIComponent(prompt)}`, { responseType: 'text' });
  }

  createRecipe(ingredients: string, cuisine: string, dietaryRestrictions: string): Observable<string> {
    return this.http.get(
      `${this.baseUrl}/recipe-creator?ingredients=${encodeURIComponent(ingredients)}&dietaryRestrictions=${encodeURIComponent(dietaryRestrictions)}&cuisine=${encodeURIComponent(cuisine)}`,
      { responseType: 'text' }
    );
  }
}
