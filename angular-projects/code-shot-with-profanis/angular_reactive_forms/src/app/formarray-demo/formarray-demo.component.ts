import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-formarray-demo',
  templateUrl: './formarray-demo.component.html',
  styleUrls: ['./formarray-demo.component.scss']
})
export class FormarrayDemoComponent implements OnInit {

  myForm!: FormGroup;

  get colors(): FormArray {
    return this.myForm.get('colors') as FormArray;
  }

  constructor(private _fb: FormBuilder) { }

  ngOnInit(): void {
    // so it will be like - myForm.colors[index]
    this.myForm = this._fb.group({
      colors: this._fb.array([
        // so here in this case, the name will be index inside the html, because its the nested form groups
        this._fb.group({name: 'red'}),
        this._fb.group({name: 'green'}),
        this._fb.group({name: 'blue'}),
      ])
    });
  }


  removeColor(index: number) {
    this.colors.removeAt(index);
  }

  addColor() {
    this.colors.push(this._fb.group({name: ""}));
  }

  

}
