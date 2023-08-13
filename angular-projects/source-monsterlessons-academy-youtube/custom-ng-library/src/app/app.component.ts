import { Component, inject, OnInit } from '@angular/core';
import { CustomLibraryService } from 'custom-library';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  // usersService: any;
  usersService = inject(CustomLibraryService);
  constructor() {
    // console.warn(this.usersService.getUsers());
    // this.usersService = Inject(CustomLibraryService);
  }

  ngOnInit(): void {
    this.usersService.getUsers().subscribe((res: any) => console.log(res));
  }
}
