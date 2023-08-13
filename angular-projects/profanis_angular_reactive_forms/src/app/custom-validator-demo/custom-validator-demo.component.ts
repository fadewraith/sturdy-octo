import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';

@Component({
  selector: 'app-custom-validator-demo',
  templateUrl: './custom-validator-demo.component.html',
  styleUrls: ['./custom-validator-demo.component.scss']
})
export class CustomValidatorDemoComponent implements OnInit {

  myForm!: FormGroup;

  constructor(private _fb: FormBuilder) { }

  ngOnInit(): void {
    this.myForm = this._fb.group({
      password: this._fb.control(null),
      confirmPassword: this._fb.control(null),
    }, {
      validators: this.controlValuesAreEqual('password', 'confirmPassword')
    });
  }

  private controlValuesAreEqual(controlNameA: string, controlNameB: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const formGroup = control as FormGroup;
      const valueOfControlA = formGroup.get(controlNameA)?.value;
      const valueOfControlB = formGroup.get(controlNameB)?.value;

      if(valueOfControlA === valueOfControlB) {
        return null;
      } else {
        return { valuesDoNotMatch: true }
      }
    }
  }

}
