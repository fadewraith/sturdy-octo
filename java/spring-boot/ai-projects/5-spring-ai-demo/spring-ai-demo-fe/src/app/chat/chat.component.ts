import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div>
      <h2>Talk to AI</h2>
      <form [formGroup]="chatForm" (ngSubmit)="askAI()">
        <input
          type="text"
          formControlName="prompt"
          placeholder="Enter a prompt for AI"
        />
        <button type="submit">Ask AI</button>
      </form>
      <div class="output">
        <p>{{ chatResponse }}</p>
      </div>
    </div>
  `,
  styles: []
})
export class ChatComponent {
  chatForm: FormGroup;
  chatResponse: string = '';

  constructor(private fb: FormBuilder, private apiService: ApiService) {
    this.chatForm = this.fb.group({
      prompt: ['']
    });
  }

  askAI() {
    const prompt = this.chatForm.get('prompt')?.value;
    this.apiService.askAI(prompt).subscribe({
      next: (data: string) => {
        this.chatResponse = data;
        console.log(data);
      },
      error: (error) => console.error('Error generating response:', error)
    });
  }
}
