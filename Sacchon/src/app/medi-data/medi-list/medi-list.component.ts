import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MediData } from '../medi-data';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-list',
  templateUrl: './medi-list.component.html',
  styleUrls: ['./medi-list.component.css']
})
export class MediListComponent implements OnInit {

  mediData: MediData[];
  id: any;

  constructor(private mediService: MediDataService,private router:Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    console.log("kalispera");
    this.id = this.route.snapshot.queryParamMap.get("id")
    
    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else if(this.id){
      console.log("exw id");
      this.mediService.getMediOfPatient(this.id).subscribe(medi => this.mediData = medi);
    }else{
      this.mediService.getMedi().subscribe(medi => this.mediData = medi); 
    }
  }

  onClickUpdate(event, medi){
      console.log(medi);
  }
}
