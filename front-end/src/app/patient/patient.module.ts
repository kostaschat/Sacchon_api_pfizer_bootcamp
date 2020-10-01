import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AvailablePatientListComponent } from './available-patient-list/available-patient-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MyPatientListComponent } from './my-patient-list/my-patient-list.component';
import { BrowserModule } from '@angular/platform-browser';



@NgModule({
  declarations: [AvailablePatientListComponent,MyPatientListComponent],
  imports: [
    CommonModule,
    BrowserModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [AvailablePatientListComponent, MyPatientListComponent]
})
export class PatientModule { }
