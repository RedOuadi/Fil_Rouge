import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardAdminComponent} from "./dashboard-admin/dashboard-admin.component";
import {UserManagementComponent} from "./user-management/user-management.component";
import {CoachManagementComponent} from "./coach-management/coach-management.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";



const routes: Routes = [
  { path: '', component: DashboardAdminComponent },
  { path: 'login',component:LoginComponent},
  { path: 'registre',component :RegisterComponent},

  { path: 'user-management', component: UserManagementComponent },
  { path: 'coach-management', component: CoachManagementComponent },


  { path: 'admin', component: DashboardAdminComponent, children: [
      { path: 'user-management', component: UserManagementComponent },
      { path: 'coach-management', component: CoachManagementComponent },
      { path: '**', redirectTo: 'admin' }
]},

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
