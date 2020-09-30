import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Consultations } from '../consultations';
import { ConsultationsService } from '../consultations.service';

@Component({
  selector: 'app-consultations-list',
  templateUrl: './consultations-list.component.html',
  styleUrls: ['./consultations-list.component.scss']
})
export class ConsultationsListComponent implements OnInit {
  public sessionStorage = sessionStorage;  
  form: FormGroup;
  labelsTable = [' ','Medication Name', 'Dosage', 'Consultation Date',' '];
  id: any;
 // consultations: Consultations[];
  consultations: Consultations[]= [{
    medicationName : 'kosda', dosage : 123,consultationDate: '2020-1-3',doctor_id: 2,patient_id : 2, uri:'http://localhost:9000/v1/consultation/1'  
  }];

  consultationId:any;
  constructor(private consultationsService: ConsultationsService, private router: Router,  private route: ActivatedRoute) { }

  ngOnInit(): void {

    this.id = this.route.snapshot.queryParamMap.get("id")

    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }
    else{
      if(sessionStorage.getItem("role")== "chiefDoctor" )
      {
          this.form = new FormGroup({
          fromDate: new FormControl(null, [Validators.required]),
          untilDate: new FormControl(null, [Validators.required])  
        });      

      }else{
       // this.consultationsService.getConsultations().subscribe(consultations => this.consultations = consultations); 
      }
      
    }
  }

  formSumbit(){
      const data ={
          "fromDate": this.form.get('fromDate').value,
          "untilDate":this.form.get('untilDate').value
          } 
             this.consultationsService.getDoctrorConsultations(this.id, data.fromDate, data.untilDate).subscribe(consultations => this.consultations = consultations); 
  }

  onClickEdit(uri){

    this.consultationId = uri.split(['/']).pop()
    console.log("teliko  uri " + this.consultationId)
    console.log("teliko  uri " + this.consultationId)

    this.router.navigate(['doctor/consultation/edit'], {queryParams:{id : this.consultationId}}).then( (e) => {
      if (e) {
        console.log("Navigation is successful!");
      } else {
        console.log("Navigation has failed!");
      }
    }); 
  }
  
}




