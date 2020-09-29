import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Patient } from '../patient';
import { PatientService } from '../patient.service';



@Component({
  selector: 'app-my-patient-list',
  templateUrl: './my-patient-list.component.html',
  styleUrls: ['./my-patient-list.component.scss']
})

export class MyPatientListComponent implements OnInit {

  patients: Patient[];
  checkedTheUnconsulted: boolean;

  constructor(private patientService: PatientService, private router: Router) { }
  ngOnInit(): void {

    if (sessionStorage.getItem("credentials") == null) {
      this.router.navigate(['login'])
    } else {
      this.patientService.getMyPatients().subscribe(p => this.patients = p);
    }

  }

  showUncolsutedPatients(values:any){
    if(values.currentTarget.checked){
      console.log("is it checked" + values.currentTarget.checked)
      this.patientService.getMyUnconsultedPatients().subscribe(p => {this.patients = p; this.checkedTheUnconsulted = true; console.log(this.patients)});
    }else {
      this.patientService.getMyPatients().subscribe(p => {this.patients = p; this.checkedTheUnconsulted = false});
    }
  }

  gotoMediList(url, id){
   
    this.router.navigate([url], {queryParams:{id : id}}).then( (e) => {
      if (e) {
        console.log("Navigation is successful!");
      } else {
        console.log("Navigation has failed!");
      }
    });
  }

  addConsultation(url, pid){
    console.log(pid);
    this.router.navigate([url], {queryParams:{id : pid}}).then( (e) => {
      if (e) {
        console.log("Navigation is successful!");
      } else {
        console.log("Navigation has failed!");
      }
    });
  }


}
