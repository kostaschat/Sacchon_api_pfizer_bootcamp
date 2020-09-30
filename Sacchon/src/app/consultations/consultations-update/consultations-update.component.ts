import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Consultations } from '../consultations';
import { ConsultationsService } from '../consultations.service';

@Component({
  selector: 'app-consultations-update',
  templateUrl: './consultations-update.component.html',
  styleUrls: ['./consultations-update.component.css']
})
export class ConsultationsUpdateComponent implements OnInit {
  formUpdate :FormGroup;
  constructor(private consultationsService: ConsultationsService, private router : Router, private route: ActivatedRoute) { }
  consultationData : Consultations
  id :any;

  ngOnInit(): void {
    this.formUpdate = new FormGroup({
      medicationName: new FormControl(null, [Validators.required]),
      dosage: new FormControl(null, Validators.required)      
    }); 
    this.id = this.route.snapshot.queryParamMap.get("id")
    console.log("to id tou consul einai" +this.id)


    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else{
     this.consultationsService.getConsultationToUpdate(this.id).subscribe(consultationData => { this.consultationData = consultationData 
      this.fillData() } );
    }
  }
  fillData(){
    this.formUpdate.patchValue(this.consultationData)
 }

 formSubmit(){
  const data ={
  "medicationName": this.formUpdate.get('medicationName').value,
  "dosage":this.formUpdate.get('dosage').value
  }
  console.log(data) 
  console.log(this.id)

  this.consultationsService.updateConsultation(this.id,data).subscribe(data => { alert(JSON.stringify(data)); this.ngOnInit();    });
}

}
