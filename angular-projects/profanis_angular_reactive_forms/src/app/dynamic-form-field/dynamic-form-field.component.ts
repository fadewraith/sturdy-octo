import { Component, Input, OnInit } from '@angular/core';
import { DynamicFormFieldModel } from '../dynamic-form-field.model';
import { FormGroup, FormGroupDirective } from '@angular/forms';

@Component({
  selector: 'app-dynamic-form-field',
  templateUrl: './dynamic-form-field.component.html',
  styleUrls: ['./dynamic-form-field.component.scss']
})
export class DynamicFormFieldComponent implements OnInit {

  @Input() formItem!: DynamicFormFieldModel;
  // @Input() form!: FormGroup;
  form!: FormGroup;

  constructor(private _rootFormGroup: FormGroupDirective) {
    this.form = this._rootFormGroup.control;
   }

  ngOnInit(): void {
  }

}
