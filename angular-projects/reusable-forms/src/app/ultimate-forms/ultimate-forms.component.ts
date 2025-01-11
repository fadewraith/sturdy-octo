import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { FieldConfig, Validator } from '../types';

const defaultFieldConfigValues = {
  type: 'text',
  placeholder: '',
  validators: <Validator[]>[],
};

@Component({
  selector: 'app-ultimate-forms',
  templateUrl: './ultimate-forms.component.html',
  styleUrls: ['./ultimate-forms.component.css'],
})
export class UltimateFormsComponent implements OnInit {
  validationErrors: string[] = [];
  // @Input() fields!: string[];
  fieldValues: any = {};

  fieldConfigs: FieldConfig[] = [];

  @Input()
  set fields(newFields: (FieldConfig | string)[]) {
    this.fieldConfigs = newFields.map((field) => {
      if (typeof field === 'string') {
        return {
          ...defaultFieldConfigValues,
          name: field,
          displayName: field,
        };
      } else {
        return {
          ...defaultFieldConfigValues,
          displayName: field.name,
          ...field,
        };
      }
    });

    for (let field of this.fieldConfigs) {
      this.fieldValues[field.name] = this.fieldValues[field.name] || '';
    }
  }

  constructor() {}

  @Output() submit = new EventEmitter<any>();

  ngOnInit(): void {}

  submitForm() {
    this.validationErrors = [];
    for (let field of this.fieldConfigs) {
      for (let validator of field.validators!) {
        const isValid = validator.checkFn(this.fieldValues[field.name]);
        if (!isValid) {
          this.validationErrors.push(
            `${field.displayName!}: ${validator.errorMessage}`
          );
          break;
        }
      }
    }
    if (this.validationErrors.length > 0) {
      return;
    }
    this.submit.emit(this.fieldValues);
  }

  capitalize(str: string): string {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }
}
