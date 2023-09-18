import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'lib-custom-library',
  template: `
    <p>
      custom-library works!
    </p>
  `,
  styles: [
  ]
})
export class CustomLibraryComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
