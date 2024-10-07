import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DashboardAdminComponent} from "./dashboard-admin/dashboard-admin.component";
import {UserManagementComponent} from "./user-management/user-management.component";
import {CoachManagementComponent} from "./coach-management/coach-management.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {UserListComponent} from "./Users/user-list/user-list.component";
import {UserCreateComponent} from "./Users/user-create/user-create.component";
import {CoachListComponent} from "./Coaches/coach-list/coach-list.component";
import {HeaderComponent} from "./header/header.component";
import {DashboardUserComponent} from "./dashboard-user/dashboard-user.component";



const routes: Routes = [

  { path: '',component:LoginComponent},
  { path: 'dash', component: DashboardAdminComponent },
  { path: 'registre',component :RegisterComponent},
  { path: 'dashboard-user',component:DashboardUserComponent  },


  { path: 'user-management', component: UserManagementComponent },
  { path: 'coach-management', component: CoachManagementComponent },
  { path: 'header', component: HeaderComponent },


  { path: 'admin', component: DashboardAdminComponent, children: [
      { path: 'user-list', component: UserListComponent },
      { path: 'user-create', component: UserCreateComponent },

      { path: 'coach-list', component: CoachListComponent },
      { path: '**', redirectTo: 'admin' }
]},

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
