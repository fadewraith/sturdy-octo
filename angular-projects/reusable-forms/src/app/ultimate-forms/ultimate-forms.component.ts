import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-ultimate-forms',
  templateUrl: './ultimate-forms.component.html',
  styleUrls: ['./ultimate-forms.component.css'],
})
export class UltimateFormsComponent implements OnInit {
  @Input() fields!: string[];
  fieldValues: any = {};

  constructor() {}

  ngOnInit(): void {
    for (let field of this.fields) {
      this.fieldValues[field] = '';
    }
  }

  submitForm() {
    alert(
      Object.entries(this.fieldValues)
        .map((entry) => `${entry[0]}: ${entry[1]}`)
        .join('\n')
    );
  }
}
