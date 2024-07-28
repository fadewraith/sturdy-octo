import { Component, Input, OnInit } from '@angular/core';
import { FieldConfig } from './types';

@Component({
  selector: 'app-ultimate-forms',
  templateUrl: './ultimate-forms.component.html',
  styleUrls: ['./ultimate-forms.component.css'],
})
export class UltimateFormsComponent implements OnInit {
  // @Input() fields!: string[];
  fieldValues: any = {};

  fieldConfigs: FieldConfig[] = [];

  @Input()
  set fields(newFields: (FieldConfig | string)[]) {
    this.fieldConfigs = newFields.map((field) => {
      if (typeof field === 'string') {
        return { name: field, displayName: field };
      } else {
        return field;
      }
    });

    for (let field of this.fieldConfigs) {
      this.fieldValues[field.name] = this.fieldValues[field.name] || '';
    }
  }

  constructor() {}

  ngOnInit(): void {}

  submitForm() {
    alert(
      Object.entries(this.fieldValues)
        .map((entry) => `${entry[0]}: ${entry[1]}`)
        .join('\n')
    );
  }

  capitalize(str: string): string {
    return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
  }
}
