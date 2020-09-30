import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consultations } from './consultations';

@Injectable({
  providedIn: 'root'
})
export class ConsultationsService {

  readonly baseUrl = 'http://localhost:9000/v1/consultations';
  readonly url = 'http://localhost:9000/v1/add-consultation/';
  readonly urlDoctor = 'http://localhost:9000/v1/doctor/';
 
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
 
  getDoctrorConsultations(id, fromDate, untilDate): Observable<Consultations[]>{
   

    return this.http.get<Consultations[]>(
      this.urlDoctor  + id +  '/consultation/' + fromDate +'/' + untilDate,                
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      }
      );
  }

  addConsultation(values, id):Observable<any>{
    //location.reload();
    console.log(values.get('medicationName').value, values.get('dosage').value);
    return this.http.post(this.url+id,
      {
        "medicationName": values.get('medicationName').value,
        "dosage": values.get('dosage').value
      },
      {headers:new HttpHeaders(
        {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
        )
      })
    }





}
