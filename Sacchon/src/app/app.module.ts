import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSliderModule } from '@angular/material/slider';
import {MatTableModule} from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { NavComponent } from './nav/nav.component';
import { MediDataModule } from './medi-data/medi-data.module';
import { ConsultationsModule } from './consultations/consultations.module';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { PatientModule } from './patient/patient.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChiefdoctorModule } from './chiefdoctor/chiefdoctor.module';



@NgModule({
  declarations: [
    AppComponent,
    NavComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    MatSliderModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MediDataModule,
    ConsultationsModule,
    PatientModule,
    ReactiveFormsModule,
    FormsModule, 
    ChiefdoctorModule, 
    MDBBootstrapModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
