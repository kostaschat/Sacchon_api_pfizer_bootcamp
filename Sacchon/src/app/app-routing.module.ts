import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChiefdoctorModule } from './chiefdoctor/chiefdoctor.module';
import { DoctorsInactiveComponent } from './chiefdoctor/doctors-inactive/doctors-inactive.component';
import { InformationOfSubComponent } from './chiefdoctor/information-of-sub/information-of-sub.component';
import { PendingListComponent } from './chiefdoctor/pending-list/pending-list.component';
import { ConsultationsInsertComponent } from './consultations/consultations-insert/consultations-insert.component';
import { ConsultationsListComponent } from './consultations/consultations-list/consultations-list.component';
import { ConsultationsModule } from './consultations/consultations.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { MediInsertComponent } from './medi-data/medi-insert/medi-insert.component';
import { MediListComponent } from './medi-data/medi-list/medi-list.component';
import { MediUpdateComponent } from './medi-data/medi-update/medi-update.component';
import { AvailablePatientListComponent } from './patient/available-patient-list/available-patient-list.component';
import { MyPatientListComponent } from './patient/my-patient-list/my-patient-list.component';
import { PatientModule } from './patient/patient.module';
import { LoginComponent } from './users/login/login.component';
import { LogoutComponent } from './users/logout/logout.component';
import { RegisterComponent } from './users/register/register.component';
import { SettingsComponent } from './users/settings/settings.component';
import { UsersModule } from './users/users.module';

const routes: Routes = [


  {
    path: 'doctor/mypatients', component: MyPatientListComponent
  },
  {
    path: 'consultations', component: ConsultationsListComponent
  },
  {
    path: 'dashboard', component: DashboardComponent
  },
  {
    path: 'medi/insert', component: MediInsertComponent
  },
  {
    path: 'medi/list-data', component: MediListComponent
  },
  {
    path: 'medi/list-data/:pid', component: MediListComponent
  },
  {
    path: 'doctor/mypatients/insert-consultation', component: ConsultationsInsertComponent
  },
  {
    path: 'login', component: LoginComponent
  },
  {
    path: 'logout', component: LogoutComponent
  },
  {
    path: 'patients', component: AvailablePatientListComponent
  },
  {
    path: 'register', component: RegisterComponent
  },
  {
    path: 'settings', component: SettingsComponent
  },
  {
    path: 'chiefdoctor/inactive-doctors', component: DoctorsInactiveComponent
  },
  {
    path: 'edit', component: MediUpdateComponent
  },
  {
    path: 'chiefdoctor/pending-list', component: PendingListComponent
  },
  {
    path: 'chiefdoctor/submissions-list', component: InformationOfSubComponent
  }
  
   

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
  HttpClientModule, PatientModule, DashboardModule,UsersModule, ConsultationsModule,ChiefdoctorModule ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
