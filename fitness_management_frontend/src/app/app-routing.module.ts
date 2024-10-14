import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardAdminComponent } from "./dashboard-admin/dashboard-admin.component";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { UserListComponent } from "./Users/user-list/user-list.component";
import { UserCreateComponent } from "./Users/user-create/user-create.component";
import { CoachListComponent } from "./Coaches/coach-list/coach-list.component";
import { HeaderComponent } from "./header/header.component";
import { DashboardUserComponent } from "./dashboard-user/dashboard-user.component";
import { UserUpdateComponent } from "./Users/user-update/user-update.component";
import { DashboardCoachComponent } from './dashboard-coach/dashboard-coach.component';
import { ProgrammeListComponent } from "./Programmes/programme-list/programme-list.component";
import { ExerciceListComponent } from "./Programmes/Exercices/exercice-list/exercice-list.component";
import { ProgrammeCreateComponent } from "./Programmes/programme-create/programme-create.component";  // Import ProgrammeCreateComponent
import { ProgrammeUpdateComponent } from "./Programmes/programme-update/programme-update.component";  // Import ProgrammeUpdateComponent
import { ExerciceCreateComponent } from './Programmes/exercices/exercice-create/exercice-create.component';
import {ExerciceUpdateComponent} from "./Programmes/Exercices/exercice-update/exercice-update.component";

const routes: Routes = [
  { path: '', component: LoginComponent  },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard-admin', component: DashboardAdminComponent },
  { path: 'registre', component: RegisterComponent },
  { path: 'dashboard-coach', component: DashboardCoachComponent },
  { path: 'dashboard-user', component: DashboardUserComponent },
  { path: 'user-list', component: UserListComponent },
  { path: 'user-update/:id', component: UserUpdateComponent },
  { path: 'user-create', component: UserCreateComponent },
  { path: 'header', component: HeaderComponent },
  { path: 'exercice-list', component: ExerciceListComponent },
  { path: 'exercice-list/:programId', component: ExerciceListComponent },
  // This can be used for listing exercises based on programId
  { path: 'exercice-create/:programmeId', component: ExerciceCreateComponent }, // Route for creating exercise
  { path: 'exercice-update/:id', component: ExerciceUpdateComponent },

  {
    path: 'admin',
    component: DashboardAdminComponent,
    children: [
      { path: 'user-list', component: UserListComponent },
      { path: 'user-create', component: UserCreateComponent },
      { path: 'user-update/:id', component: UserUpdateComponent },
      { path: 'coach-list', component: CoachListComponent },
      { path: '', redirectTo: 'user-list', pathMatch: 'full' },
      { path: '**', redirectTo: 'user-list' }
    ]
  },

  {
    path: 'coach',
    component: DashboardCoachComponent,
    children: [
      { path: 'programme-list', component: ProgrammeListComponent },
      { path: 'programme-create', component: ProgrammeCreateComponent },  // Add create route
      { path: 'programme-update/:id', component: ProgrammeUpdateComponent },  // Add update route
      { path: 'exercice-list', component: ExerciceListComponent },
      { path: 'exercice-list/:programId', component: ExerciceListComponent },
       // This can be used for listing exercises based on programId
      { path: 'exercice-create/:programmeId', component: ExerciceCreateComponent }, // Route for creating exercise
      { path: 'exercice-update/:id', component: ExerciceUpdateComponent }, //
      { path: '', redirectTo: 'programme-list', pathMatch: 'full' },
      { path: '**', redirectTo: 'programme-list' }
    ]
  },

  {
    path: 'user',
    component: DashboardUserComponent,
    children: []
  },

  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
