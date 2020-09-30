import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiefdoctorRoutingModule } from './chiefdoctor-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { PendingListComponent } from './pending-list/pending-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { UsersInactiveComponent } from './Users-inactive/users-inactive.component';
import { MonitorUsersComponent } from './users-monitor/users-monitor.component';




@NgModule({
  declarations: [UsersInactiveComponent, PendingListComponent, MonitorUsersComponent],
  imports: [
    CommonModule,
    MDBBootstrapModule,
    ChiefdoctorRoutingModule,
    ReactiveFormsModule
  ]
})
export class ChiefdoctorModule { }
