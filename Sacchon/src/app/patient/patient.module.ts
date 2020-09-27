import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AvailablePatientListComponent } from './available-patient-list/available-patient-list.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [AvailablePatientListComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [AvailablePatientListComponent]
})
export class PatientModule { }
