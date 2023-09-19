import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  AsyncValidatorFn,
  FormBuilder,
  FormControl,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Observable, delay, map, of } from 'rxjs';

@Component({
  selector: 'app-async-validator-form',
  templateUrl: './async-validator-form.component.html',
  styleUrls: ['./async-validator-form.component.scss'],
})
export class AsyncValidatorFormComponent implements OnInit {
  username!: FormControl;

  private readonly usernames = ['john', 'jane'];

  constructor(private _fb: FormBuilder) {}

  ngOnInit(): void {
    this.username = this._fb.control(null, Validators.required, this.userValidator());
  }

  private userExists(username: string): Observable<boolean> {
    return of(this.usernames.includes(username)).pipe(delay(2000));
  }

  private userValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return this.userExists(control.value).pipe(
        map((response) => (response ? { userExists: true } : null))
      );
    };
  }
}
