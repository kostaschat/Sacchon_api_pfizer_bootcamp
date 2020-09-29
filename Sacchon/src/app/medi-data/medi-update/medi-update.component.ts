import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {  ActivatedRoute, Router } from '@angular/router';
import { MediData } from '../medi-data';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-update',
  templateUrl: './medi-update.component.html',
  styleUrls: ['./medi-update.component.scss'],
 
})
 
export class MediUpdateComponent implements OnInit {
  formUpdate :FormGroup;
  
  constructor(private mediService: MediDataService, private router : Router, private route: ActivatedRoute) { }

  mediData: MediData;
  uri :any;
  
  ngOnInit(): void {

    this.formUpdate = new FormGroup({
      carb: new FormControl(null, [Validators.required]),
      glucose: new FormControl(null, Validators.required)      
    }); 

    this.uri = this.route.snapshot.queryParamMap.get("uri")

    console.log("to uri einai " +this.uri)


    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else{
     this.mediService.getMediToUpdate(this.uri).subscribe(medi => { this.mediData = medi 
      this.fillData() } );
    }
  }
  fillData(){
     this.formUpdate.patchValue(this.mediData)
  }

  

  formSubmit(){
    const data ={
    "carb": this.formUpdate.get('carb').value,
    "glucose":this.formUpdate.get('glucose').value
    }
    console.log(data)
    this.mediService.updateMediData(this.uri,data).subscribe(data => { alert(JSON.stringify(data)); this.ngOnInit();    });
  }

}
