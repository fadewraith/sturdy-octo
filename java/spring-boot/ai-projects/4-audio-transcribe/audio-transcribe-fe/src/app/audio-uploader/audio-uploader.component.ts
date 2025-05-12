import { Component } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { HttpClientService } from './http-client.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-audio-uploader',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="container">
      <h1>Audio to Text Transcriber</h1>
      <form [formGroup]="audioForm" class="file-input">
        <input type="file" accept="audio/*" formControlName="audioFile" (change)="onFileChange($event)" />
      </form>
      <button class="upload-button" (click)="onUpload()">Upload and Transcribe</button>
      <div class="transcription-result">
        <h2>Transcription Result</h2>
        <p>{{ transcription }}</p>
      </div>
    </div>
  `,
  styleUrls: ['./audio-uploader.component.scss']
})
export class AudioUploaderComponent {
  audioForm: FormGroup;
  transcription: string = '';

  constructor(private fb: FormBuilder, private httpService: HttpClientService) {
    this.audioForm = this.fb.group({
      audioFile: [null]
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.audioForm.patchValue({ audioFile: input.files[0] });
    }
  }

  onUpload(): void {
    const file = this.audioForm.get('audioFile')?.value;
    if (file) {
      this.httpService.uploadAudio(file).subscribe({
        next: (response) => {
          this.transcription = response;
        },
        error: (error) => {
          console.error('Error transcribing audio', error);
        }
      });
    }
  }
}
