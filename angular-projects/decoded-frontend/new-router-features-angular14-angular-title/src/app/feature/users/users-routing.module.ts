import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserTitleResolverService } from './user-title-resolver.service';
import { UserComponent } from './user/user.component';
import { UsersContainerComponent } from './users-container/users-container.component';

const routes: Routes = [
  { path: '', component: UsersContainerComponent },
  // step 7 - use the name of the resolver - UserTitleResolverService
  { path: ':id', component: UserComponent, title: UserTitleResolverService },
  // { path: ':id', component: UserComponent, title: `User:id` } // in this way in Angular 14, we can set the title, but its hard coded

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
