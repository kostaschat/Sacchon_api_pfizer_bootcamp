import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MediInsertComponent } from './medi-insert/medi-insert.component';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MediListComponent } from './medi-list/medi-list.component';
import { MediUpdateComponent } from './medi-update/medi-update.component';



@NgModule({
  declarations: [MediInsertComponent, MediListComponent, MediUpdateComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ],
  exports: [MediInsertComponent, MediListComponent]
})
export class MediDataModule { }
