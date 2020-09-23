import { NgModule } from '@angular/core';


import { PatientRoutingModule } from './patient-routing.module';
import { PatientListComponent } from './patient-list/patient-list.component';
import { CommonModule } from '@angular/common';
import { PatientInsertDataComponent } from './patient-insert-data/patient-insert-data.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [PatientListComponent, PatientInsertDataComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PatientRoutingModule
  ],exports: [PatientListComponent]
})
export class PatientModule { }
