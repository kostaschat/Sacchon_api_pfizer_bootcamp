import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChiefdoctorRoutingModule } from './chiefdoctor-routing.module';
import { DoctorsInactiveComponent } from './doctors-inactive/doctors-inactive.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [DoctorsInactiveComponent],
  imports: [
    CommonModule,
    ChiefdoctorRoutingModule,
    ReactiveFormsModule
  ]
})
export class ChiefdoctorModule { }
