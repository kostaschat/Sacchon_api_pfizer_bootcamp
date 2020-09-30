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
  readonly url = 'http://localhost:9000/v1/all-medidata';
  readonly urlto = 'http://localhost:9000/v1/';


  getMedi(): Observable<MediData[]> {
    return this.http.get<MediData[]>(
      this.baseUrl,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

    
  getMediOfPatient(id,): Observable<MediData[]> {
    return this.http.get<MediData[]>(
      this.url +'/' + id,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }


  getMediOfPatientSub(id,fromDate,untilDate): Observable<MediData[]> {
    return this.http.get<MediData[]>(
      this.urlto + 'patient/' + id + '/medidata/' +fromDate  +'/' + untilDate,
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

  updateMediData(id,data): Observable<any>{
  
    return this.http.put(this.urlto +  'medidata/'+id, data,{headers : new  HttpHeaders(
      {'Authorization': 'Basic ' +  btoa(sessionStorage.getItem("credentials"))}
      )});

  }

  getMediToUpdate(id):Observable <MediData> {
    return this.http.get<MediData>(
      this.urlto +  'medidata/'+id,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

}
