import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Consultations } from '../consultations';
import { ConsultationsService } from '../consultations.service';


@Component({
  selector: 'app-consultations-list',
  templateUrl: './consultations-list.component.html',
  styleUrls: ['./consultations-list.component.scss']
})
export class ConsultationsListComponent implements OnInit {

  labelsTable = ['#','Medication Name', 'Dosage', 'Consultation Date'];

  consultations: Consultations[];

  constructor(private consultationsService: ConsultationsService, private router: Router) { }

  ngOnInit(): void {
    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else{
      this.consultationsService.getConsultations().subscribe(consultations => this.consultations = consultations); 
    }
  }

}
