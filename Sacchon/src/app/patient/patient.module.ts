import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PatientRoutingModule } from './patient-routing.module';
import { PatientListComponent } from './patient-list/patient-list.component';


@NgModule({
  declarations: [PatientListComponent],
  imports: [
    CommonModule,
    PatientRoutingModule
  ], exports:[PatientRoutingModule]
})
export class PatientModule { }
