import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-parent',
  templateUrl: './parent.component.html',
  styleUrls: ['./parent.component.css']
})
export class ParentComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  reuse_comp = 'Reusable component';

  employee = [
    {
        name: 'John',
        designation: 'BA',
        company: 'Thrymr',
    },
    {
        name: 'Doe',
        designation: 'QA',
        company: 'Thrymr',
    },
    {
        name: 'Shawn',
        designation: 'HR',
        company: 'Thrymr',
    },
    {
        name: 'Mark',
        designation: 'Developer',
        company: 'Thrymr',
    }
]

}
