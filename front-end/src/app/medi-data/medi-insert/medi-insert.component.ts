import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-insert',
  templateUrl: './medi-insert.component.html',
  styleUrls: ['./medi-insert.component.scss']
})
export class MediInsertComponent implements OnInit {
  res: any;
  form: FormGroup;
  
  constructor(private mediDataService: MediDataService,private router: Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      carb: new FormControl(null, [Validators.required]),
      glucose: new FormControl(null, Validators.required),
      measuredDate: new FormControl(null, Validators.required)
    });
  }

  formSumbit(){
    this.mediDataService.addMedi(this.form).subscribe(
      (response) => {
        this.res = response;
        console.log(this.res)

        if (this.res != null){
          alert("Medical Data entered successfully! ")
          this.router.navigate(['medi/list-data'])
         }
         else {
          alert("Please wait a doctor to consult your previous Medical Data, before adding a new one");
        }
      } 
    )
    
  }
}
