import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { UsersRoutingModule } from './users-routing.module';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { SettingsComponent } from './settings/settings.component';


@NgModule({
  declarations: [RegisterComponent, LoginComponent, LogoutComponent, SettingsComponent],
  imports: [
    CommonModule,
    UsersRoutingModule,
    ReactiveFormsModule
  ], exports:[UsersRoutingModule]
})
export class UsersModule { }
