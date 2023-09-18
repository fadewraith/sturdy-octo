import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-display-form-error-messages',
  templateUrl: './display-form-error-messages.component.html',
  styleUrls: ['./display-form-error-messages.component.scss']
})
export class DisplayFormErrorMessagesComponent implements OnInit {

  form !: FormGroup;

  constructor(private _fb: FormBuilder) { }

  ngOnInit(): void {
    // this means that this formcontrol will gather the value, only when the user clicks the submit button and not on every key stroke which is the default option, thats why updateOn is used
    this.form = this._fb.group({
      username: this._fb.control('', Validators.required),
      password: this._fb.control('', Validators.required),
    }, {updateOn: 'submit'});
  }

  submitForm() {
    console.warn(this.form.value);
  }

}
