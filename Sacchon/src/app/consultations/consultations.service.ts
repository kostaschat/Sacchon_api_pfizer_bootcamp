import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consultations } from './consultations';
import { Medi } from './medi';

@Injectable({
  providedIn: 'root'
})
export class ConsultationsService {

  readonly baseUrl = 'http://localhost:9000/v1/consultations';
  readonly url = 'http://localhost:9000/v1/add-consultation/';
  readonly mediUrl = 'http://localhost:9000/v1/medidata';
 
  constructor(private http: HttpClient){}


  getConsultations(): Observable<Consultations[]>{
    return this.http.get<Consultations[]>(
      this.baseUrl,
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


  // consultationMedi(id): Observable<Medi[]>{
  //   return this.http.get<Medi[]>(
  //     this.mediUrl+id, {
  //     headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials")) }
  //     )
  //   }
  //   );
  // }

  getConsultationMedi(): Observable<Medi[]>{
    return this.http.get<Medi[]>(
      this.mediUrl, {
      headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials")) }
      )
    }
    );
  }
}
