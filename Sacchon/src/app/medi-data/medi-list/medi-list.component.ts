import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MediData } from '../medi-data';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-list',
  templateUrl: './medi-list.component.html',
  styleUrls: ['./medi-list.component.css']
})
export class MediListComponent implements OnInit {

  mediData: MediData[];

  constructor(private mediService: MediDataService,private router:Router) { }

  ngOnInit(): void {
    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else{
      this.mediService.getMedi().subscribe(medi => this.mediData = medi); 
    }
  }

  onClickUpdate(event, medi){
      console.log(medi);
  }
}
