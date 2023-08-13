import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { UsersService } from './services/users.service';
import { UsersTableComponent } from './users-table/users-table.component';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule],
  declarations: [UsersTableComponent],
  exports: [UsersTableComponent],
  providers: [UsersService],
})
export class UsersTableModule {}
