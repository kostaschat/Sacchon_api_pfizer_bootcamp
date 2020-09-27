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
import { ConsultationsListComponent } from './consultations/consultations-list/consultations-list.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
<<<<<<< HEAD
import { PatientModule } from './patient/patient.module';

=======
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
>>>>>>> a84bab13510646f76efa0c752e3bec19b4964d6c



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
<<<<<<< HEAD
    PatientModule,
=======
    ReactiveFormsModule,
    FormsModule,
>>>>>>> a84bab13510646f76efa0c752e3bec19b4964d6c
    MDBBootstrapModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
