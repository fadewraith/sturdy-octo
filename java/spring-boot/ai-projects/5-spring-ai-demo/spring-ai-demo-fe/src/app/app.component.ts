import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageGeneratorComponent } from './image-generator/image-generator.component';
import { ChatComponent } from './chat/chat.component';
import { RecipeGeneratorComponent } from './recipe-generator/recipe-generator.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    ImageGeneratorComponent,
    ChatComponent,
    RecipeGeneratorComponent
  ],
  template: `
    <div class="app">
      <button
        [class.active]="activeTab === 'image-generator'"
        (click)="handleTabChange('image-generator')"
      >
        Image Generator
      </button>
      <button
        [class.active]="activeTab === 'chat'"
        (click)="handleTabChange('chat')"
      >
        Ask AI
      </button>
      <button
        [class.active]="activeTab === 'recipe-generator'"
        (click)="handleTabChange('recipe-generator')"
      >
        Recipe Generator
      </button>

      <div>
        <app-image-generator *ngIf="activeTab === 'image-generator'"></app-image-generator>
        <app-chat *ngIf="activeTab === 'chat'"></app-chat>
        <app-recipe-generator *ngIf="activeTab === 'recipe-generator'"></app-recipe-generator>
      </div>
    </div>
  `,
  styles: []
})
export class AppComponent {
  activeTab = 'image-generator';

  handleTabChange(tab: string) {
    this.activeTab = tab;
  }
}
