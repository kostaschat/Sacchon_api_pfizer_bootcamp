import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MediData } from '../medi-data';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-list',
  templateUrl: './medi-list.component.html',
  styleUrls: ['./medi-list.component.scss']
})
export class MediListComponent implements OnInit {
  public sessionStorage = sessionStorage;  
  mediData: MediData[];
  id: any;
  form: FormGroup;

  constructor(private mediService: MediDataService,private router:Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    
    this.id = this.route.snapshot.queryParamMap.get("id") 
    
    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else{
      if(sessionStorage.getItem("role")== "chiefDoctor")
      {
          this.form = new FormGroup({
          fromDate: new FormControl(null, [Validators.required]),
          untilDate: new FormControl(null, [Validators.required])  
      });      
      }
      else{
        if(this.id){
     
          this.mediService.getMediOfPatient(this.id).subscribe(medi => this.mediData = medi);
        }else{
          this.mediService.getMedi().subscribe(medi => this.mediData = medi); 
        }
    
      }
      }
  }

  formSumbit(){
    const data ={
        "fromDate": this.form.get('fromDate').value,
        "untilDate":this.form.get('untilDate').value
        } 
           this.mediService.getMediOfPatientSub(this.id, data.fromDate, data.untilDate).subscribe(mediData => this.mediData = mediData); 
}

  onClickUpdate(url, uri){
    this.router.navigate([url], {queryParams:{uri : uri}}).then( (e) => {
      if (e) {
        console.log("Navigation is successful!");
      } else {
        console.log("Navigation has failed!");
      }
    }); 
  }
}









    










