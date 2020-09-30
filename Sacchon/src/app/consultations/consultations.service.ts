import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MediData } from '../medi-data/medi-data';
import { Consultations } from './consultations';

@Injectable({
  providedIn: 'root'
})
export class ConsultationsService {

  readonly baseUrl = 'http://localhost:9000/v1/consultations';
  readonly url = 'http://localhost:9000/v1/add-consultation/';
  readonly mediUrl = 'http://localhost:9000/v1/medidata';
 readonly url_k = 'http://localhost:9000/v1/';

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

  getDoctrorConsultations(id, fromDate, untilDate): Observable<Consultations[]>{
    return this.http.get<Consultations[]>(
      this.url_k + 'doctor/'  + id +  '/consultation/' + fromDate +'/' + untilDate,
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

 getConsultationToUpdate(id):Observable <Consultations> {
      console.log("getConsultationToUpdate " +this.url_k+ 'consultation/'  +id )
      return this.http.get<Consultations>(
        this.url+ 'consultation/'  +id,
        {headers:new HttpHeaders(
          {'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials"))}
          )
        }
        );
    }

  getConsultationMedi(): Observable<MediData[]>{
    return this.http.get<MediData[]>(
      this.mediUrl, {
      headers: new HttpHeaders({ 'Authorization': 'Basic ' + btoa(sessionStorage.getItem("credentials")) }
      )
    }
    );
  }

    updateConsultation(id,data): Observable<any>{
      console.log("updateConsultation method" )
      console.log(id )

      return this.http.put( this.url_k + 'consultation/'  +id, data,{headers : new  HttpHeaders(
        {'Authorization': 'Basic ' +  btoa(sessionStorage.getItem("credentials"))}
        )});

    }

}
