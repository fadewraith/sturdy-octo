import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  constructor(private apiService: ApiService) { }
  signupForm!: FormGroup;


  ngOnInit(): void {

    this.signupForm = new FormGroup({
      'firstName': new FormControl(null, Validators.required),
      'lastName': new FormControl(null, Validators.required),
      'password': new FormControl(null, Validators.required),
      'confirmPassword': new FormControl(null, Validators.required),
      'email': new FormControl(null, Validators.required),
      'mobile': new FormControl(null, Validators.required),
    });
  }

  onSubmit() {

    this.apiService.createUser(this.apiService.sign_up, this.signupForm.value).subscribe(response => {
      console.warn(response);
    });
    console.warn(this.signupForm);
    this.signupForm.reset();
  }

  // ngOnDestroy(): void {
  //     this.signupForm.unsubscribe();
  // }

}
