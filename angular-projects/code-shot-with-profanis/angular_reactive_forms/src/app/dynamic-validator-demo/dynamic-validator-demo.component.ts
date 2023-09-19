import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-dynamic-validator-demo',
  templateUrl: './dynamic-validator-demo.component.html',
  styleUrls: ['./dynamic-validator-demo.component.scss']
})
export class DynamicValidatorDemoComponent implements OnInit {

  myForm!: FormGroup;

  constructor(private _fb: FormBuilder) { }

  ngOnInit(): void {
    this.myForm = this._fb.group({
      isUseful: this._fb.control(null, Validators.required),
      // the reason field should be required once the user selects other dynamicall
      reason: this._fb.control(null),
    });

    /**
   * if(value of fieldA === 'something) {
   *    setValidator on fieldB
   * } else {
   *    clearValidator on fieldB
   * }
   * 
   * recalculate the value and validity of fieldB
   */

    const isUsefulField = this.myForm.get('isUseful');
    const reasonField = this.myForm.get('reason');

    isUsefulField?.valueChanges.subscribe((value: any) => {
      if(value === '3') {
        reasonField?.setValidators(Validators.required);
      } else {
        reasonField?.clearValidators();
      }

      reasonField?.updateValueAndValidity();
    })
  }



}
