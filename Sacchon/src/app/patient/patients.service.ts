import { Injectable } from '@angular/core';
import { HttpClient,HttpParams,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Patient } from './patient';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  getProducts(): Observable<Patient[]> {

    let headers = new HttpHeaders();
    headers = headers.append("Authorization", "Basic " + btoa('anestis'+':'+'anestis'));
    headers = headers.append("Content-Type", "application/x-www-form-urlencoded");


    //  let headers = new HttpHeaders().set('anestis','anestis');
    // let headers = new HttpHeaders({'Authorization':'Basic'+ btoa('anestis'+':'+'anestis')});
    return this.http.get<Patient[]>('http://localhost:9000/v1/medidata');
  }
}
