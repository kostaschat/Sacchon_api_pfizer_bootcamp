import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MediData } from './medi-data';

@Injectable({
  providedIn: 'root'
})
export class MediDataService {

  constructor(private http: HttpClient) { }

  readonly baseUrl = 'http://localhost:9000/v1/medidata';


  getMedi(): Observable<MediData[]> {
    return this.http.get<MediData[]>(
      this.baseUrl,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }


  addMedi(values):Observable<any>{
    return this.http.post(this.baseUrl,{
      'carb': values.get('carb').value,
      'glucose':values.get('glucose').value,
      'measuredDate':values.get('measuredDate').value
    },{headers:new HttpHeaders({'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))})});
  } 

}
