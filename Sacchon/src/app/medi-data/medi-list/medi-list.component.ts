import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MediData } from '../medi-data';
import { MediDataService } from '../medi-data.service';

@Component({
  selector: 'app-medi-list',
  templateUrl: './medi-list.component.html',
  styleUrls: ['./medi-list.component.scss']
})
export class MediListComponent implements OnInit {

  mediData: MediData[];
  id: any;
  public sessionStorage = sessionStorage;

  carbs: number[] = [];
  glucose:number[] = [];
  dates: string[] = [];

  labelsTable = ['#','Blood Glucose Level', 'Carb Intake', 'Date & Time'];

  constructor(private mediService: MediDataService,private router:Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    console.log("kalispera");
    this.id = this.route.snapshot.queryParamMap.get("id")

    if(sessionStorage.getItem("credentials") == null){
      this.router.navigate(['login'])
    }else if(this.id){
      console.log("exw id");
      this.mediService.getMediOfPatient(this.id).subscribe(medi => {this.mediData = medi, this.fillData(this.mediData)});
    }else{
      this.mediService.getMedi().subscribe(medi => {this.mediData = medi, this.fillData(this.mediData)});
    }
  }

  fillData(mediData: MediData[]){

    const datePipe = new DatePipe('en-US');
    mediData.forEach((value) => {
      this.carbs.push(value.carb),
      this.glucose.push(value.glucose),
      this.dates.push(datePipe.transform(value.measuredDate, 'EEEE, MMMM d'));
      })
  }

  deleteMedidata(medi_id){

    this.mediService.removeMedi(medi_id).subscribe();
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
