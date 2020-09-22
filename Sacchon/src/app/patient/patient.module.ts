import { NgModule } from '@angular/core';


import { PatientRoutingModule } from './patient-routing.module';
import { PatientListComponent } from './patient-list/patient-list.component';


@NgModule({
  declarations: [PatientListComponent],
  imports: [

    PatientRoutingModule
  ]
})
export class PatientModule { }
