import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GetUserDataComponent } from './get-user-data/get-user-data.component';
import { TdFormsComponent } from './td-forms/td-forms.component';

const routes: Routes = [
  // {path:'', redirectTo:'forms',pathMatch:'full'},
  {path:'', component: TdFormsComponent, data: {pageTitle: 'World'}},
  {path:'forms/:mobNum', component: TdFormsComponent, data: {pageTitle: 'World'}},
  // {path:'forms/:email', component: TdFormsComponent},
  {path:'user', component: GetUserDataComponent, data: {pageTitle: 'World'}},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
