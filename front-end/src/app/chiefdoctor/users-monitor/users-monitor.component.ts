import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ChiefdoctorService } from '../chiefdoctor.service';
import { UsersList } from '../users-list';


@Component({
  selector: 'app-users-monitor',
  templateUrl: './users-monitor.component.html',
  styleUrls: ['./users-monitor.component.css']
})
export class MonitorUsersComponent implements OnInit {
  uri:any;
  data :string;
  usersList: UsersList[];
  labelsTable = ['#', 'Name','Last Name','Username','City','Birthday','Email','Phone number','CreationDate',' '];
  UserOptions = ['Doctor'];
  form: FormGroup;
  constructor(private chiefdoctorService: ChiefdoctorService, private router: Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      Users :new FormControl('Patient')  
    });

  }
  formSumbit(){
    this.data = this.form.get('Users').value    
    if(sessionStorage.getItem("credentials") == null ){
      this.router.navigate(['login'])
    }else{
      if(this.data==='Doctor'){
        this.chiefdoctorService.getDoctors().subscribe(usersList => this.usersList = usersList); 
      }else{
        this.chiefdoctorService.getPatients().subscribe(usersList => this.usersList = usersList); 
      }
    }
    console.log("o user einai " +this.form.get('Users').value);
    }

  onClick(id){

    if(this.data ==="Doctor")
    {
      this.uri='consultations'
    }else{
      this.uri='medi/list-data/'
    }
    this.router.navigate([this.uri], {queryParams:{id : id}}).then((e) => {
      if (e) {
        console.log("Navigation is successful!");
      } else {
        console.log("Navigation has failed!");
      }
    }); 

  }

}
