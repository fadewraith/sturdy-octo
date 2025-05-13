import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-recipe-generator',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div>
      <h2>Create a Recipe</h2>
      <form [formGroup]="recipeForm" (ngSubmit)="createRecipe()">
        <input
          type="text"
          formControlName="ingredients"
          placeholder="Enter ingredients (comma separated)"
        />
        <input
          type="text"
          formControlName="cuisine"
          placeholder="Enter cuisine type"
        />
        <input
          type="text"
          formControlName="dietaryRestrictions"
          placeholder="Enter dietary restrictions"
        />
        <button type="submit">Create Recipe</button>
      </form>
      <div class="output">
        <pre class="recipe-text">{{ recipe }}</pre>
      </div>
    </div>
  `,
  styles: []
})
export class RecipeGeneratorComponent {
  recipeForm: FormGroup;
  recipe: string = '';

  constructor(private fb: FormBuilder, private apiService: ApiService) {
    this.recipeForm = this.fb.group({
      ingredients: [''],
      cuisine: ['any'],
      dietaryRestrictions: ['']
    });
  }

  createRecipe() {
    const { ingredients, cuisine, dietaryRestrictions } = this.recipeForm.value;
    this.apiService.createRecipe(ingredients, cuisine, dietaryRestrictions).subscribe({
      next: (data: string) => {
        this.recipe = data;
        console.log(data);
      },
      error: (error) => console.error('Error generating recipe:', error)
    });
  }
}
