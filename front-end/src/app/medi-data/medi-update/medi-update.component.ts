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
  id :any;
  
  ngOnInit(): void {

    this.formUpdate = new FormGroup({
      carb: new FormControl(null, [Validators.required]),
      glucose: new FormControl(null, Validators.required)      
    }); 

    this.id = this.route.snapshot.queryParamMap.get("id")

    console.log("to id einai " +this.id)


    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else{
     this.mediService.getMediToUpdate(this.id).subscribe(medi => { this.mediData = medi 
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
    this.mediService.updateMediData(this.id,data).subscribe(data => { alert("You successfully updated your Medical Data"); this.router.navigate(['medi/list-data'])});
  }

}
