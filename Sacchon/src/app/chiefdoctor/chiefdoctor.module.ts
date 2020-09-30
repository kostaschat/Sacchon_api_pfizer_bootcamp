import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiefdoctorRoutingModule } from './chiefdoctor-routing.module';
import { DoctorsInactiveComponent } from './doctors-inactive/doctors-inactive.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PendingListComponent } from './pending-list/pending-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { InformationOfSubComponent } from './information-of-sub/information-of-sub.component';


@NgModule({
  declarations: [DoctorsInactiveComponent, PendingListComponent, InformationOfSubComponent, InformationOfSubComponent],
  imports: [
    CommonModule,
    MDBBootstrapModule,
    ChiefdoctorRoutingModule,
    ReactiveFormsModule
  ]
})
export class ChiefdoctorModule { }
