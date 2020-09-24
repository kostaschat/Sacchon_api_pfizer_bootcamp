import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardModule } from './dashboard/dashboard.module';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { PatientConsultationComponent } from './patient/patient-consultation/patient-consultation.component';
import { PatientInsertDataComponent } from './patient/patient-insert-data/patient-insert-data.component';
import { PatientListComponent } from './patient/patient-list/patient-list.component';
import { PatientModule } from './patient/patient.module';

const routes: Routes = [
  {
    path: 'patient/consultation', component: PatientConsultationComponent
  },
  {
    path: 'dashboard', component: DashboardComponent
  },
  {
    path: 'patient/insert', component: PatientInsertDataComponent
  },
  {
    path: 'patient/list-data', component: PatientListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
  HttpClientModule, PatientModule, DashboardModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
