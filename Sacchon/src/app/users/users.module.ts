import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { UsersRoutingModule } from './users-routing.module';
import { RegisterComponent } from './register/register.component';


@NgModule({
  declarations: [RegisterComponent],
  imports: [
    CommonModule,
    UsersRoutingModule,ReactiveFormsModule
  ], exports:[UsersRoutingModule]
})
export class UsersModule { }
