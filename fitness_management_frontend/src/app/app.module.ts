import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardAdminComponent } from './dashboard-admin/dashboard-admin.component';
import { UserManagementComponent } from './user-management/user-management.component';
import { CoachManagementComponent } from './coach-management/coach-management.component';
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

@NgModule({
  declarations: [
    AppComponent,
    DashboardAdminComponent,
    UserManagementComponent,
    CoachManagementComponent,
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
    DashboardCoachComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
