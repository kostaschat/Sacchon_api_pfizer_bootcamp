import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ChiefdoctorService } from '../chiefdoctor.service';
import { UsersList } from '../users-list';

@Component({
  selector: 'app-users-inactive',
  templateUrl: './users-inactive.component.html',
  styleUrls: ['.//users-inactive.component.scss']
})
export class UsersInactiveComponent implements OnInit {
  form: FormGroup;
  usersList: UsersList[];
  UserOptions = ['Doctor'];
  
  constructor(private chiefdoctorService : ChiefdoctorService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      fromDate: new FormControl(null, [Validators.required]),
      untilDate: new FormControl(null, [Validators.required]),
      Users :new FormControl('Patient')  
    });


  }
  formSumbit(){
    const data ={
      "fromDate": this.form.get('fromDate').value,
      "untilDate":this.form.get('untilDate').value,
      "Users":this.form.get('Users').value
      
      
      }
      console.log("o user einai " +data.Users);
      
  
    if(data.Users==='Doctor'){
      this.chiefdoctorService.getDoctorList(data.fromDate, data.untilDate).subscribe(medi => this.usersList = medi);
    }
    else if(data.Users==='Patient'){  
      this.chiefdoctorService.getPatientList(data.fromDate, data.untilDate).subscribe(medi => this.usersList = medi);
    }
  }
}