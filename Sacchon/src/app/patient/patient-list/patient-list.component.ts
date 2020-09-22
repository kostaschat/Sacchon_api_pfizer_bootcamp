import { Component, OnInit } from '@angular/core';
import { Patient } from '../patient';
import { PatientService } from '../patients.service';


@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss']
})
export class PatientListComponent implements OnInit {
  patients: Patient[];
  constructor(private patientService: PatientService ) { }
  ngOnInit(): void {
    this.patientService.getProducts().subscribe(patients => this.patients = patients);
    }
   

}
