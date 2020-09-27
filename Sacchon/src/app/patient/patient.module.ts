import { NgModule } from '@angular/core';


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
})
export class PatientModule { }
