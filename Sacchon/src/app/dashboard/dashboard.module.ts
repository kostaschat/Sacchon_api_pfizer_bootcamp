import { NgModule } from '@angular/core';


import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';


@NgModule({
  declarations: [DashboardComponent],
  imports: [
    DashboardRoutingModule
  ], 
  exports: [DashboardRoutingModule]
})
export class DashboardModule { }
