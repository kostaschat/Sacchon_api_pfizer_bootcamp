import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { PatientModule } from './patient/patient.module';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';


const routes: Routes = [ 
  { path: '', component: HomeComponent },
   { path: 'some', component: SomeComponent }];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    PatientModule,
    HttpClientModule,
    RouterModule.forRoot(routes)

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
