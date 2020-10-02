import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChiefdoctorService } from '../chiefdoctor.service';
import { UsersList } from '../users-list';



@Component({
  selector: 'app-pending-list',
  templateUrl: './pending-list.component.html',
  styleUrls: ['./pending-list.component.scss']
})

export class PendingListComponent implements OnInit {
  labelsTable = ['#','User', 'Pending Days'];
  constructor(private chiefdoctorService: ChiefdoctorService, private router: Router) { }
   pendingList: UsersList[];
  
 
  

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
