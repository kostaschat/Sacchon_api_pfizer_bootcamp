import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsersList } from './users-list';


@Injectable({
  providedIn: 'root'
})
export class ChiefdoctorService {
  constructor(private http: HttpClient) { }
  readonly Url = 'http://localhost:9000/v1/';

  getDoctorList(fromDate,untilDate): Observable<UsersList[]> {

    this.Url + 'inactive-doctors/'
    return this.http.get<UsersList[]>(
      this.Url + 'inactive-doctors/' + fromDate + '/' +  untilDate,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }
  getPatientList(fromDate,untilDate): Observable<UsersList[]> { 
  return this.http.get<UsersList[]>(
    this.Url + 'patients/'+ fromDate + '/' +  untilDate,
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );
}

getPendingList() : Observable <UsersList[]> {
  return this.http.get<UsersList[]>(
    this.Url + 'patients/consultation-pending',
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );
}

getDoctors() : Observable <UsersList[]> {
  return this.http.get<UsersList[]>(
    this.Url + 'inactive-doctors',
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );

}
getPatients(): Observable <UsersList[]> { 
  return this.http.get<UsersList[]>(
    this.Url + 'my-patients/',
    {headers:new HttpHeaders(
      {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
      )
    }
    );
}
}

