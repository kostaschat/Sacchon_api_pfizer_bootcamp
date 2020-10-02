import { HttpClient, HttpHeaders } from '@angular/common/http';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';

@Component({
  selector: 'app-available-patient-list',
  templateUrl: './available-patient-list.component.html',
  styleUrls: ['./available-patient-list.component.scss']
})
export class AvailablePatientListComponent implements OnInit {

  patients: Patient[];

  constructor(private patientService: PatientService, private router:Router,private http: HttpClient) { }

 

  ngOnInit(): void {
    if(sessionStorage.getItem("credentials") == null
    || sessionStorage.getItem("role") != "doctor"){
      this.router.navigate(['login'])
    }else{
      this.patientService.getAvailable().subscribe(patient => this.patients = patient); 
    }
  }

  onClickConsult(event, id){
    this.patientService.consultPatient(id).subscribe
    (data => console.log('success', data),
    error => console.log(error));
    this.ngOnInit();
  }
}
