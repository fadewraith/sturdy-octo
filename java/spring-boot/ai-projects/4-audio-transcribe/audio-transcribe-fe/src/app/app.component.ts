import { Component } from '@angular/core';
import { AudioUploaderComponent } from './audio-uploader/audio-uploader.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [AudioUploaderComponent],
  template: `
    <app-audio-uploader></app-audio-uploader>
  `,
  styles: []
})
export class AppComponent {}
