import { NgModule } from '@angular/core';
<<<<<<< HEAD
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
=======


import { PatientRoutingModule } from './patient-routing.module';
import { MyPatientListComponent } from './my-patient-list/my-patient-list.component';
import { CommonModule } from '@angular/common';
import { PatientInsertDataComponent } from './patient-insert-data/patient-insert-data.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PatientConsultationComponent } from './patient-consultation/patient-consultation.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';


@NgModule({
  declarations: [MyPatientListComponent, PatientInsertDataComponent, PatientConsultationComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule, 
    PatientRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],exports: [MyPatientListComponent,PatientRoutingModule]
>>>>>>> a84bab13510646f76efa0c752e3bec19b4964d6c
})
export class PatientModule { }
