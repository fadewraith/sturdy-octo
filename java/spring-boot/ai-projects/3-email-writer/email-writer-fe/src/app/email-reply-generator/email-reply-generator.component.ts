import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { EmailService } from './email.service';
import { catchError, finalize } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-email-reply-generator',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatCardModule
  ],
  templateUrl: './email-reply-generator.component.html',
  styleUrls: ['./email-reply-generator.component.scss']
})
export class EmailReplyGeneratorComponent implements OnInit {
  emailForm: FormGroup;
  generatedReply: string = '';
  loading: boolean = false;
  error: string = '';

  constructor(
    private fb: FormBuilder,
    private emailService: EmailService
  ) {
    this.emailForm = this.fb.group({
      emailContent: ['', Validators.required],
      tone: ['']
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.emailForm.invalid) return;

    this.loading = true;
    this.error = '';

    const { emailContent, tone } = this.emailForm.value;

    this.emailService.generateEmailReply({ emailContent, tone })
      .pipe(
        catchError(err => {
          this.error = 'Failed to generate email reply. Please try again';
          console.error(err);
          return throwError(err);
        }),
        finalize(() => this.loading = false)
      )
      .subscribe(response => {
        this.generatedReply = typeof response === 'string' ? response : JSON.stringify(response);
      });
  }

  copyToClipboard(): void {
    navigator.clipboard.writeText(this.generatedReply);
  }
}
