import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consultations } from './consultations';

@Injectable({
  providedIn: 'root'
})
export class ConsultationsService {

  readonly baseUrl = "http://localhost:9000/v1/consultations";
 
  constructor(private http: HttpClient)
   {

   }


  getConsultations(): Observable<Consultations[]>{
    return this.http.get<Consultations[]>(
      this.baseUrl,
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }



}
