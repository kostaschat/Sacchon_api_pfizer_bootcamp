import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultationsListComponent } from './consultations-list/consultations-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';




@NgModule({
  declarations: [ConsultationsListComponent],
  imports: [
    CommonModule,
    MDBBootstrapModule
  ], exports: [ConsultationsListComponent]
})
export class ConsultationsModule { }
