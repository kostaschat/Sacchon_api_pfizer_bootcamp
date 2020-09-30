import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MediInsertComponent } from './medi-insert/medi-insert.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MediListComponent } from './medi-list/medi-list.component';
import { MediUpdateComponent } from './medi-update/medi-update.component';
import { MedidataChartsComponent } from './medidata-charts/medidata-charts.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';



@NgModule({
  declarations: [MediInsertComponent, MediListComponent, MediUpdateComponent, MedidataChartsComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    MDBBootstrapModule
  ],
  exports: [MediInsertComponent, MediListComponent, MedidataChartsComponent]
})
export class MediDataModule { }
