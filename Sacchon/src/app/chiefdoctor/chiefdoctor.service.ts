import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, Query } from '@angular/core';
import { Observable } from 'rxjs';
import { PatientsPendingList } from './pending-list/pending-list.component';
import { UsersList } from './users-list';


@Injectable({
  providedIn: 'root'
})
export class ChiefdoctorService {
  
  constructor(private http: HttpClient) { }

  readonly UrlDoctro = 'http://localhost:9000/v1/inactive-doctors/';
  readonly UrlPatient = 'http://localhost:9000/v1/patients/';
  readonly UrlPatientPending = 'http://localhost:9000/v1/patients/consultation-pending';


  getDoctorList(fromDate,untilDate): Observable<UsersList[]> {
    return this.http.get<UsersList[]>(
      this.UrlDoctro + fromDate + '/' +  untilDate,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

  getPatientList(fromDate,untilDate): Observable<UsersList[]> {
      
    console.log(this.UrlPatient + 'from?'+ fromDate + '/to?' +  untilDate)
  
  return this.http.get<UsersList[]>(
    this.UrlPatient + fromDate + '/' +  untilDate,
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );
}

getPendingList(){
  console.log("2 ok")
  return this.http.get<PatientsPendingList[]>(
    this.UrlPatientPending,
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );

}

}

