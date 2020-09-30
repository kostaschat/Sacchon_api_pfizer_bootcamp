import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChiefdoctorService } from '../chiefdoctor.service';


export interface PatientsPendingList {
  username: string;
  pendignDays : number;
}
@Component({
  selector: 'app-pending-list',
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.css']
})

export class PendingListComponent implements OnInit {
  labelsTable = ['#','User Name', 'Pending Days'];
  constructor(private chiefdoctorService: ChiefdoctorService, private router: Router) { }
   pendingList: PatientsPendingList[];
  
 
  

  ngOnInit(): void {
    if(sessionStorage.getItem("credentials") == null ){
      this.router.navigate(['login'])
    }else{
      console.log("1 ok")
     this.chiefdoctorService.getPendingList().subscribe(pendingList => this.pendingList = pendingList); 

     console.log("3 ok")
    }
   


  }

}
