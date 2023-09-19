import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-split-reactive-form-to-child-c-demo',
  templateUrl: './split-reactive-form-to-child-c-demo.component.html',
  styleUrls: ['./split-reactive-form-to-child-c-demo.component.scss']
})
export class SplitReactiveFormToChildCDemoComponent implements OnInit {

  userForm!: FormGroup;

  constructor(private _fb: FormBuilder) { }

  ngOnInit(): void {
    this.userForm = this._fb.group({
      basicInfo: this._fb.group({
        firstName: [],
        lastName: [],
        email: [],
        age: []
      }),
      address: this._fb.group({
        street: [],
        number: [],
        postal: [],
        company: []
      })
    }); 
  }

}
