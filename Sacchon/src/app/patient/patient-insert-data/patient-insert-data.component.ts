import { HttpClient } from '@angular/common/http';
import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-patient-insert-data',
  templateUrl: './patient-insert-data.component.html',
  styleUrls: ['./patient-insert-data.component.css']
})
export class PatientInsertDataComponent implements OnInit {
  form: FormGroup;

 private readonly url ='  ';
  

  constructor(private http: HttpClient) { 
  }

  ngOnInit() {
    this.form = new FormGroup({
      carb: new FormControl(null, [Validators.required]),
      glucose: new FormControl(null, Validators.required)
      
    });


  }
  formSumbit(){
    console.log(this.form.value)
    this.http.post(this.url,this.form.value)

  }



}

