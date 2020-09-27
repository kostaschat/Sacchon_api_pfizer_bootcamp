import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { PatientService } from '../patients.service';

@Component({
  selector: 'app-patient-insert-data',
  templateUrl: './patient-insert-data.component.html',
  styleUrls: ['./patient-insert-data.component.scss']
})
export class PatientInsertDataComponent implements OnInit {
  form: FormGroup;

  constructor(private patientService: PatientService) { 
  }

  ngOnInit() {
    this.form = new FormGroup({
      carb: new FormControl(null, [Validators.required]),
      glucose: new FormControl(null, Validators.required),
      measuredDate: new FormControl(null, Validators.required)
    });
  }

  
  // formSumbit(){
  //   this.patientService.addMedi(this.form).subscribe(data => {
  //     alert(JSON.stringify(data));
  //     this.ngOnInit();
  //   });
  // }
}

