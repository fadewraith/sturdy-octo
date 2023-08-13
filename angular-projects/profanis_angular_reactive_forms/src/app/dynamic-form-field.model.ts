import { ValidatorFn } from "@angular/forms";

export interface DynamicFormFieldModel {
  id: string;
  type: 'text' | 'select';
  label: string;
  selectMenuOptions?: { [key: string]: string };
  value?: string;
//   isRequired?: boolean;
  validators?: ValidatorFn[]
}
