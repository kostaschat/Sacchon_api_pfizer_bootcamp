import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PatientConsultationComponent } from './patient-consultation/patient-consultation.component';
import { MyPatientListComponent } from './my-patient-list/my-patient-list.component';
import { MediListComponent } from '../medi-data/medi-list/medi-list.component';
const routes: Routes = [
  //{ path: '', component: PatientListComponent}
    //{ path: '', component: PatientInsertDataComponent}
  //  { path: '/medi/list-data/:pid', component: MediListComponent}
  
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
