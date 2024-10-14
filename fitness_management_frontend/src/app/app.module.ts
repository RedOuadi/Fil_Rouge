import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardAdminComponent } from './dashboard-admin/dashboard-admin.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import { UserListComponent } from './Users/user-list/user-list.component';
import { UserCreateComponent } from './Users/user-create/user-create.component';
import { UserUpdateComponent } from './Users/user-update/user-update.component';
import { CoachListComponent } from './Coaches/coach-list/coach-list.component';
import { CoachCreateComponent } from './Coaches/coach-create/coach-create.component';
import { CoachUpdateComponent } from './Coaches/coach-update/coach-update.component';
import { HeaderComponent } from './header/header.component';
import { DashboardUserComponent } from './dashboard-user/dashboard-user.component';
import { DashboardCoachComponent } from './dashboard-coach/dashboard-coach.component';
import { ProgrammeListComponent } from './Programmes/programme-list/programme-list.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatCardModule} from "@angular/material/card";
import {MatChipsModule} from "@angular/material/chips";
import {MatIconModule} from "@angular/material/icon";
import {ExerciceListComponent} from "./Programmes/Exercices/exercice-list/exercice-list.component";
import {MatButtonModule} from "@angular/material/button";
import { ProgrammeCreateComponent } from './Programmes/programme-create/programme-create.component';
import { ProgrammeUpdateComponent } from './Programmes/programme-update/programme-update.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import { ExerciceCreateComponent } from './Programmes/exercices/exercice-create/exercice-create.component';
import {ExerciceUpdateComponent} from "./Programmes/Exercices/exercice-update/exercice-update.component";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import { ObjectifListComponent } from './objectifs/objectif-list/objectif-list.component';
import { ObjectifCreateComponent } from './objectifs/objectif-create/objectif-create.component';
import { ObjectifUpdateComponent } from './objectifs/objectif-update/objectif-update.component';
import { ActiviteListComponent } from './Activities/activite-list/activite-list.component';
import { ActiviteCreateComponent } from './Activities/activite-create/activite-create.component';
import { ActiviteUpdateComponent } from './Activities/activite-update/activite-update.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardAdminComponent,

    LoginComponent,
    RegisterComponent,
    UserListComponent,
    UserCreateComponent,
    UserUpdateComponent,
    CoachListComponent,
    CoachCreateComponent,
    CoachUpdateComponent,
    HeaderComponent,
    DashboardUserComponent,
    DashboardCoachComponent,
    ProgrammeListComponent,

    ExerciceListComponent,
      ProgrammeCreateComponent,
      ProgrammeUpdateComponent,
      ExerciceCreateComponent,
      ExerciceUpdateComponent,
      ObjectifListComponent,
      ObjectifCreateComponent,
      ObjectifUpdateComponent,
      ActiviteListComponent,
      ActiviteCreateComponent,
      ActiviteUpdateComponent,
      HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    MatProgressBarModule,
    MatCardModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
