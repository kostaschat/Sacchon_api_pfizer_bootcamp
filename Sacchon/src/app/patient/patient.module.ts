import { NgModule } from '@angular/core';


import { PatientRoutingModule } from './patient-routing.module';
import { PatientListComponent } from './patient-list/patient-list.component';
import { CommonModule } from '@angular/common';
import { PatientInsertDataComponent } from './patient-insert-data/patient-insert-data.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PatientConsultationComponent } from './patient-consultation/patient-consultation.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';


@NgModule({
  declarations: [PatientListComponent, PatientInsertDataComponent, PatientConsultationComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PatientRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],exports: [PatientListComponent,PatientRoutingModule]
})
export class PatientModule { }
