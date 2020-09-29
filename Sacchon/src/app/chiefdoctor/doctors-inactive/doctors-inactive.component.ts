import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefdoctorService } from '../chiefdoctor.service';
import { UsersList } from '../users-list';

@Component({
  selector: 'app-doctors-inactive',
  templateUrl: './doctors-inactive.component.html',
  styleUrls: ['./doctors-inactive.component.scss']
})
export class DoctorsInactiveComponent implements OnInit {
  form: FormGroup;
  usersList: UsersList[];
  UserOptions = ['Doctor'];
  



  constructor(private chiefdoctorService : ChiefdoctorService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      fromDate: new FormControl(null, [Validators.required]),
      untilDate: new FormControl(null, [Validators.required]),
      Users :new FormControl(null, [Validators.required])
      
      
    });

  }
  formSumbit(){

    console.log(this.form.value)
    const data ={
      "fromDate": this.form.get('fromDate').value,
      "untilDate":this.form.get('untilDate').value,
      "Users":this.form.get('Users').value
      }

   
    if(data.Users==='Doctor'){
      this.chiefdoctorService.getDoctorList(data.fromDate, data.untilDate).subscribe(medi => this.usersList = medi);
    }
    else{
      this.chiefdoctorService.getPatientList(data.fromDate, data.untilDate).subscribe(medi => this.usersList = medi);
    }
  }
}