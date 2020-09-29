import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultationsListComponent } from './consultations-list/consultations-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { ConsultationsInsertComponent } from './consultations-insert/consultations-insert.component';
import { AppRoutingModule } from '../app-routing.module';
import { Browser } from 'protractor';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';




@NgModule({
  declarations: [ConsultationsListComponent, ConsultationsInsertComponent],
  imports: [
    CommonModule,
    MDBBootstrapModule,
    FormsModule,
    ReactiveFormsModule
  ], exports: [ConsultationsListComponent, ConsultationsInsertComponent]
})
export class ConsultationsModule { }
