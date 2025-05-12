import { Component } from '@angular/core';
import { EmailReplyGeneratorComponent } from './email-reply-generator/email-reply-generator.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [EmailReplyGeneratorComponent],
  template: `
    <app-email-reply-generator></app-email-reply-generator>
  `
})
export class AppComponent { }
