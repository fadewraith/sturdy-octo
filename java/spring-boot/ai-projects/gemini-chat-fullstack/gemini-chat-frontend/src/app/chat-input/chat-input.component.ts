import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-chat-input',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './chat-input.component.html',
  styleUrls: ['./chat-input.component.css']
})
export class ChatInputComponent {
  @Output() onSubmit: EventEmitter<string> = new EventEmitter<string>();
  questionForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.questionForm = this.fb.group({
      question: ['', [Validators.required, Validators.minLength(1)]]
    });
  }

  handleSubmit(): void {
    if (this.questionForm.valid) {
      const question = this.questionForm.get('question')?.value.trim();
      if (question) {
        this.onSubmit.emit(question);
        this.questionForm.reset();
      }
    }
  }
}
