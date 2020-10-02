import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public sessionStorage = sessionStorage;
  
  constructor(private router:Router,private http: HttpClient) { }

  readonly baseUrl = 'http://localhost:9000/v1/error-modify';

  ngOnInit(): void {
  }

  onClickUpdate(){
    sessionStorage.setItem('modified', 'false')
    return this.http.put(this.baseUrl,null,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }).subscribe(error => console.log(error));
  }

}
