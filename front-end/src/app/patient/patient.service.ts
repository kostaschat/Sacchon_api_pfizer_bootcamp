import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from './patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  readonly baseUrl = 'http://localhost:9000/v1/patients';
  readonly consultUrl = 'http://localhost:9000/v1/consult-patient/';
  readonly mypatientsUrl = 'http://localhost:9000/v1/my-patients';
  readonly myunconsultedpatientsUrl = 'http://localhost:9000/v1/patients/no-consultation';

  getAvailable(): Observable<Patient[]> {
    return this.http.get<Patient[]>(
      this.baseUrl,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

  consultPatient(id){
    location.reload();
    return this.http.put(this.consultUrl+id,null,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      })
    }
    
    getMyPatients(): Observable<Patient[]> {
      return this.http.get<Patient[]>(
        this.mypatientsUrl, {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials")) }
        )
      }
      );
    }

    getMyUnconsultedPatients(): Observable<Patient[]> {
      return this.http.get<Patient[]>(
        this.myunconsultedpatientsUrl, {
        headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials")) }
        )
      }
      );
    }
}
