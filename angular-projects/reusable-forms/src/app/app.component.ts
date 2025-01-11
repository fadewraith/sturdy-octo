import { Component } from '@angular/core';
import { FieldConfig } from './types';
import {
  createMinLengthValidator,
  createMinValueValidator,
  isNotEmptyValidator,
  emailRegex,
  createRegexValidator,
  dateRegex,
  timeRegex,
} from './validators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'reusable-forms';

  passwordFieldConfig: FieldConfig = {
    name: 'password',
    type: 'password',
    placeholder: 'Must have at least 8 characters',
  };
  confirmPasswordFieldConfig: FieldConfig = {
    name: 'confirmPassword',
    type: 'password',
    placeholder: 'Must match password',
  };

  formFields: FieldConfig[] = [
    {
      name: 'name',
      validators: [isNotEmptyValidator, createMinLengthValidator(2)],
    },
    {
      name: 'email',
      validators: [
        isNotEmptyValidator,
        createRegexValidator(emailRegex, 'email address'),
      ],
    },
    {
      name: 'age',
      type: 'number',
      validators: [isNotEmptyValidator, createMinValueValidator(21)],
    },
    {
      name: 'birthday',
      placeholder: 'DD/MM/YYYY',
      validators: [
        isNotEmptyValidator,
        createRegexValidator(dateRegex, 'date in DD/MM/YYYY'),
      ],
    },
    {
      name: 'time',
      placeholder: '23:52',
      validators: [
        isNotEmptyValidator,
        createRegexValidator(timeRegex, 'time in HH:MM format'),
      ],
    },
    {
      name: 'password',
      type: 'password',
      placeholder: 'Must have at least 8 characters',
    },
    {
      name: 'bio',
      displayName: 'Write a short biography',
      placeholder: 'i.e. I like food',
    },
    {
      name: 'favoriteColor',
      displayName: 'Favorite Color',
    },
  ];

  handleFormSubmit({ name, email, age, password, bio, favoriteColor }: any) {
    alert(
      `Name: ${name}\nEmail: ${email}\nAge: ${age}\nPassword: ${password}\nBio: ${bio}\nFavorite Color: ${favoriteColor}`
    );
  }
}
