import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AvailablePatientListComponent } from './available-patient-list/available-patient-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyPatientListComponent } from './my-patient-list/my-patient-list.component';



@NgModule({
  declarations: [AvailablePatientListComponent,MyPatientListComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule 
  ],
  exports: [AvailablePatientListComponent, MyPatientListComponent]
})
export class PatientModule { }
