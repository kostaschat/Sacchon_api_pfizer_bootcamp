import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MediInsertComponent } from './medi-insert/medi-insert.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MediListComponent } from './medi-list/medi-list.component';



@NgModule({
  declarations: [MediInsertComponent, MediListComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [MediInsertComponent, MediListComponent]
})
export class MediDataModule { }
