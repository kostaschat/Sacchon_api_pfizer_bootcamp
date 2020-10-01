import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MediData } from '../medi-data';

import { MediDataService } from '../medi-data.service';


@Component({
  selector: 'app-average-data',
  templateUrl: './average-data.component.html',
  styleUrls: ['./average-data.component.scss']
})
export class AverageDataComponent implements OnInit {
  form: FormGroup;
  averageGlucose: MediData
  averageCarb: MediData 



  isShow = false;
  carbType : string
  glucoseType :string
  
  
  constructor(private mediDataService : MediDataService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      fromDate: new FormControl(null, [Validators.required]),
      untilDate: new FormControl(null, [Validators.required])
    });
    this.isShow = !this.isShow;
  
  }
  

  formSumbit(){
    this.isShow = !this.isShow;
    const data ={
      "fromDate": this.form.get('fromDate').value,
      "untilDate":this.form.get('untilDate').value  
    }
    this.isShow = this.isShow;
    this.carbType="carb"
    this.glucoseType="glucose"
    
     this.mediDataService.getAverageData(this.carbType,data.fromDate, data.untilDate).subscribe(averageCarb => this.averageCarb = averageCarb);
     this.mediDataService.getAverageData(this.glucoseType,data.fromDate, data.untilDate).subscribe(averageGlucose => this.averageGlucose = averageGlucose);      
      
  }

}