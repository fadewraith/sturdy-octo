import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormGroupDirective } from '@angular/forms';

@Component({
  selector: 'app-basic-info',
  templateUrl: './basic-info.component.html',
  styleUrls: ['./basic-info.component.scss']
})
export class BasicInfoComponent implements OnInit {

  @Input() formGroupName!: string

  form!: FormGroup;

  // this formgroupdirective is the instance of the parent userForm inside the split-reactive-form-to-child-c-demo.component.html file
  constructor(private _rootFormGroup: FormGroupDirective) { }

  ngOnInit(): void {
    this.form = this._rootFormGroup.control.get(this.formGroupName) as FormGroup;
  }

}
