import { Component, OnInit } from '@angular/core';
import { DynamicFormFieldModel } from '../dynamic-form-field.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-dynamic-reactive-forms-demo',
  templateUrl: './dynamic-reactive-forms-demo.component.html',
  styleUrls: ['./dynamic-reactive-forms-demo.component.scss'],
})
export class DynamicReactiveFormsDemoComponent implements OnInit {
  dynamicFormFields!: DynamicFormFieldModel[];

  myForm!: FormGroup;

  constructor(private _fb: FormBuilder) {}

  ngOnInit(): void {
    // this.myForm = this._fb.group({
    //   dynamicField: [],
    // });
    this.myForm = this._fb.group({});

    this.dynamicFormFields = [
      {
        id: 'dynamicSelect',
        label: 'My Label Select',
        type: 'select',
        selectMenuOptions: {
          item1: 'Item 1',
          item2: 'Item 2',
          item3: 'Item 3',
        },
        value: 'item2',
      },
      {
        id: 'dynamicText',
        label: 'Email',
        type: 'text',
        value: '',
        // isRequired: true,
        validators: [Validators.required, Validators.email]
      },
      {
        id: 'dynamicText2',
        label: 'My Label Text 2',
        type: 'text',
        value: 'Angular2',
      },
    ];

    this.dynamicFormFields.forEach((formItem: any) => {
      // const formControl = this._fb.control(
      //   formItem.value,
      //   formItem.isRequired ? Validators.required : null
      // );
      const formControl = this._fb.control(
        formItem.value,
        formItem.validators
      );
      this.myForm.addControl(formItem.id, formControl);
    });
  }
}
