import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PatientConsultationComponent } from './patient-consultation/patient-consultation.component';
import { PatientInsertDataComponent } from './patient-insert-data/patient-insert-data.component';
import { PatientListComponent } from './patient-list/patient-list.component';
const routes: Routes = [
  //{ path: '', component: PatientListComponent}
    //{ path: '', component: PatientInsertDataComponent}
    { path: '', component: PatientConsultationComponent}
  
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PatientRoutingModule { }
