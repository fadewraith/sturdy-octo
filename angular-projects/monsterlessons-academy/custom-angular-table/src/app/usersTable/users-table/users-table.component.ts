import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { UsersService } from '../services/users.service';
import { SortingInterface } from '../types/sorting.interface';
import { UserInterface } from '../types/user.interface';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css']
})
export class UsersTableComponent implements OnInit {

  users: UserInterface[] = [];
  columns: Array<keyof UserInterface> = ['id', 'name', 'age'];
  sorting: SortingInterface = {
    column: 'id',
    order: 'asc',
  };
  searchValue: string = '';
  searchForm = this._fb.nonNullable.group({
    searchValue: ''
  })

  constructor(private _usersService: UsersService, private _fb: FormBuilder) { }

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    this._usersService.getUsers(this.sorting, this.searchValue).subscribe((users) => {
      this.users = users;
      console.warn(this.users);
    })
  }

  capitalize(str: string): string {
    return str.charAt(0).toUpperCase() + str.substring(1);
  }

  isDescSorting(column: string): boolean {
    return this.sorting.column === column && this.sorting.order === 'desc';
  }

  isAscSorting(column: string): boolean {
    return this.sorting.column === column && this.sorting.order === 'asc';
  }

  sortTable(column: string): void {
    const futureSortingOrder = this.isDescSorting(column) ? 'asc' : 'desc';
    this.sorting = {
      column,
      order: futureSortingOrder,
    };
    this.fetchData();
  }

  onSearchSubmit(): void {
    // console.warn(this.searchForm.value.searchValue);
    this.searchValue = this.searchForm.value.searchValue ?? '';
    this.fetchData();
  }

}
