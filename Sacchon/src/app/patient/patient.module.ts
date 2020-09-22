import { NgModule } from '@angular/core';


import { PatientRoutingModule } from './patient-routing.module';
import { PatientListComponent } from './patient-list/patient-list.component';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [PatientListComponent],
  imports: [
    CommonModule,

    PatientRoutingModule
  ],exports: [PatientListComponent]
})
export class PatientModule { }
