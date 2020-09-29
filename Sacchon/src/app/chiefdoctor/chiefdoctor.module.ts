import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiefdoctorRoutingModule } from './chiefdoctor-routing.module';
import { DoctorsInactiveComponent } from './doctors-inactive/doctors-inactive.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PendingListComponent } from './pending-list/pending-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';


@NgModule({
  declarations: [DoctorsInactiveComponent, PendingListComponent],
  imports: [
    CommonModule,
    MDBBootstrapModule,
    ChiefdoctorRoutingModule,
    ReactiveFormsModule
  ]
})
export class ChiefdoctorModule { }
