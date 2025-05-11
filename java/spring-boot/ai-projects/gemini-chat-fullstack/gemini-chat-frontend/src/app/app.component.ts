import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatInputComponent } from './chat-input/chat-input.component';
import { ChatResponseComponent } from './chat-response/chat-response.component';
import { ApiService } from './services/api.service';
import { ChatResponse } from './models/chat-response.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ChatInputComponent, ChatResponseComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  response: ChatResponse | null = null;
  loading: boolean = false;

  constructor(private apiService: ApiService) {}

  handleQuestionSubmit(question: string): void {
    this.loading = true;
    this.response = null;
    this.apiService.fetchChatResponse(question).subscribe({
      next: (apiResponse) => {
        this.response = apiResponse;
        this.loading = false;
      },
      error: () => {
        alert('Failed to get response');
        this.loading = false;
      }
    });
  }
}
