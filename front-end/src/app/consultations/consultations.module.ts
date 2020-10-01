import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultationsListComponent } from './consultations-list/consultations-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { ConsultationsInsertComponent } from './consultations-insert/consultations-insert.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConsultationsUpdateComponent } from './consultations-update/consultations-update.component';




@NgModule({
  declarations: [ConsultationsListComponent, ConsultationsInsertComponent, ConsultationsUpdateComponent],
  imports: [
    CommonModule,
    MDBBootstrapModule,
    FormsModule,
    ReactiveFormsModule
  ], exports: [ConsultationsListComponent, ConsultationsInsertComponent, ConsultationsUpdateComponent]
})
export class ConsultationsModule { }
