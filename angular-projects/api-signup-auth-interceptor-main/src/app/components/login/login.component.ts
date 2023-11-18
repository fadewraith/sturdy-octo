import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private apiService: ApiService, private router: Router) { }
  signupForm!: FormGroup;

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      'email': new FormControl(null, Validators.required),
      'password': new FormControl(null, Validators.required),

    });
  }

  onSubmit() {
    this.apiService.createUser(this.apiService.log_in, this.signupForm.value).subscribe((response: any) => {
      console.warn(response);
      // localStorage.setItem('token', response.data.accessToken);
      this.router.navigate(['/userDashboard']);
    });
  }

}
