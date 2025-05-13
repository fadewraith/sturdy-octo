import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-image-generator',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="tab-content">
      <h2>Generate Image</h2>
      <form [formGroup]="imageForm" (ngSubmit)="generateImage()">
        <input
          type="text"
          formControlName="prompt"
          placeholder="Enter prompt for image"
        />
        <button type="submit">Generate Image</button>
      </form>

      <div class="image-grid">
        <img
          *ngFor="let url of imageUrls; let i = index"
          [src]="url"
          [alt]="'Generated ' + i"
        />
        <div
          *ngFor="let _ of emptySlots"
          class="empty-image-slot"
        ></div>
      </div>
    </div>
  `,
  styles: []
})
export class ImageGeneratorComponent {
  imageForm: FormGroup;
  imageUrls: string[] = [];
  emptySlots: any[] = Array(4).fill(null);

  constructor(private fb: FormBuilder, private apiService: ApiService) {
    this.imageForm = this.fb.group({
      prompt: ['']
    });
  }

  generateImage() {
    const prompt = this.imageForm.get('prompt')?.value;
    this.apiService.generateImage(prompt).subscribe({
      next: (urls: string[]) => {
        this.imageUrls = urls;
        this.emptySlots = Array(4 - this.imageUrls.length).fill(null);
        console.log(urls);
      },
      error: (error) => console.error('Error generating image:', error)
    });
  }
}
